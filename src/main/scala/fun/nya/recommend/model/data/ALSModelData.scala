package fun.nya.recommend.model.data

import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD

class ALSModelData(ratings : RDD[Rating]) extends ModelData {
  def getRatings() = {
    ratings;
  }
}
