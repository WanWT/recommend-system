package fun.nya.recommend.stream

import java.io.File

import fun.nya.recommend.model.ALSRecommendModel
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import com.redislabs.provider.redis._
import fun.nya.recommend.dao.RatingParser
import fun.nya.recommend.model.data.ALSModelData
import fun.nya.recommend.dao._
import org.apache.spark.internal.Logging
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD




class RecommendStream(spark : SparkSession, initRatings : RDD[Rating]) extends Logging with Serializable {
  private val sc = spark.sparkContext
  private var sparkStream : StreamingContext = new StreamingContext(sc, Seconds(1));
  private val modelPath = sc.getConf.get("model.local")
  private var ratings = initRatings;
  val redisRecommendStream = sparkStream.createRedisStreamWithoutListname(Array("recommend_for"));
  val redisTrainStream = sparkStream.createRedisStreamWithoutListname(Array("new_rating")).window(Minutes(2), Minutes(2));
  def startTrain() = {
    ratings = spark.getRatingsLocal()
    val modelData = new ALSModelData(ratings)
    val recomModel = new ALSRecommendModel()
    recomModel.train(modelData)
    checkModelExist()
    recomModel.save(sc, modelPath)
    redisTrainStream.foreachRDD(processTrainStream(_))
    sparkStream.start()
    sparkStream.awaitTermination()
  }
  def startRecommend() = {
    redisRecommendStream.foreachRDD(processRecommendStream(_))
    sparkStream.start()
    sparkStream.awaitTermination()
  }
  def processRecommendStream(rdds : RDD[String]) = {

    if (!rdds.isEmpty()) {
      val commands = rdds.collect()
      commands.foreach(command => {
        val model = new ALSRecommendModel();
        model.load(sc, modelPath)
        println("Recommend stream : " + System.currentTimeMillis())
        val start: Long = System.currentTimeMillis()
        val commands = command.split(",")
        val user = commands(0).toInt
        val num = commands(1).toInt
        val recommendRDD = sc.parallelize(model.recommend(user, num))
        val startTo: Long = System.currentTimeMillis()
        sc.toRedisLIST(recommendRDD, "recommend_movie_" + user)
        val endTo: Long = System.currentTimeMillis()
        val end: Long = System.currentTimeMillis()
        println("User : " + user + ", num : " + num + " , success : " + recommendRDD.count() + " , cost : " + (end - start) + " , toRedisCost : " + (endTo - startTo))
      })
    }
  }
  def processTrainStream(RDD: RDD[String]) = {
    if(!RDD.isEmpty()) {
      println("Train stream : " + System.currentTimeMillis())
      ratings.union(RDD.map(RatingParser.parseRatingData _))
      val modelData = new ALSModelData(ratings)
      var recomModel: ALSRecommendModel = new ALSRecommendModel();
      recomModel.train(modelData)
      checkModelExist()
      recomModel.save(sc, modelPath)
    }
  }

  def checkModelExist() = {
    val modelFile = new File(modelPath)
    if (modelFile.exists()) deleteModelFile(modelFile)
  }
  def deleteModelFile(file : File) : Unit = {
    if(file.listFiles() != null) {
      for (subFile <- file.listFiles()) {
        val result = deleteModelFile(subFile)
      }
    }
    file.delete()
  }
}