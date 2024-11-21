package creatures

import scala.util.Random

class CellConverter {
  private val random = new Random()

  def convertMaze(maze: Maze): Maze = {
    val convertedCells = maze.cells.map { case (coords, cell) =>
      coords -> convert(cell)
    }

    val convertedEdges = maze.edges.map { edge =>
      val newStart = convertedCells((edge.a.x, edge.a.y))
      val newEnd = convertedCells((edge.b.x, edge.b.y))
      Edge(newStart, newEnd)
    }

    val convertedEntrance = convertedCells((maze.entrance.x, maze.entrance.y))
    val convertedExit = convertedCells((maze.exit.x, maze.exit.y))

    Maze(
      maze.width,
      maze.height,
      convertedEdges,
      convertedEntrance,
      convertedExit,
      convertedCells
    )
  }

  private def convert(cell: Cell): Cell = {
    random.nextInt(100) match {
      case x if x < 60 => DefaultCell(cell.x, cell.y)
      case x if x < 75 => DesertCell(cell.x, cell.y)
      case _           => CoinCell(cell.x, cell.y)
    }
  }
}
