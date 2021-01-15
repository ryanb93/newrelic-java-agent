/*
 *
 *  * Copyright 2020 New Relic Corporation. All rights reserved.
 *  * SPDX-License-Identifier: Apache-2.0
 *
 */

package com.agent.instrumentation.akka.http.core

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

class AkkaFlowServer() {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  implicit val timeout: Timeout = 3 seconds

  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  var bindingFuture: Future[Http.ServerBinding] = _

  def start(port: Int) = {

    import akka.http.scaladsl.server.Directives._
    bindingFuture = Http().newServerAt(interface = "localhost", port).bindFlow {
        path("asyncPing") {
          get {
            complete {
              println("request made")
              StatusCodes.OK -> "Hoops!"
            }
          }
        }
      }

    Await.ready({
      bindingFuture
    }, timeout.duration)
  }

  def stop() = {
    if (bindingFuture != null) {
      bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
    }
  }
}
