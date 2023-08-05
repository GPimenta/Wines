package controller

import akka.actor.Status.Success
import connector.PSQLconnection
import model.{Customer, NewCustomer, NewWine, Wine}
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

  "receives a POST action" when {
    "creates a Wine" should {
      "returns right with the Wine" in {
        val dao = mock[WineDAOImplementation]
        val wine = Wine(1, "test", "test", 2000, "test", 10.0)

        when(dao.setWine(wine)).thenReturn(Try(Right(wine)))

        val result = WineController(dao).setWine(wine)

        assert(result == Right(wine))
      }
    }
  }

  "receives a POST action" when {
    "does not create a Wine" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val wine = Wine(1, "test", "test", 2000, "test", 10.0)
        val message = "No created"

        when(dao.setWine(wine)).thenReturn(Try(Left(message)))

        val result = WineController(dao).setWine(wine)

        assert(result == Left(message))
      }
    }
  }

  "receives a POST action" when {
    "creates a Customer" should {
      "returns right with the Customer" in {
        val dao = mock[WineDAOImplementation]
        val customer = Customer(1, "test", "test", "test")

        when(dao.setCustomer(customer)).thenReturn(Try(Right(customer)))

        val result = WineController(dao).setCustomer(customer)

        assert(result == Right(customer))
      }
    }
  }

  "receives a POST action" when {
    "does not create a Customer" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val customer = Customer(1, "test", "test", "test")
        val message = "No created"

        when(dao.setCustomer(customer)).thenReturn(Try(Left(message)))

        val result = WineController(dao).setCustomer(customer)

        assert(result == Left(message))
      }
    }
  }

  "receives a PUT action" when {
    "Updates a Wine" should {
      "returns right with a message" in {
        val dao = mock[WineDAOImplementation]
        val newWine = NewWine(1, "test", "test", 2000, "test", 10.0, 20.0)
        val wineUpdated = Wine(1, "test", "test", 2000, "test", 20.0)
        val message = "updated"


        when(dao.updateWine(newWine)).thenReturn(Try(Right(message)))

        val result = WineController(dao).updateWine(newWine)

        assert(result == Right(message))
      }
    }
  }

  "receives a PUT action" when {
    "Does not updates a Wine" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val newWine = NewWine(1, "test", "test", 2000, "test", 10.0, 20.0)
        val wineUpdated = Wine(1, "test", "test", 2000, "test", 20.0)
        val message = "not updated"


        when(dao.updateWine(newWine)).thenReturn(Try(Left(message)))

        val result = WineController(dao).updateWine(newWine)

        assert(result == Left(message))
      }
    }
  }

  "receives a PUT action" when {
    "Updates a Customer" should {
      "returns right with a message" in {
        val dao = mock[WineDAOImplementation]
        val newCustomer = NewCustomer(1, "test", "test", "test", "test2")
        val customerUpdated = Customer(1, "test", "test", "test2")
        val message = "updated"


        when(dao.updateCustomer(newCustomer)).thenReturn(Try(Right(message)))

        val result = WineController(dao).updateCustomer(newCustomer)

        assert(result == Right(message))
      }
    }
  }

  "receives a PUT action" when {
    "Does not updates a Customer" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val newCustomer = NewCustomer(1, "test", "test", "test", "test2")
        val customerUpdated = Customer(1, "test", "test", "test2")
        val message = "not updated"


        when(dao.updateCustomer(newCustomer)).thenReturn(Try(Left(message)))

        val result = WineController(dao).updateCustomer(newCustomer)

        assert(result == Left(message))
      }
    }
  }

  "receives a Delete action" when {
    "Deletes a Wine" should {
      "returns right with a message" in {
        val dao = mock[WineDAOImplementation]
        val wineDeleted = Wine(1, "test", "test", 2000, "test", 20.0)
        val message = "deleted"


        when(dao.deleteWine(wineDeleted)).thenReturn(Try(Right(message)))

        val result = WineController(dao).deleteWine(wineDeleted)

        assert(result == Right(message))
      }
    }
  }

  "receives a Delete action" when {
    "Does not delete a Wine" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val wineDeleted = Wine(1, "test", "test", 2000, "test", 20.0)
        val message = "not deleted"


        when(dao.deleteWine(wineDeleted)).thenReturn(Try(Left(message)))

        val result = WineController(dao).deleteWine(wineDeleted)

        assert(result == Left(message))
      }
    }
  }

  "receives a Delete action" when {
    "Deletes a Customer" should {
      "returns right with a message" in {
        val dao = mock[WineDAOImplementation]
        val customerDeleted = Customer(1, "test", "test", "test2")
        val message = "deleted"


        when(dao.deleteCustomer(customerDeleted)).thenReturn(Try(Right(message)))

        val result = WineController(dao).deleteCustomer(customerDeleted)

        assert(result == Right(message))
      }
    }
  }

  "receives a Delete action" when {
    "Does not delete a Customer" should {
      "returns left with a message" in {
        val dao = mock[WineDAOImplementation]
        val customerDeleted = Customer(1, "test", "test", "test2")
        val message = "not deleted"


        when(dao.deleteCustomer(customerDeleted)).thenReturn(Try(Left(message)))

        val result = WineController(dao).deleteCustomer(customerDeleted)

        assert(result == Left(message))
      }
    }
  }

}
