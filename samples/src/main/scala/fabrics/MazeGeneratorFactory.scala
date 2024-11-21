package fabrics

import creatures.MazeGenerator
import mazeGeneratorAlgo.{MazeKruskalAlgo, MazePrimAlgo}

object MazeGeneratorFactory {
  def createMazeGenerator(
      algorithm: String
  ): Either[IllegalArgumentException, MazeGenerator] =
    algorithm.toLowerCase match {
      case "prim"    => Right(new MazePrimAlgo())
      case "kruskal" => Right(new MazeKruskalAlgo())
      case _ => Left(new IllegalArgumentException(s"Не реализован: $algorithm"))
    }
}
