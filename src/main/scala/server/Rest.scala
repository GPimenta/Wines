package server

import spray.json.*
import DefaultJsonProtocol.*
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.*
import akka.http.scaladsl.model.StatusCodes.*
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import connector.PSQLconnection
import controller.WineController
import model.*
import repository.WineDAOImplementation

import scala.io.StdIn


object Rest extends DefaultJsonProtocol{

  def main(args: Array[String]): Unit = {
    val wineObject = WineController(WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP")))
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val customerFormat: RootJsonFormat[Customer] = jsonFormat4(Customer.apply)
    implicit val newCustomerFormat: RootJsonFormat[NewCustomer] = jsonFormat5(NewCustomer.apply)
    implicit val wineFormat: RootJsonFormat[Wine] = jsonFormat6(Wine.apply)
    implicit val newWineFormat: RootJsonFormat[NewWine] = jsonFormat7(NewWine.apply)


    val base = path("") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "UP"))
      }
    }

    val route:Route =
      pathPrefix("Wines") {
        concat(
          get{
            concat(
              pathEndOrSingleSlash{
                wineObject.getAllWines match
                  case Left(message) => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$message"} """))
                  case Right(list) if list.isEmpty => complete(NotFound, HttpEntity(ContentTypes.`application/json`,""" {"message" : "Not Found"} """))
                  case Right(list) => complete(OK, HttpEntity(ContentTypes.`application/json`,s""" {"result" : "$list"} """))
              },
              path(Segment) { name =>
                wineObject.getWine(name) match
                  case Left(message) => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$message"} """))
                  case Right(value) => ???
              }
            )
          }
        )
      }

    Http().newServerAt("0.0.0.0", 8080).bind(concat(route, base))
    StdIn.readLine()

  }

}
