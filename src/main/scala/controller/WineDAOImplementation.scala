package controller

import connector.PSQLconnection
import model.Wine

import scala.collection.mutable.ListBuffer
import scala.util.Try

case class WineDAOImplementation(connection: PSQLconnection) {

  private final val SELECT_ALL_QUERY = "SELECT * FROM Wine;"
  private final val QUERY = "SELECT id, first_name, last_name, email, wine_name, grape_variety, vintage_year, winery_name, price FROM Wine WHERE id = ?;"

  def getAll: Try[List[Wine]] =
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(SELECT_ALL_QUERY)
      val resultSet = preparedStatement.executeQuery()
      var wineList = ListBuffer[Wine]()

      while (resultSet.next()) {
        val wine = Wine(resultSet.getInt("id"),
          resultSet.getString("first_name"),
          resultSet.getString("last_name"),
          resultSet.getString("email"),
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

  def getById(id: Integer): Try[Wine] =
    Try {
      val preparedStatement = connection.getConnection.prepareStatement(QUERY)
      preparedStatement.setInt(1, id)
      val resultSet = preparedStatement.executeQuery()
      var wine:Wine = null

      while (resultSet.next()) {
        wine = Wine(resultSet.getInt("id"),
          resultSet.getString("first_name"),
          resultSet.getString("last_name"),
          resultSet.getString("email"),
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
