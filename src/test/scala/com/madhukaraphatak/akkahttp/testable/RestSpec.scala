package com.madhukaraphatak.akkahttp.testable

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model._
import com.madhukaraphatak.azkaban.restservice.RestService


/**
  * Created by madhu on 8/11/15.
  */
abstract class RestSpec extends WordSpec with Matchers with ScalatestRouteTest with RestService{

}
