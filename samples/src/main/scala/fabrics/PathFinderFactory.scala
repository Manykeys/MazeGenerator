package fabrics

import creatures.PathFinder
import pathFinders.{AStarSearch, BreadthSearch}

object PathFinderFactory {
  def createPathFinder(
      algorithm: String
  ): Either[IllegalArgumentException, PathFinder] = {
    algorithm.toLowerCase match {
      case "astar" => Right(new AStarSearch())
      case "bfs"   => Right(new BreadthSearch())
      case _ => Left(new IllegalArgumentException(s"Не реализован: $algorithm"))
    }
  }
}
