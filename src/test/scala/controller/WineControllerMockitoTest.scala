package controller

import akka.actor.Status.Success
import connector.PSQLconnection
import model.{Customer, Wine}
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import repository.WineDAOImplementation
import org.mockito.Mockito.*

import scala.util.Try


class WineControllerMockitoTest extends AnyWordSpec with MockitoSugar {

  "receives a GET action" when {
    "delivers a List of Wines" should {
      "return right list of wines" in {
        val dao = mock[WineDAOImplementation]
        val wine = Wine(1, "test", "test", 2000, "test", 10.0)
        when(dao.getAllWines).thenReturn(Try(Right(List(wine))))

        val wines = WineController(dao).getAllWines

        assert(wines == Right(List(wine)))
      }
    }
  }

  "receives a GET action" when {
    "delivers an empty List of Wines" should {
      "return left with a message" in {
        val dao = mock[WineDAOImplementation]
        val message = "Empty List"
        when(dao.getAllWines).thenReturn(Try(Left("Empty List")))

        val wines = WineController(dao).getAllWines

        assert(wines == Left(message))
      }
    }
  }

  "receives a GET action" when {
    "delivers a List of Customers" should {
      "return right list of customers" in {
        val dao = mock[WineDAOImplementation]
        val customer = Customer(1, "test", "test", "test")
        when(dao.getAllCustomers).thenReturn(Try(Right(List(customer))))

        val wines = WineController(dao).getAllCustomers

        assert(wines == Right(List(customer)))
      }
    }
  }

  "receives a GET action" when {
    "delivers an empty List of Customers" should {
      "return left with a message" in {
        val dao = mock[WineDAOImplementation]
        val message = "Empty List"
        when(dao.getAllCustomers).thenReturn(Try(Left(message)))

        val customer = WineController(dao).getAllCustomers

        assert(customer == Left(message))
      }
    }
  }

  "receives a GET action" when {
    "delivers Wine by Id" should {
      "returns right Wine" in {
        val dao = mock[WineDAOImplementation]
        val id = 1
        val wine = Wine(1, "test", "test", 2000, "test", 10.0)
        when(dao.getByIdWine(id)).thenReturn(Try(Right(wine)))

        val wineById = WineController(dao).getWineById(id)

        assert(wineById == Right(wine))
      }
    }
  }

  "receives a GET action" when {
    "does not find wine" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val id = 1
        val wine = Wine(1, "test", "test", 2000, "test", 10.0)
        when(dao.getByIdWine(id)).thenReturn(Try(Left("not found")))

        val wineById = WineController(dao).getWineById(id)

        assert(wineById == Left("not found"))
      }
    }
  }

  "receives a GET action" when {
    "delivers Customer by Id" should {
      "returns right Customer" in {
        val dao = mock[WineDAOImplementation]
        val id = 1
        val customer = Customer(1, "test", "test", "test")
        when(dao.getByIdCustomer(id)).thenReturn(Try(Right(customer)))

        val customerById = WineController(dao).getCustomerById(id)

        assert(customerById == Right(customer))
      }
    }
  }

  "receives a GET action" when {
    "does not find customer" should {
      "returns Left with a message" in {
        val dao = mock[WineDAOImplementation]
        val id = 1
        val message = "not found"
        when(dao.getByIdCustomer(id)).thenReturn(Try(Left(message)))

        val customerById = WineController(dao).getCustomerById(id)

        assert(customerById == Left(message))
      }
    }
  }

  "receives a GET action" when {
    "delivers a small list of Wines" should {
      "returns right with the List of Wines" in {
        val dao = mock[WineDAOImplementation]
        val wine = "test"
        when(dao.getWineByName(wine)).thenReturn(Try(Right(List(wine))))

        val wineList = WineController(dao).getWineByName(wine)

        assert(wineList == Right(List(wine)))
      }
    }
  }

  "receives a GET action" when {
    "there is no Wine with that name" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val wine = "test"
        val message = "not found"
        when(dao.getWineByName(wine)).thenReturn(Try(Left(message)))

        val result = WineController(dao).getWineByName(wine)

        assert(result == Left(message))
      }
    }
  }

  "receives a GET action" when {
    "delivers a Costumer" should {
      "returns right with the Customer" in {
        val dao = mock[WineDAOImplementation]
        val firstName = "test"
        val lastName = "test"
        val email = "test"
        val customer = Customer(1, "test", "test", "test")

        when(dao.getCustomerByNameEmail(firstName, lastName, email)).thenReturn(Try(Right(customer)))

        val result = WineController(dao).getCustomerByNameEmail(firstName, lastName, email)

        assert(result == Right(customer))
      }
    }
  }

}
