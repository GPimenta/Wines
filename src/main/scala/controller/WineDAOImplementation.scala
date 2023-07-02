package controller

import connector.PSQLconnection
import model.{Customer, Wine}

import scala.collection.mutable.ListBuffer
import scala.util.Try

case class WineDAOImplementation(connection: PSQLconnection) {

  private final val SELECT_ALL_WINES = "SELECT * FROM Wine;"
  private final val SELECT_ALL_CUSTOMERS = "SELECT * FROM Customer;"
  private final val QUERY_WINE_ID = "SELECT id, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE id = ?;"
  private final val QUERY_CUSTOMER_ID = "SELECT id, first_name, last_name, email FROM Customer WHERE id = ?;"
  private final val QUERY_WINE_NAME = "SELECT id, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE wine_name = ?;"
  private final val QUERY_CUSTOMER_FULL_NAME = "SELECT id, first_name, last_name, email FROM Customer WHERE first_name = ? AND last_name = ?;"



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

  def getByIdWine(id: Integer): Try[Wine] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_WINE_ID)
      preparedStatement.setInt(1, id)
      val resultSet = preparedStatement.executeQuery()
      var wine: Wine = null

      while (resultSet.next()) {
        wine = Wine(resultSet.getInt("id"),
          resultSet.getString("wine_name"),
          resultSet.getString("grape_variety"),
          resultSet.getInt("vintage_year"),
          resultSet.getString("winery_name"),
          resultSet.getBigDecimal("price")
        )
      }
      wine
    }
  }

    def getByIdCustomer(id: Integer): Try[Customer] = {
      Try {
        val preparedStatement = connection.getConnection.prepareStatement(QUERY_CUSTOMER_ID)
        preparedStatement.setInt(1, id)
        val resultSet = preparedStatement.executeQuery()
        var customer: Customer = null

        while (resultSet.next()) {
          customer = Customer(resultSet.getInt("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("email")
          )
        }
        customer
      }
    }


  def getByNameWine(name: String): Try[List[Wine]] = {
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
      wineList.toList
    }
  }

  def getByNameCustomer(firstName: String, lastName: String): Try[Customer] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_CUSTOMER_FULL_NAME)
      preparedStatement.setString(1, firstName)
      preparedStatement.setString(2, lastName)
      val resultSet = preparedStatement.executeQuery()
      var customer: Customer = null

      while (resultSet.next()) {
        customer = Customer(resultSet.getInt("id"),
          resultSet.getString("first_name"),
          resultSet.getString("last_name"),
          resultSet.getString("email")
        )
      }
      customer
    }
  }

  def setWine(wine_name: String, grape_variety: String, vintage_year: Integer, winery_name: String, price: BigDecimal): Try[Boolean] = {
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY_CUSTOMER_FULL_NAME)

    }
  }

}
