package fun.nya.recommend.test

import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.sql.{Row, SparkSession}
import com.redislabs.provider.redis._


object LoadTimeTest {
  def parseRatingData(data: String): Rating = {
    val dataField = data.split(",")
    assert(dataField.size == 4)
    Rating(dataField(0).toInt, dataField(1).toInt, dataField(2).toDouble)
  }
  def parseRatingData(data: Row): Rating = {
//    val dataField = data.split(",")
//    assert(dataField.size == 4)
    Rating(data.getString(0).toInt, data.getString(1).toInt, data.getString(2).toDouble)
  }
  def main(args : Array[String]) = {
    val spark = SparkSession.builder.master("local[*]").appName("Load time test").config("spark.driver.memory","6G").config("redis.host", "localhost").config("redis.port", "6379").getOrCreate()

    import spark.implicits._
    var startTimeLocal: Long = System.currentTimeMillis
    var ratingsData1 = spark.read.option("header", "true").csv(".//resources//ml-latest//ratings.csv").mapPartitions((rows : Iterator[Row]) => {
      rows.map(parseRatingData _)
    }).count()
    var endTimeLocal: Long = System.currentTimeMillis
    println("Local file finish.")
    var startTimeRedis = System.currentTimeMillis
    var ratingsData2 = spark.sparkContext.fromRedisList("user_rating_*").mapPartitions((strings : Iterator[String]) => {
      strings.map(parseRatingData _)
    }).count()
    var endTimeRedis = System.currentTimeMillis
    println("Load from local fs time is : " + (endTimeLocal - startTimeLocal) + " ms")
    println("Load from redis time is : " + (endTimeRedis - startTimeRedis) + " ms")
  }
}
