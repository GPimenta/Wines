import connector.PSQLconnection

object Testing {
  def main(args: Array[String]): Unit = {
    println("Hi")
// have to create database in POSTGRESSQL
    val schema = PSQLconnection("localhost", "5432", "WINE_SHOP").getConnection.isValid(1000)
    println(schema)
  }
}
////@main def Hello(): Unit =
////  print("hello")
//}
//@main def Hello(params: String*): Unit =
//  print("hello: ")
//  params.map(s => s.toUpperCase).foreach(s => printf("%s ", s))
//  println(" ")
