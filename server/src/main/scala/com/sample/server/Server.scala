package com.sample.server

import com.sample.thrift.Data
import com.twitter.finagle.http._
import com.twitter.finagle.{Http, Service}
import com.twitter.util.logging.Logger
import com.twitter.util.{Await, Future}

object HttpServer extends App {
  lazy val dataThriftSerializer = com.twitter.scrooge.TJSONProtocolThriftSerializer(Data)
  val log = Logger("server")
    val httpserver = Http.serve(addr=":8081", new Service[Request, Response] {
      override def apply(request: Request): Future[Response] = {
        val dataContent: String = request.getContentString().substring(request.getContentString().indexOf("""rec":""") + 5, request.getContentString().length - 3)

        val thriftStructDataInfoContent: Data = dataThriftSerializer.fromString(dataContent)

        log.info(s"thrift struct: $thriftStructDataInfoContent")
        Future.value(Response.apply(request.version, Status.Ok))
      }
    })

    Await.ready(httpserver)
}
