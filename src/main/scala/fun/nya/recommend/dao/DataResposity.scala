package fun.nya.recommend.dao

import com.redislabs.provider.redis._
import org.apache.spark.internal.Logging
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

class DataResposity(val sparkSession: SparkSession) extends Logging {
  private val VALID_LOG_LEVELS =
    Set("ALL", "DEBUG", "ERROR", "FATAL", "INFO", "OFF", "TRACE", "WARN")

  private val sc = sparkSession.sparkContext

  def getRatingsRedis()(implicit dataConfig: DataConfig = new DataConfig(sc.getConf))
  : RDD[Rating] = {
    logInfo("Get ratings from redis")
    val ratings = sc.fromRedisList(dataConfig.getRedisRating()).map(RatingParser.parseRatingData _).cache()
    ratings
  }
  def getRatingsLocal()(implicit dataConfig: DataConfig = new DataConfig(sc.getConf)) : RDD[Rating] = {
    logInfo("Get ratings from local")
    import sparkSession.implicits._
    val ratings = sparkSession.read.option("header", "true").csv(dataConfig.getLocalRating()).map(RatingParser.parseRatingData _).rdd.cache()
    ratings
  }
}
