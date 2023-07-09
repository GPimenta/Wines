package controller

import repository.WineDAOImplementation

import scala.util.{Failure, Success}

class WineController(wineDAOImplementation: WineDAOImplementation) {

  def getAllWines: Either[String, List[Wines]] = {
    val triedEither = wineDAOImplementation.getAllWines.map(wine => wine)
//    wineDAOImplementation.getAllWines match
//      case Failure(exception) => Left(exception.getMessage)
//      case Success(result) => result
  }

}
