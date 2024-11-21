package mazeGeneratorAlgo

import creatures.*

import scala.annotation.tailrec
import scala.util.Random

class MazePrimAlgo extends MazeGenerator {

  def generateMaze(
      width: Int,
      height: Int,
      entrance: Cell,
      exit: Cell
  ): Maze = {
    val cells: Map[(Int, Int), Cell] = (for {
      x <- 0 until width
      y <- 0 until height
    } yield (x, y) -> DefaultCell(x, y)).toMap

    @tailrec
    def step(
        frontier: Set[Edge],
        visited: Set[Cell],
        maze: Set[Edge]
    ): Set[Edge] = {
      if (frontier.isEmpty) maze
      else {
        val randomEdge = Random.shuffle(frontier.toList).head
        val Edge(a, b) = randomEdge
        if (!visited.contains(b)) {
          val newFrontier = frontier ++ neighbors(b, width, height)
            .map(Edge(b, _))
            .filterNot(e => visited.contains(e.b))
          step(newFrontier - randomEdge, visited + b, maze + randomEdge)
        } else {
          step(frontier - randomEdge, visited, maze)
        }
      }
    }

    val initialFrontier =
      neighbors(entrance, width, height).map(Edge(entrance, _)).toSet
    val mazeEdges = step(initialFrontier, Set(entrance), Set.empty)
    Maze(width, height, mazeEdges, entrance, exit, cells)
  }

  def neighbors(cell: Cell, width: Int, height: Int): List[Cell] = {
    val neighborCells = List(
      DefaultCell(cell.x - 1, cell.y),
      DefaultCell(cell.x + 1, cell.y),
      DefaultCell(cell.x, cell.y - 1),
      DefaultCell(cell.x, cell.y + 1)
    )
    neighborCells.filter(cell =>
      cell.x >= 0 && cell.y >= 0 && cell.x < width && cell.y < height
    )
  }
}
