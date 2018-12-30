package fun.nya.recommend.test

import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.{SparkConf, SparkContext}
import com.redislabs.provider.redis._

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
    val dataField = data.split(",")
    assert(dataField.size == 4)
    Rating(dataField(0).toInt, dataField(1).toInt, dataField(2).toDouble)
  }

  def main(args: Array[String]){
    //val spark = SparkSession.builder.master("local").appName("Predict").getOrCreate()
    val sc = SparkContext.getOrCreate(new SparkConf().setMaster("local[*]").setAppName("userbasetest").set("redis.host", "localhost").set("redis.port", "6379"));
    //import spark.implicits._
    //var usersData = sc.textFile(".//resources//ml-100k//u.user").map(parseUserData _).cache()
    //var ratingsData = sc.textFile(".//resources//ml-latest//ratings.csv").map(parseRatingData _).cache()
    var ratingsData = sc.fromRedisList("user_rating_*").map(parseRatingData _).cache()
    //var moviesData = sc.textFile(".//resources//ml-100k//u.item").map(parseMovieData _)
    // convert to DataFrame
//    val moviesDF = moviesData.toDF()
//    val usersDF = usersData.toDF()
//    val ratingsDF = ratingsData.toDF()
//
    // split to data set and test set

    val tempPartitions = ratingsData.randomSplit(Array(0.7, 0.3), 1024L)
    val trainingSetOfRatingsData = tempPartitions(0).cache()
    val testSetOfRatingData = tempPartitions(1).cache()

    // training model
    //val recomModel = MatrixFactorizationModel.load(sc, "C://Model/modelSaved");
    val recomModel = new ALS().setRank(20).setIterations(10).run(trainingSetOfRatingsData)
    //recomModel.save(sc, "C://Model/modelSaved")
    //println(recomModel.recommendProductsForUsers(5).collectAsMap().take(1).size);
    recomModel.recommendProductsForUsers(5).collect().foreach(
      recomArray => {
        val array = recomArray._2.map(rating => {
          rating.product.toString
        })
        sc.toRedisLIST(sc.parallelize(array), "recommend_movie_" + recomArray._1.toString);
      }
    )
//    val products = recomModel.recommendUsersForProducts(10);
//    products.mapValues(
//      (ratings : Array[Rating]) => {
//        var stringArray : Array[String] = new Array[String](ratings.size);
//        for(rating in ratings) {
//
//        }
//      }
//    )
//    val userMatrix = recomModel.userFeatures.collectAsMap();
//    var userIDs = userMatrix.keys
//
//    userIDs.foreach((userID : Int) => {
//      val recomResult = recomModel.recommendProducts(userID, 5);
//      //println(s"Recommend Movie to User ID" + userID)
//      //println(recomResult.mkString("\n"))
//      val recomArray : Array[String] = new Array[String](recomResult.size);
//      for(i <- 0 to recomResult.size - 1) {
//        recomArray.update(i, recomResult.apply(i).product.toString());
//      }
//      val recomRDD = recomModel.recommendProductsForUsers(5)
//      println(recomArray.apply(0))
//      sc.toRedisLIST(sc.parallelize(recomArray),"recommend_movie_" + userID.toString);
//    })
    //val movieTitles = moviesData.toDF().as[(Int, String)].rdd.collectAsMap()
    //val recommendMoviesWithTitle = recomResult.map(rating =>(movieTitles(rating.product), rating.rating))
    //println(recommendMoviesWithTitle.mkString("\n"))

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
  }
}
