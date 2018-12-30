package fun.nya.recommend.model
import fun.nya.recommend.model.config.{ALSModelConfig, DefaultALSModelConfig, ModelConfig}
import fun.nya.recommend.model.data.{ALSModelData, ModelData}
import org.apache.spark.SparkContext
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}

class ALSRecommendModel() extends RecommendModel() {
  var recomModel : MatrixFactorizationModel = null;
  var config : ALSModelConfig = new DefaultALSModelConfig()
  override def recommend(userID: Int, num: Int) : Array[String] = {
    recomModel.recommendProducts(userID, num).sortWith((rating_1, rating_2) =>{
      rating_1.rating > rating_2.rating
    }).map(rating => {
      rating.product.toString
    })
  }

  override def train(data: ModelData): Unit = {
    var alsdata = data.asInstanceOf[ALSModelData].getRatings()
    val tempPartitions = alsdata.randomSplit(Array(0.7, 0.3), 1024L)
    val trainingSetOfRatingsData = tempPartitions(0).cache()
    val testSetOfRatingData = tempPartitions(1).cache()
    recomModel = new ALS().setRank(config.RANKS).setIterations(config.ITERATIONS).run(trainingSetOfRatingsData)

//    val predictResultOfTestSet = recomModel.predict(testSetOfRatingData.map{
//      case Rating(user, product, rating) => (user, product)
//    })
//
//    val formatResultOfTestSet = testSetOfRatingData.map{
//      case Rating(user, product, rating) => ((user, product), rating)
//    }
//
//    val formatResultOfPredictionResult = predictResultOfTestSet.map {
//      case Rating(user, product, rating) => ((user, product), rating)
//    }
//
//    val finalResultForComparison = formatResultOfPredictionResult.join(formatResultOfTestSet)
//
//    val MAE = finalResultForComparison.map {
//      case ((user, product), (ratingOfTest, ratingOfPrediction)) =>
//        val error = (ratingOfTest - ratingOfPrediction)
//        Math.abs(error)
//    }.mean()
//
    println(s"mean error: ")
  }

  override def getConf(): ModelConfig = {
    config
  }

  override def setConf(config: ModelConfig): Unit = {
    this.config = config.asInstanceOf[ALSModelConfig]
  }

  override def save(sc: SparkContext, path: String): Unit = {
    if(recomModel != null) {
      recomModel.save(sc, path)
    }
  }

  override def load(sc: SparkContext, path: String): Unit = {
    recomModel = MatrixFactorizationModel.load(sc, path)
  }
}
