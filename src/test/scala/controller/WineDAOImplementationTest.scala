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

//  test("Get all Wines") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    implementation.getAllWines match
//      case Failure(exception) => exception.printStackTrace()
//      case Success(wineList) => wineList.foreach(Wine => println(Wine))
//  }
//
//  test("Get all Customers") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    implementation.getAllCustomers match
//      case Failure(exception) => exception.printStackTrace()
//      case Success(customerList) => customerList.foreach(Customer => println(Customer))
//  }
//
//
//  test("Get by Id Wine") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    implementation.getByIdWine(1) match
//      case Failure(exception) => exception.printStackTrace()
//      case Success(wine) => println(wine)
//  }
//
//
//  test("Get by Id Customer") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    implementation.getByIdCustomer(1) match
//      case Failure(exception) => exception.printStackTrace()
//      case Success(customer) => println(customer)
//  }

//  test("Get by Wine name") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    implementation.getByNameWine("Sauvignon Blanc") match
//      case Failure(exception) => exception.printStackTrace()
//      case Success(wineList) => wineList.foreach(Wine => println(Wine))
//  }

//  test("Get by Customer full name") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    implementation.getByIdCustomer(1) match
//      case Failure(exception) => exception.printStackTrace()
//      case Success(customer) => println(customer)
//  }


}