package controller

import repository.WineDAOImplementation
import model.{Customer, NewCustomer, NewWine, Wine}
import scala.util.{Failure, Success}

class WineController(wineDAOImplementation: WineDAOImplementation) {

  def getAllWines: Either[String, List[Wine]] = {
    wineDAOImplementation.getAllWines match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(wineList => wineList)
  }

  def getAllCustomers: Either[String, List[Customer]] = {
    wineDAOImplementation.getAllCustomers match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(customerList => customerList)
  }

  def getWineById(id: Integer): Either[String, Wine] = {
    wineDAOImplementation.getByIdWine(id) match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(wine => wine)
  }

  def getCustomerById(id: Integer): Either[String, Customer] = {
    wineDAOImplementation.getByIdCustomer(id) match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(customer => customer)
  }

  def getWine(wine: Wine): Either[String, Wine] = {
    wineDAOImplementation.getWine(wine) match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(wineUnit => wineUnit)
  }

  def getCustomer(customer: Customer): Either[String, Customer] = {
    wineDAOImplementation.getCustomer(customer) match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(customerUnit => customerUnit)
  }

  def setWine(wine: Wine): Either[String, Wine] = {
    wineDAOImplementation.setWine(wine) match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(wineUnit => wineUnit)
  }

  def setCustomer(customer: Customer): Either[String, Customer] = {
    wineDAOImplementation.setCustomer(customer) match
      case Failure(exception) => Left(exception.printStackTrace().toString)
      case Success(result) => result.map(customerUnit => customerUnit)
  }

}
