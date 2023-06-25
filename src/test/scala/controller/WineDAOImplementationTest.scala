package controller

import connector.PSQLconnection
import org.scalatest.funsuite.AnyFunSuite

class WineDAOImplementationTest extends AnyFunSuite {

  test("Test Connection") {
    val implementation = WineDAOImplementation(PSQLconnection("localhost", "5432", "WINE_SHOP"))
    val all = implementation.getAll.get
    println(all)
    assert(1==1)
  }

}
