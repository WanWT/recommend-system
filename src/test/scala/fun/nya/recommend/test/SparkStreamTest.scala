package fun.nya.recommend.test

import org.apache.spark.{SparkConf, SparkContext}
import com.redislabs.provider.redis._
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamTest {

  def parseRatingData(data: String): Rating = {
    val dataField = data.split(",")
    assert(dataField.size == 4)
    Rating(dataField(0).toInt, dataField(1).toInt, dataField(2).toDouble)
  }

  def main(args : Array[String]): Unit = {
    val sc = SparkContext.getOrCreate(new SparkConf().setMaster("local[*]").setAppName("sparkstreamtest").set("redis.host", "localhost").set("redis.port", "6379"));
    var ratingsData = sc.fromRedisList("user_rating_*").map(parseRatingData _).cache()
    val tempPartitions = ratingsData.randomSplit(Array(0.7, 0.3), 1024L)
    val trainingSetOfRatingsData = tempPartitions(0).cache()
    val testSetOfRatingData = tempPartitions(1).cache()

    val recomModel = new ALS().setRank(20).setIterations(10).run(trainingSetOfRatingsData)
    //val recomModel = MatrixFactorizationModel.load(sc, "C://Model/modelSaved");
    val ssc = new StreamingContext(sc, Seconds(1));
    var redisStream = ssc.createRedisStream(Array("foo"))
    redisStream.foreachRDD(rdds => {
      val startTime: Long = System.currentTimeMillis
      val recomUsers = rdds.values.collect()
      recomUsers.foreach(user => {
        val prodocts = recomModel.recommendProducts(user.toInt, 5).map(
          rating => {
            rating.product.toString
          }
        );
        sc.toRedisLIST(sc.parallelize(prodocts), "recommend_movie_" + user)
        val endTime: Long = System.currentTimeMillis
        //println("： " + (endTime - startTime) + "ms")
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
