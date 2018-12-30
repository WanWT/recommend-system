package fun.nya.recommend.recommend

import org.apache.spark.sql.SparkSession
import fun.nya.recommend.stream.RecommendStream

object Main {
  def main(args : Array[String]): Unit = {
    var mode : Int = -1
    var name : String = ""
    if(args(0).equalsIgnoreCase("train")) mode = 0;
    if(args(0).equalsIgnoreCase("recommend")) mode = 1;
    if(mode == -1) {
      println("Wrong argument. Exit.")
      return
    }
    val spark = SparkSession.builder
      .appName("Recommend System " + args(0))
      .config("redis.host","localhost")
      .config("redis.port","6379")
      .config("rating.local", ".//resources//ml-latest//ratings.csv")
      .config("rating.redis", "user_rating")
      .config("model.local","./modelSaved")
      .getOrCreate()
    spark.sparkContext.getConf.getAll.foreach( pair => {
      println(pair._1 + " = " + pair._2)
    })
    if(mode == 0) {
      val recommendStream = new RecommendStream(spark, null)
      recommendStream.startTrain()
    }
    else {
      val recommendStream = new RecommendStream(spark, null)
      recommendStream.startRecommend()
    }
  }
}
