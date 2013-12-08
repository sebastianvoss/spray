package com.example

//import MyJsonProtocol._
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import akka.actor.Actor
import spray.routing.HttpService
import spray.http.StatusCodes
import com.typesafe.scalalogging.slf4j.Logging
import akka.actor.ActorLogging
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val config = ConfigFactory.load();
  val s = config.getString("my.organization.project.name")

  implicit val colorFormat = jsonFormat4(Color)
  implicit val legFormat = jsonFormat3(Leg)
  implicit val tradeFormat = jsonFormat5(Trade)

  def uuid = java.util.UUID.randomUUID.toString

  val myRoute =
    path("test") {
      get {
        complete {
          Map("a" -> 1, "b" -> 2)
        }
      }
    } ~
    path("hello") {
      get {
        complete {
          Color("CadetBlue", 95, 158, 160)
        }
      }
    } ~
    path("trade" / Segment) { id =>
      get {
        complete {
          Trade(id, s"Spread $s", 0.2, 50, List(Leg("Underlying 1", 25.2, 50), Leg("Underlying 2", 25, 50)))
        }
      } ~
      post {
        entity(as[Trade]) { trade =>
          val result: Trade = Trade(id, trade.product, trade.price * 1.1, trade.quantity)
          complete(result)
        }
      }
    } ~
    path("oldApi") {
      redirect("hello", StatusCodes.MovedPermanently)
    }
}