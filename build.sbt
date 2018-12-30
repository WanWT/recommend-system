name := "recommend-system"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.0"
libraryDependencies += "com.redislabs" % "spark-redis" % "2.3.1-M2"