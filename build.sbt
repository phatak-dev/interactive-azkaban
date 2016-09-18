import sbt._

name := "azkaban-examples"

version := "1.0"

scalaVersion := "2.11.5"

resolvers ++= Seq(
  "oss-sonatype" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0",
  "com.typesafe.akka" %%"akka-http-testkit-experimental" % "1.0",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "org.apache.spark" %% "spark-sql" % "2.0.0",
"com.madhukaraphatak" %% "azkaban-client" % "0.1-SNAPSHOT"
)
