package connector

import org.postgresql.util.PSQLException

import java.sql.{Connection, DriverManager}

case class PSQLconnection(host: String, port: String, database: String) {
  final val basePath = "jdbc:postgresql:"
//  final val URL = basePath + "//" + host + ":" + port + database
  final val URL = basePath + s"//$host:$port/$database"

  val username = "user"
  val password = "password"

  def getConnection: Connection = {
    try
      val connection = DriverManager.getConnection(URL, username, password)
      println("Connection success")
      connection
    catch
      case e: PSQLException =>
        println(e.printStackTrace())
        null


  }
}
