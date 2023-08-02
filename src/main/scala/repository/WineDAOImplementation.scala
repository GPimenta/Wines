package repository

import connector.PSQLconnection
import model.{Customer, NewCustomer, NewWine, Wine}

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Right, Success, Try}

case class WineDAOImplementation(connection: PSQLconnection) {

  private final val SELECT_ALL_WINES = "SELECT * FROM Wine;"
  private final val SELECT_ALL_CUSTOMERS = "SELECT * FROM Customer;"
  private final val QUERY_WINE_ID = "SELECT id, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE id = ?;"
  private final val QUERY_CUSTOMER_ID = "SELECT id, first_name, last_name, email FROM Customer WHERE id = ?;"
  private final val QUERY_WINE_OBJECT = "SELECT id, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE wine_name = ? AND grape_variety = ? AND vintage_year = ? AND winery_name = ? AND price = ?;"
  private final val QUERY_WINE_NAME = "SELECT id, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE wine_name = ?;"
  private final val QUERY_CUSTOMER = "SELECT id, first_name, last_name, email FROM Customer WHERE first_name = ? AND last_name = ? AND email = ?;"
  private final val QUERY_CUSTOMER_NAME_EMAIL = "SELECT id, first_name, last_name, email FROM Customer WHERE first_name = ? AND last_name = ? AND email = ?;"
  private final val INSERT_WINE = "INSERT INTO Wine (wine_name, grape_variety, vintage_year, winery_name, price) VALUES (?, ?, ?, ?, ?);"
  private final val INSERT_CUSTOMER = "INSERT INTO Customer (first_name, last_name, email) VALUES (?, ?, ?);"
  private final val UPDATE_WINE = "UPDATE Wine SET price = ? WHERE wine_name = ? AND grape_variety = ? AND vintage_year = ? AND winery_name = ? AND price = ?;"
  private final val UPDATE_CUSTOMER = "UPDATE Customer SET email = ? WHERE first_name = ? AND last_name = ? AND email = ?"
  private final val DELETE_WINE = "DELETE FROM Wine WHERE wine_name = ? AND grape_variety = ? AND vintage_year = ? AND winery_name = ? AND price = ?;"
  private final val DELETE_CUSTOMER = "DELETE FROM Customer WHERE first_name = ? AND last_name = ? AND email = ?;"

  def getAllWines: Try[Either[String, List[Wine]]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(SELECT_ALL_WINES)
      val resultSet = preparedStatement.executeQuery()
      var wineList = ListBuffer[Wine]()

      while (resultSet.next()) {
        val wine = Wine(resultSet.getInt("id"),
          resultSet.getString("wine_name"),
          resultSet.getString("grape_variety"),
          resultSet.getInt("vintage_year"),
          resultSet.getString("winery_name"),
          resultSet.getBigDecimal("price")
        )
        wineList += wine
      }
      preparedStatement.close()
      if wineList.nonEmpty then Right(wineList.toList) else Left("Empty list")
    }
  }

    def getAllCustomers: Try[Either[String, List[Customer]]] = {
      Try {
        val preparedStatement = connection.getConnection.prepareStatement(SELECT_ALL_CUSTOMERS)
        val resultSet = preparedStatement.executeQuery()
        var customerList = ListBuffer[Customer]()

        while (resultSet.next()) {
          val customer = Customer(resultSet.getInt("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("email")
          )
          customerList += customer
        }
        preparedStatement.close()
        if customerList.nonEmpty then Right(customerList.toList) else Left("Empty list")
      }
    }

  def getByIdWine(id: Integer): Try[Either[String, Wine]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_WINE_ID)
      preparedStatement.setInt(1, id)
      val resultSet = preparedStatement.executeQuery()

      if (resultSet.next()) {
        val wine = Right(Wine(resultSet.getInt("id"),
          resultSet.getString("wine_name"),
          resultSet.getString("grape_variety"),
          resultSet.getInt("vintage_year"),
          resultSet.getString("winery_name"),
          resultSet.getBigDecimal("price")
        ))
        preparedStatement.close()
        wine
      } else {
        preparedStatement.close()
        Left("Not found")
      }
    }
  }

    def getByIdCustomer(id: Integer): Try[Either[String, Customer]] = {
      Try {
        val preparedStatement = connection.getConnection.prepareStatement(QUERY_CUSTOMER_ID)
        preparedStatement.setInt(1, id)
        val resultSet = preparedStatement.executeQuery()
        var customer: Customer = null

        if (resultSet.next()) {
          val value = Right(Customer(resultSet.getInt("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("email")
          ))
          preparedStatement.close()
          value
        } else {
          Left("Not found")
        }
      }
    }

  //"INSERT INTO Wine (wine_name, grape_variety, vintage_year, winery_name, price) VALUES (?, ?, ?, ?, ?)"

  def getWine(wine: Wine): Try[Either[String, Wine]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_WINE_OBJECT)
      preparedStatement.setString(1, wine.wineName)
      preparedStatement.setString(2, wine.grapeVariety)
      preparedStatement.setInt(3, wine.vintageYear)
      preparedStatement.setString(4, wine.wineryName)
      preparedStatement.setBigDecimal(5, wine.price.bigDecimal)
      val resultSet = preparedStatement.executeQuery()

      if (resultSet.next()) {
        val wine = Wine(resultSet.getInt("id"),
          resultSet.getString("wine_name"),
          resultSet.getString("grape_variety"),
          resultSet.getInt("vintage_year"),
          resultSet.getString("winery_name"),
          resultSet.getBigDecimal("price")
        )
        preparedStatement.close()
        Right(wine)
      } else {
        preparedStatement.close()
        Left("Not found")
      }
    }
  }

  def getWineByName(wineName: String): Try[Either[String, List[Wine]]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_WINE_NAME)
      preparedStatement.setString(1, wineName)
      val resultSet = preparedStatement.executeQuery()
      var wineList = ListBuffer[Wine]()

      while (resultSet.next()) {
        val wine = Wine(resultSet.getInt("id"),
          resultSet.getString("wine_name"),
          resultSet.getString("grape_variety"),
          resultSet.getInt("vintage_year"),
          resultSet.getString("winery_name"),
          resultSet.getBigDecimal("price")
        )
        wineList += wine
      }
      preparedStatement.close()
      if wineList.nonEmpty then Right(wineList.toList) else Left("Empty list")
    }
  }



  def getCustomer(customer: Customer): Try[Either[String, Customer]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_CUSTOMER)
      preparedStatement.setString(1, customer.firstName)
      preparedStatement.setString(2, customer.lastName)
      preparedStatement.setString(3, customer.email)
      val resultSet = preparedStatement.executeQuery()

      if (resultSet.next()) {
        val customer = Customer(resultSet.getInt("id"),
          resultSet.getString("first_name"),
          resultSet.getString("last_name"),
          resultSet.getString("email")
        )
        preparedStatement.close()
        Right(customer)
      } else {
        preparedStatement.close()
        Left("Not found")
      }
    }
  }

  def getCustomerByNameEmail(firstName: String, lastName: String, email:String): Try[Either[String, Customer]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_CUSTOMER_NAME_EMAIL)
      preparedStatement.setString(1, firstName)
      preparedStatement.setString(2, lastName)
      preparedStatement.setString(3, email)
      val resultSet = preparedStatement.executeQuery()

      if (resultSet.next()) {
        val customer = Customer(resultSet.getInt("id"),
          resultSet.getString("first_name"),
          resultSet.getString("last_name"),
          resultSet.getString("email")
        )
        preparedStatement.close()
        Right(customer)
      } else {
        preparedStatement.close()
        Left("Not found")
      }
    }
  }


//"INSERT INTO Wine (wine_name, grape_variety, vintage_year, winery_name, price) VALUES (?, ?, ?, ?, ?)"
  def setWine(wine: Wine): Try[Either[String, Wine]] = {
    Try {
      getWine(wine) match
        case Failure(exception) => Left(exception.printStackTrace().toString)
        case Success(result) => result match
          case Right(found) => Right(found)
          case Left(value) =>
            val con = connection.getConnection
            val preparedStatement = con.prepareStatement(INSERT_WINE)
            preparedStatement.setString(1, wine.wineName)
            preparedStatement.setString(2, wine.grapeVariety)
            preparedStatement.setInt(3, wine.vintageYear)
            preparedStatement.setString(4, wine.wineryName)
            preparedStatement.setBigDecimal(5, wine.price.bigDecimal)

            preparedStatement.executeUpdate match
              case 1 => Right(wine)
              case 0 => Left("No Insertion")
    }
  }

  def setCustomer(customer: Customer): Try[Either[String, Customer]] = {
    Try {
      getCustomer(customer) match
        case Failure(exception) => Left(exception.printStackTrace().toString)
        case Success(result) => result match
          case Right(found) => Right(found)
          case Left(value) =>
            val con = connection.getConnection
            val preparedStatement = con.prepareStatement(INSERT_CUSTOMER)
            preparedStatement.setString(1, customer.firstName)
            preparedStatement.setString(2, customer.lastName)
            preparedStatement.setString(3, customer.email)

            preparedStatement.executeUpdate match
              case 1 => Right(customer)
              case 0 => Left("No Insertion")
    }
  }
  //"UPDATE Wine SET price = ? WHERE wine_name = ? AND grape_variety = ? AND vintage_year = ? AND winery_name = ? AND price = ?"
  // Only Price can change
  def updateWine(newWine: NewWine): Try[Either[String, String]] = {
    Try {
      getWine(Wine(newWine.id, newWine.wineName, newWine.grapeVariety, newWine.vintageYear, newWine.wineryName, newWine.price)) match
        case Failure(exception) => Left(exception.printStackTrace().toString)
        case Success(result) => result match
          case Left(notFound) => Left(notFound)
          case Right(wine) =>
            val preparedStatement = connection.getConnection.prepareStatement(UPDATE_WINE)
            preparedStatement.setBigDecimal(1, newWine.newPrice.bigDecimal)
            preparedStatement.setString(2, newWine.wineName)
            preparedStatement.setString(3, newWine.grapeVariety)
            preparedStatement.setInt(4, newWine.vintageYear)
            preparedStatement.setString(5, newWine.wineryName)
            preparedStatement.setBigDecimal(6, newWine.price.bigDecimal)

            preparedStatement.executeUpdate match
              case 1 => Right("Updated")
              case 0 => Left("No update")
    }
  }

  def updateCustomer(newCustomer: NewCustomer): Try[Either[String, String]] = {
    Try {
      getCustomer(Customer(newCustomer.id, newCustomer.firstName, newCustomer.lastName, newCustomer.email)) match
        case Failure(exception) => Left(exception.printStackTrace().toString)
        case Success(result) => result match
          case Left(notFound) => Left(notFound)
          case Right(customer) =>
            val preparedStatement = connection.getConnection.prepareStatement(UPDATE_CUSTOMER)
            preparedStatement.setString(1, newCustomer.newEmail)
            preparedStatement.setString(2, newCustomer.firstName)
            preparedStatement.setString(3, newCustomer.lastName)
            preparedStatement.setString(4, newCustomer.email)

            preparedStatement.executeUpdate match
              case 1 => Right("Updated")
              case 0 => Left("No update")
    }
  }

  def deleteWine(wine: Wine): Try[Either[String, String]] = {
    Try {
      getWine(wine) match
        case Failure(exception) => Left(exception.printStackTrace().toString)
        case Success(result) => result match
          case Left(notFound) => Left(notFound)
          case Right(customer) =>
            val preparedStatement = connection.getConnection.prepareStatement(DELETE_WINE)
            preparedStatement.setString(1, wine.wineName)
            preparedStatement.setString(2, wine.grapeVariety)
            preparedStatement.setInt(3, wine.vintageYear)
            preparedStatement.setString(4, wine.wineryName)
            preparedStatement.setBigDecimal(5, wine.price.bigDecimal)

            preparedStatement.executeUpdate() match
              case 1 => Right("Deleted")
              case 0 => Left("Not deleted")
    }
  }

  def deleteCustomer(customer: Customer): Try[Either[String, String]] = {
    Try {
      getCustomer(customer) match
        case Failure(exception) => Left(exception.printStackTrace().toString)
        case Success(result) => result match
          case Left(notFound) => Left(notFound)
          case Right(customer) =>
            val preparedStatement = connection.getConnection.prepareStatement(DELETE_CUSTOMER)
            preparedStatement.setString(1, customer.firstName)
            preparedStatement.setString(2, customer.lastName)
            preparedStatement.setString(3, customer.email)

            preparedStatement.executeUpdate() match
              case 1 => Right("Deleted")
              case 0 => Left("Not deleted")
    }
  }
}
