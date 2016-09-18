package com.madhukaraphatak.scheduling

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.util.ByteString

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by madhu on 18/9/16.
  */
object RestServiceFlow {
  def main(args: Array[String]): Unit = {
    //load a dataset

    val restServer = "http://localhost:8080"

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    //send load request
    val loadJsonRequestString = ByteString(
      s"""{
            "path": "src/main/resources/test.csv"
          |}
     """.stripMargin)

    val loadJsonRequest = HttpRequest(
      HttpMethods.POST,
      uri = restServer+"/"+"load",
      entity = HttpEntity(MediaTypes.`application/json`, loadJsonRequestString)
    )

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(loadJsonRequest)

    val result = Await.result[HttpResponse](responseFuture, 60 seconds)
    println(result)

    system.shutdown()
  }
}
