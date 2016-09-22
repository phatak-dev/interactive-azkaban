package com.madhukaraphatak.azkaban.restservice

import java.util.concurrent.ConcurrentHashMap

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.madhukaraphatak.azkaban.Models._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.madhukaraphatak.azkaban.schedule.Scheduler
import spray.json.JsArray

import scala.collection.JavaConverters._
import spray.json.{DefaultJsonProtocol, JsArray, pimpAny}
import spray.json.DefaultJsonProtocol._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._

/**
  * Created by madhu on 8/11/15.
  */
trait RestService {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val sparkSession: SparkSession
  val datasetMap = new ConcurrentHashMap[String, Dataset[Row]]()

  import ServiceJsonProtoocol._

  val route =
    pathSingleSlash {
      get {
        complete {
          "welcome to rest service"
        }
      }
    } ~
      path("load") {
        post {
          entity(as[LoadRequest]) {
            loadRequest => complete {
              val id = "" + System.nanoTime()
              val dataset = sparkSession.read.format("csv")
                .option("header", "true")
                .load(loadRequest.path)
              datasetMap.put(id, dataset)
              id
            }
          }
        }
      } ~
      path("view" / """[\w[0-9]-_]+""".r) { id =>
        get {
          complete {
            val dataset = datasetMap.get(id)
            dataset.take(10).map(row => row.toString())
          }
        }
      } ~
      path("schedule") {
        get {
          complete {
            val scheduler = new Scheduler()
            scheduler.schedule(system, materializer)
            "scheduled"
          }
        }
      }

}
