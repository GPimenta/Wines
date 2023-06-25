package controller

import connector.PSQLconnection
import model.Wine

import scala.collection.mutable.ListBuffer

case class WineDAOImplementation(connection: PSQLconnection) {

  final val QUERY = "SELECT * FROM Wine LIMIT 10;"
  
  
  def getAll: Option[List[Wine]] =
    val statement = connection.getConnection.createStatement()
    val resultSet = statement.executeQuery(QUERY)
    var wineList = ListBuffer[Wine]()

    while(resultSet.next()) {
//      print(s"ID: ${resultSet.getInt("id")}")
//      print(s" First_name: ${resultSet.getString("first_name")} ")
//      print(s" Last_name: ${resultSet.getString("last_name")} ")
//      print(s" Email: ${resultSet.getString("email")} ")
//      print(s" Wine_name: ${resultSet.getString("wine_name")} ")
//      print(s" Grape_name: ${resultSet.getString("wine_name")} ")
//      print(s" Vintage_year: ${resultSet.getInt("vintage_year")} ")
//      print(s" Winery_name: ${resultSet.getString("winery_name")} ")
//      print(s" Price: ${resultSet.getBigDecimal("price")} ")

      val wine = Wine(resultSet.getInt("id"),
        resultSet.getString("first_name"),
        resultSet.getString("last_name"),
        resultSet.getString("email"),
        resultSet.getString("wine_name"),
        resultSet.getString("wine_name"),
        resultSet.getInt("vintage_year"),
        resultSet.getString("winery_name"),
        resultSet.getBigDecimal("price")
      )
      wineList += wine
    }
    statement.close()
    Some(wineList.toList)
}
