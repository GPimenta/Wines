package repository

import connector.PSQLconnection
import model.{Customer, NewCustomer, NewWine, Wine}
import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success}

class WineDAOImplementationTest extends AnyFunSuite {

//  test("Test Connection") {
//    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
//    val all = implementation.getAll.get
//    all.foreach(Wine => println(Wine))
//  }

  test("Get all Wines") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getAllWines match
      case Failure(exception) => exception.printStackTrace()
      case Success(wineList) => wineList.foreach(Wine => println(Wine))
  }

  test("Get all Customers") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getAllCustomers match
      case Failure(exception) => exception.printStackTrace()
      case Success(customerList) => customerList.foreach(Customer => println(Customer))
  }

  test("Get by Id Wine") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getByIdWine(1) match
      case Failure(exception) => exception.printStackTrace()
      case Success(wine) => println(wine)
  }


  test("Get by Id Customer") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getByIdCustomer(1) match
      case Failure(exception) => exception.printStackTrace()
      case Success(customer) => println(customer)
  }

  test("Get by Wine") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getWine(Wine(1,"Syrah", "Merlot", 1988, "Cogibox", 520.08)) match
      case Failure(exception) => exception.printStackTrace()
      case Success(wine) => wine match
        case Left(value) => println(value)
        case Right(wineUnit) => println(wineUnit)
  }

  test("Get by Wine name") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getWineByName("Pinot Noir") match
      case Failure(exception) => exception.printStackTrace()
      case Success(wine) => wine match
        case Left(value) => println(value)
        case Right(wineList) => println(wineList)
  }

  test("Get by Customer full name") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    implementation.getCustomer(Customer(1, "Phedra", "Goffe", "pgoffe6@salon.com")) match
      case Failure(exception) => exception.printStackTrace()
      case Success(customer) => customer match
        case Left(value) => println(value)
        case Right(customerUnit) => println(customerUnit)
  }

  //"INSERT INTO Wine (wine_name, grape_variety, vintage_year, winery_name, price) VALUES (?, ?, ?, ?, ?)"
  test("Insert Wine") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    val wine = Wine(1, "test5", "test5", 1999, "test5", 100.1)
    implementation.setWine(wine) match
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => value match
        case Left(value) => println(value)
        case Right(result) =>
          println(result)
          implementation.deleteWine(wine)
  }

  test("Insert Customer") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    val customer = Customer(1, "test3", "test3", "test3")
    implementation.setCustomer(customer) match
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => value match
        case Left(value) => println(value)
        case Right(result) =>
          println(result)
          implementation.deleteCustomer(customer)
  }

    test("Update Wine") {
      val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
      val newWine = NewWine(1, "Gewürztraminer", "Pinot Noir", 1977, "Skynoodle", 13.65, 100)

      implementation.updateWine(newWine) match
        case Failure(exception) => exception.printStackTrace()
        case Success(value) => value match
          case Left(value) => println(value)
          case Right(value) => println(value)
    }

  test("Update Customer") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    val newCustomer = NewCustomer(2, "Ethelbert", "Cubin", "ecubin7@yale.edu", "ecubin7@yale.com")
    implementation.updateCustomer(newCustomer) match
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => value match
        case Left(value) => println(value)
        case Right(value) => println(value)
  }

  test("Delete Wine") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    val wine = Wine(1, "Zinfandel", "Cabernet Sauvignon", 1987, "Mybuzz", 543.86)
    implementation.deleteWine(wine) match
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => value match
        case Left(value) => println(value)
        case Right(value) => println(value)
  }

  test("Delete Customer") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    val customer = Customer(1, "Kurtis", "MacRory", "kmacrory9@google.co.jp")
    implementation.deleteCustomer(customer) match
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => value match
        case Left(value) => println(value)
        case Right(value) => println(value)
  }
}