package com.madhukaraphatak.azkaban.schedule

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.madhukaraphatak.azkaban.AzkabanClient
import com.madhukaraphatak.azkaban.AzkabanModels.{CreateSessionRequest, UploadProjectZipRequest}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await

/**
  * Created by madhu on 18/9/16.
  */
class Scheduler {

  def schedule(implicit actorSystem: ActorSystem,
               materializer:ActorMaterializer) = {
    val azkabanUrl = "http://localhost:8081"
    val userName = "azkaban"
    val password = "azkaban"
    val project =  "test"
    val flow = "test1"
    val zipFilePath = "src/main/resources/azkaban-jobs.zip"

    val azkabanClient = new AzkabanClient(azkabanUrl)

    //create azkaban session
    val azkabanSession = azkabanClient.createSession(
      CreateSessionRequest(userName,password)).get

    val fileUploadResponse = azkabanClient.uploadProjectZip(UploadProjectZipRequest(
      project,zipFilePath))(azkabanSession)

    import scala.concurrent.duration._
    Await.result(fileUploadResponse, 3 minute)
  }
}
