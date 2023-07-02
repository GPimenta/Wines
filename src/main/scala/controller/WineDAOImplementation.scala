package controller

import connector.PSQLconnection
import model.{Customer, Wine}

import scala.collection.mutable.ListBuffer
import scala.util.{Right, Try}

case class WineDAOImplementation(connection: PSQLconnection) {

  private final val SELECT_ALL_WINES = "SELECT * FROM Wine;"
  private final val SELECT_ALL_CUSTOMERS = "SELECT * FROM Customer;"
  private final val QUERY_WINE_ID = "SELECT id, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE id = ?;"
  private final val QUERY_CUSTOMER_ID = "SELECT id, first_name, last_name, email FROM Customer WHERE id = ?;"
  private final val QUERY_WINE_NAME = "SELECT id, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE wine_name = ?;"
  private final val QUERY_CUSTOMER_FULL_NAME = "SELECT id, first_name, last_name, email FROM Customer WHERE first_name = ? AND last_name = ?;"
  private final val INSERT_WINE = "INSERT INTO Wine (wine_name, grape_variety, vintage_year, winery_name, price) VALUES (?, ?, ?, ?, ?)"
  private final val INSERT_CUSTOMER = "INSERT INTO Customer (first_name, last_name, email) VALUES (?, ?, ?)"



  def getAllWines: Try[List[Wine]] = {
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
      wineList.toList
    }
  }

    def getAllCustomers: Try[List[Customer]] = {
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
        customerList.toList
      }
    }

  def getByIdWine(id: Integer): Try[Either[String, Wine]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_WINE_ID)
      preparedStatement.setInt(1, id)
      val resultSet = preparedStatement.executeQuery()
      var wine: Wine = null

      if (resultSet.next()) {
        val value = Right(Wine(resultSet.getInt("id"),
          resultSet.getString("wine_name"),
          resultSet.getString("grape_variety"),
          resultSet.getInt("vintage_year"),
          resultSet.getString("winery_name"),
          resultSet.getBigDecimal("price")
        ))
        preparedStatement.close()
        value
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


  def getByNameWine(name: String): Try[Either[String,List[Wine]]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_WINE_NAME)
      preparedStatement.setString(1, name)
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
      if wineList.nonEmpty then Right(wineList.toList) else Left("Not found")
    }
  }

  def getByNameCustomer(firstName: String, lastName: String, email: String): Try[Either[String, Customer]] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_CUSTOMER_FULL_NAME)
      preparedStatement.setString(1, firstName)
      preparedStatement.setString(2, lastName)
      preparedStatement.setString(3, email)
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
        preparedStatement.close()
        Left("Not found")
      }
    }
  }
//"INSERT INTO Wine (wine_name, grape_variety, vintage_year, winery_name, price) VALUES (?, ?, ?, ?, ?)"
  def setWine(wine_name: String, grape_variety: String, vintage_year: Integer, winery_name: String, price: BigDecimal): Try[Either[String, Boolean]] = {
    Try {
      val con = connection.getConnection
      val preparedStatement = con.prepareStatement(INSERT_WINE)
      preparedStatement.setString(1, wine_name)
      preparedStatement.setString(2, grape_variety)
      preparedStatement.setInt(3, vintage_year)
      preparedStatement.setString(4, winery_name)
      preparedStatement.setBigDecimal(5, price.bigDecimal)

      preparedStatement.executeUpdate match
        case 1 => Right(true)
        case 0 => Left("No Insertion")
        case _ => Right(false)

    }
  }


  def setCustomer(customer: Customer): Try[Either[String, Boolean]] = {
    Try {
      val con = connection.getConnection
      val preparedStatement = con.prepareStatement(INSERT_CUSTOMER)
      preparedStatement.setString(1, customer.firstName)
      preparedStatement.setString(2, customer.lastName)
      preparedStatement.setString(3, customer.email)

      preparedStatement.executeUpdate match
        case 1 => Right(true)
        case 0 => Left("No Insertion")
        case _ => Right(false)
    }
  }
}
