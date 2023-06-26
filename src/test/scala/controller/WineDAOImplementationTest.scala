package controller

import connector.PSQLconnection
import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success}

class WineDAOImplementationTest extends AnyFunSuite {

//  test("Test Connection") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    val all = implementation.getAll.get
//    all.foreach(Wine => println(Wine))
//  }

//  test("Get all") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    implementation.getAll match
//      case Failure(exception) => exception.printStackTrace()
//      case Success(wineList) => wineList.foreach(Wine => println(Wine))
//  }

  test("Get by Id") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getById(1) match
      case Failure(exception) => exception.printStackTrace()
      case Success(wine) => println(wine)
  }
}
