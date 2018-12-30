package fun.nya.recommend.dao

import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.sql.Row

object RatingParser {
  def parseRatingData(data: String): Rating = {
    val dataField = data.split(",")
    assert(dataField.size == 4)
    Rating(dataField(0).toInt, dataField(1).toInt, dataField(2).toDouble)
  }
  def parseRatingData(data: Row): Rating = {
    Rating(data.getString(0).toInt, data.getString(1).toInt, data.getString(2).toDouble)
  }
}
