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

    val wineRoute:Route =
      pathPrefix("wines") {
        concat(
          get{
            concat(
              pathEndOrSingleSlash{
                wineObject.getAllWines match
                  case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                  case Left(emptyList) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "List of Wines is Empty"} """))
                  case Right(wineList) => complete(OK, HttpEntity(ContentTypes.`application/json`,s""" {"Wine List" : "$wineList"} """))
              },
              //http://localhost:8080/wines/{id}
              path(Segment) {
                id => wineObject.getWineById(id.toInt) match
                  case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                  case Left(notFound) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Wine with ID $id not found"} """))
                  case Right(wine) => complete(OK, HttpEntity(ContentTypes.`application/json`,s""" {"Wine with id $id" : "$wine"} """))
              },
              //http://localhost:8080/wines/name/?wine_name=Pinot%20Noir
              pathPrefix("name"){
                parameter("wine_name") {
                  wineName =>
                    wineObject.getWineByName(wineName) match
                      case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                      case Left(emptyList) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "List of Wines is Empty"} """))
                      case Right(wineList) => complete(OK, HttpEntity(ContentTypes.`application/json`,s""" {"Wine List" : "$wineList"} """))
                }
              }
            )
          },
          post{
            pathEndOrSingleSlash{
              entity(as[Wine]) {
                wine =>
                  wineObject.setWine(wine) match
                    case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                    case Left(failedInsertion) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Insertion failed"} """))
                    case Right(wineInserted) => complete(Created, HttpEntity(ContentTypes.`application/json`,s""" {"Wine inserted"} """))
              }
            }
          },
          put{
            pathEndOrSingleSlash{
              entity(as[NewWine]) {
                newWine =>
                  wineObject.updateWine(newWine) match
                    case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                    case Left(failedUpdate) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Update failed"} """))
                    case Right(wineUpdated) => complete(OK, HttpEntity(ContentTypes.`application/json`,s""" {"Wine updated"} """))
              }
            }
          },
          delete{
            pathEndOrSingleSlash{
              entity(as[Wine]) {
                wine =>
                  wineObject.deleteWine(wine) match
                    case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                    case Left(failedDelete) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Delete failed"} """))
                    case Right(wineDeleted) => complete(OK, HttpEntity(ContentTypes.`application/json`,s""" {"Wine Deleted"} """))
              }
            }
          }
        )
      }

    val customerRoute: Route =
      pathPrefix("customers") {
        concat(
          get{
            concat(
              pathEndOrSingleSlash{
                wineObject.getAllCustomers match
                  case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                  case Left(emptyList) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "List of Customer is Empty"} """))
                  case Right(customerList) => complete(OK, HttpEntity(ContentTypes.`application/json`, s""" {"Customer List" : "$customerList"} """))
              },
              path(Segment) {
                id =>
                  wineObject.getCustomerById(id.toInt) match
                    case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                    case Left(notFound) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Customer with ID $id not found"} """))
                    case Right(wine) => complete(OK, HttpEntity(ContentTypes.`application/json`, s""" {"Customer with id $id" : "$wine"} """))
              },
              pathPrefix("name") {
                parameter("first_name", "last_name", "email") {
                  (firstName, lastName, email) =>
                    wineObject.getCustomerByNameEmail(firstName, lastName, email) match
                      case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                      case Left(notFound) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Customer not found"} """))
                      case Right(customer) => complete(OK, HttpEntity(ContentTypes.`application/json`, s""" {"Customer" : "$customer"} """))
                }
              }
            )
          },
          post {
            pathEndOrSingleSlash {
              entity(as[Customer]) {
                customer =>
                  wineObject.setCustomer(customer) match
                    case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                    case Left(failedInsertion) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Insertion failed"} """))
                    case Right(customerInserted) => complete(Created, HttpEntity(ContentTypes.`application/json`, s""" {"Customer inserted"} """))
              }
            }
          },
          put {
            pathEndOrSingleSlash {
              entity(as[NewCustomer]) {
                newCustomer =>
                  wineObject.updateCustomer(newCustomer) match
                    case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                    case Left(failedUpdate) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Update failed"} """))
                    case Right(customerUpdated) => complete(OK, HttpEntity(ContentTypes.`application/json`, s""" {"Customer updated"} """))
              }
            }
          },
          delete {
            pathEndOrSingleSlash {
              entity(as[Customer]) {
                customer =>
                  wineObject.deleteCustomer(customer) match
                    case Left(exception) if exception.equals("Exception occurred while accessing DB") => complete(InternalServerError, HttpEntity(ContentTypes.`application/json`, s""" {"exception" : "$exception"} """))
                    case Left(failedInsertion) => complete(NotFound, HttpEntity(ContentTypes.`application/json`, s""" {"message" : "Delete failed"} """))
                    case Right(customerInserted) => complete(OK, HttpEntity(ContentTypes.`application/json`, s""" {"Customer deleted"} """))
              }
            }
          }
        )
      }
    Http().newServerAt("0.0.0.0", 8080).bind(concat(wineRoute, customerRoute, base))
    StdIn.readLine()
  }
}
