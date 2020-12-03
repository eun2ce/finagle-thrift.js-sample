import sbt.Keys.libraryDependencies

import scala.collection.TraversableOnce.MonadOps

val twitterVersion = "18.8.0"
val jacksonVersion = "2.8.4"
lazy val thriftApi = (project in file("thrift"))
  .settings(
    commonSettings
  )
  .settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "scrooge-core" % twitterVersion exclude("com.twitter", "libthrift"),
      "com.twitter" %% "scrooge-serializer" % twitterVersion exclude("com.twitter", "libthrift"),

      "com.twitter" %% "twitter-server" % twitterVersion,
      "com.twitter" %% "finagle-http" % twitterVersion,
      "com.twitter" %% "finagle-thrift" % twitterVersion,

      "org.apache.thrift" % "libthrift" % "0.10.0",
      "org.json4s" %% "json4s-jackson" % "3.2.11",
      "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
    ),
    libraryDependencies ~= {_.map(_.exclude("org.slf4j", "slf4j-log4j12"))},
  )

lazy val server = (project in file("server"))
  .settings(
    commonSettings
  )
  .dependsOn(thriftApi % "compile->compile")


lazy val client = (project in file("client"))
  .settings(
    commonSettings
  )
  .dependsOn(thriftApi % "compile->compile")

lazy val commonSettings = Seq(
  organization := "jo2unce@gmail.com",
  version := "0.1",
  scalaVersion := "2.12.1"
)
