package controller

import repository.WineDAOImplementation
import model.{Customer, NewCustomer, NewWine, Wine}
import scala.util.{Failure, Success}

case class WineController(wineDAOImplementation: WineDAOImplementation) {

  def getAllWines: Either[String, List[Wine]] = {
    wineDAOImplementation.getAllWines match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(wineList => wineList)
  }

  def getAllCustomers: Either[String, List[Customer]] = {
    wineDAOImplementation.getAllCustomers match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(customerList => customerList)
  }

  def getWineById(id: Integer): Either[String, Wine] = {
    wineDAOImplementation.getByIdWine(id) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(wine => wine)
  }

  def getCustomerById(id: Integer): Either[String, Customer] = {
    wineDAOImplementation.getByIdCustomer(id) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(customer => customer)
  }

  private def getWine(wine: Wine): Either[String, Wine] = {
    wineDAOImplementation.getWine(wine) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(wineUnit => wineUnit)
  }

  def getWineByName(wineName: String): Either[String, List[Wine]] = {
    wineDAOImplementation.getWineByName(wineName) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(wineList => wineList)
  }

  def getCustomer(customer: Customer): Either[String, Customer] = {
    wineDAOImplementation.getCustomer(customer) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(customerUnit => customerUnit)
  }

  def getCustomerByNameEmail(firstName: String, lastName: String, email:String): Either[String, Customer] = {
    wineDAOImplementation.getCustomerByNameEmail(firstName, lastName, email) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(customerUnit => customerUnit)
  }

  def setWine(wine: Wine): Either[String, Wine] = {
    wineDAOImplementation.setWine(wine) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(wineUnit => wineUnit)
  }

  def setCustomer(customer: Customer): Either[String, Customer] = {
    wineDAOImplementation.setCustomer(customer) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(customerUnit => customerUnit)
  }

  def updateWine(newWine: NewWine): Either[String, String] = {
    wineDAOImplementation.updateWine(newWine) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(updated => updated)
  }

  def updateCustomer(newCustomer: NewCustomer): Either[String, String] = {
    wineDAOImplementation.updateCustomer(newCustomer) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(updated => updated)
  }

  def deleteWine(wine: Wine): Either[String, String] = {
    wineDAOImplementation.deleteWine(wine) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(response => response)
  }
  
  def deleteCustomer(customer: Customer): Either[String, String] = {
    wineDAOImplementation.deleteCustomer(customer) match
      case Failure(exception) => Left("Exception occurred while accessing DB")
      case Success(result) => result.map(response => response)
  }

}
