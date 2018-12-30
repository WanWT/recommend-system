package fun.nya.recommend.dao

import org.apache.spark.SparkConf

class DataConfig(val conf: SparkConf) extends Serializable {
  val localRating = getLocalRatingFromConf()
  val redisRating = getRedisRatingFromConf()


  private def getLocalRatingFromConf(): String = {
    conf.get("rating.local")
  }

  private def getRedisRatingFromConf(): String = {
    conf.get("rating.redis")
  }

  def getLocalRating() : String = {
    localRating
  }

  def getRedisRating() : String = {
    redisRating
  }
}
