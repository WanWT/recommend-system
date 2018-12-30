package fun.nya.recommend.model

import fun.nya.recommend.model.config.ModelConfig
import fun.nya.recommend.model.data.ModelData
import org.apache.spark.SparkContext

abstract class RecommendModel() extends Serializable {
  def recommend(userID : Int, num : Int) : Array[String]
  def train(data : ModelData)
  def save(sc : SparkContext, path : String)
  def load(sc : SparkContext, path : String)
  def getConf() : ModelConfig
  def setConf(config : ModelConfig)
}
