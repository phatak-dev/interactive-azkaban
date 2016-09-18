import sbt._

name := "scheduling-code"

version := "1.0"

scalaVersion := "2.11.5"

resolvers ++= Seq(
  "oss-sonatype" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0"
)
