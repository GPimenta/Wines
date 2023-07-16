package controller

import connector.PSQLconnection
import model.{Customer, Wine}
import org.scalatest.funsuite.AnyFunSuiteLike
import repository.WineDAOImplementation

class WineControllerTest extends AnyFunSuiteLike {

  final val connection = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
  final val controller = WineController(connection)

  test("testGetAllWines") {
    controller.getAllWines match
      case Left(noValue) => println(noValue)
      case Right(wineList) => println(wineList)
  }

  test("testGetAllCustomers") {
    controller.getAllCustomers match
      case Left(noValue) => println(noValue)
      case Right(customerList) => println(customerList)
  }

  test("testGetWineById") {
    controller.getWineById(1) match
      case Left(noValue) => println(noValue)
      case Right(wine) => println(wine)
  }

  test("testGetCustomerById") {
    controller.getCustomerById(1) match
      case Left(noValue) => println(noValue)
      case Right(customer) => println(customer)
  }

  test("testGetWine") {
    controller.getWine(Wine(1, "Syrah", "Merlot", 1988, "Cogibox", 520.08)) match
      case Left(noValue) => println(noValue)
      case Right(value) => println(value)
  }

  test("testGetCustomer") {
    controller.getCustomer(Customer(1, "Phedra", "Goffe", "pgoffe6@salon.com")) match
      case Left(noValue) => println(noValue)
      case Right(value) => println(value)

  }

  test("testSetWine") {
    controller.setWine(Wine(1, "test6", "test6", 1999, "test6", 100.1)) match
      case Left(noValue) => println(noValue)
      case Right(value) => println(value)
  }

  test("testSetCustomer") {
    controller.setWine(Wine(1, "test6", "test6", 1999, "test6", 100.1)) match
      case Left(noValue) => println(noValue)
      case Right(value) => println(value)
  }

  test("testDeleteWine") {
    controller.deleteWine(Wine(1, "Zinfandel", "Cabernet Sauvignon", 1987, "Mybuzz", 543.86)) match
      case Left(noValue) => println(noValue)
      case Right(value) => println(value)
  }

  test("testDeleteCustomer") {
    controller.deleteCustomer(Customer(1, "Kurtis", "MacRory", "kmacrory9@google.co.jp")) match
      case Left(noValue) => println(noValue)
      case Right(value) => println(value)
  }
}
