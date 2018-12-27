package test

import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.SparkSession

object UserBaseTest {
  case class Movie(movieId: Int, title: String)
  case class User(userId: Int, age: Int, gender: String, occupation: Int, zipCode: String)


  def parseMovieData(data: String): Movie = {
    val dataField = data.split("\\|+")
    //assert(dataField.size == 3)
    Movie(dataField(0).toInt, dataField(1).toString)
  }

  def parseUserData(data: String): User = {
    val dataField = data.split("\\|")
    //assert(dataField.size == 5)
    User(dataField(0).toInt, dataField(1).toInt, dataField(2).toString, dataField(3).toInt, dataField(4).toString)
  }

  def parseRatingData(data: String): Rating = {
    val dataField = data.split("\\s+")
    assert(dataField.size == 4)
    Rating(dataField(0).toInt, dataField(1).toInt, dataField(2).toDouble)
  }

  def main(args: Array[String]){
    val spark = SparkSession.builder.master("local").appName("Predict").getOrCreate()
    import spark.implicits._
    var usersData = spark.read.textFile(".//resources//ml-100k//u.user").map(parseUserData _).cache()
    var ratingsData = spark.read.textFile(".//resources//ml-100k//u.data").map(parseRatingData _).cache()
    var moviesData = spark.read.textFile(".//resources//ml-100k//u.item").map(parseMovieData _)
    // convert to DataFrame
//    val moviesDF = moviesData.toDF()
//    val usersDF = usersData.toDF()
//    val ratingsDF = ratingsData.toDF()
//
    // split to data set and test set
    val tempPartitions = ratingsData.randomSplit(Array(0.7, 0.3), 1024L)
    val trainingSetOfRatingsData = tempPartitions(0).cache().rdd
    val testSetOfRatingData = tempPartitions(1).cache().rdd

    // training model
    val recomModel = new ALS().setRank(20).setIterations(10).run(trainingSetOfRatingsData)

    val recomResult = recomModel.recommendProducts(10, 10)
    println(s"Recommend Movie to User ID 10")
    println(recomResult.mkString("\n"))
    val movieTitles = moviesData.toDF().as[(Int, String)].rdd.collectAsMap()
    val recommendMoviesWithTitle = recomResult.map(rating =>(movieTitles(rating.product), rating.rating))
    println(recommendMoviesWithTitle.mkString("\n"))

    val predictResultOfTestSet = recomModel.predict(testSetOfRatingData.map{
      case Rating(user, product, rating) => (user, product)
    })

    val formatResultOfTestSet = testSetOfRatingData.map{
      case Rating(user, product, rating) => ((user, product), rating)
    }

    val formatResultOfPredictionResult = predictResultOfTestSet.map {
      case Rating(user, product, rating) => ((user, product), rating)
    }

    val finalResultForComparison = formatResultOfPredictionResult.join(formatResultOfTestSet)

    val MAE = finalResultForComparison.map {
      case ((user, product), (ratingOfTest, ratingOfPrediction)) =>
        val error = (ratingOfTest - ratingOfPrediction)
        Math.abs(error)
    }.mean()

    println(s"mean error: $MAE")
    spark.stop()
  }
}
