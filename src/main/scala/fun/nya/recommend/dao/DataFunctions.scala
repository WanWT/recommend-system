package fun.nya.recommend.dao

import org.apache.spark.sql.SparkSession

trait DataFunctions {
  implicit def toDataResposity(sparkSession: SparkSession): DataResposity = new DataResposity(sparkSession)
}
