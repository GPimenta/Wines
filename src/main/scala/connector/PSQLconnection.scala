package connector

import org.postgresql.util.PSQLException

import java.net.Socket
import java.sql.{Connection, DriverManager}
import java.util.concurrent.{Executor, Executors}

case class PSQLconnection(host: String, port: String, database: String) {
  final val basePath = "jdbc:postgresql:"
//  final val URL = basePath + "//" + host + ":" + port + database
  final val URL = basePath + s"//$host:$port/$database"

  val username = "postgres"
  val password = "postgres"

  def getConnection: Connection = {
    try
      val connection = DriverManager.getConnection(URL, username, password)
      connection.setNetworkTimeout(Executors.newSingleThreadExecutor(),5000)
      connection.setAutoCommit(false)

      if connection.isValid(1000) 
      then 
        println("Connection success") 
        connection 
      else 
        println(" Failed to make connection")
        null

    catch
      case e: PSQLException =>
        println(e.printStackTrace())
        null
  }
}
