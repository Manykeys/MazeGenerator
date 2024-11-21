package mazeGeneratorAlgo

import creatures.*

import scala.annotation.tailrec
import scala.util.Random

class MazeKruskalAlgo extends MazeGenerator {

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

  def find(sets: Map[Cell, Cell], cell: Cell): Cell = {
    if (sets(cell) == cell) cell else find(sets, sets(cell))
  }

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

    val edges = cells.values
      .flatMap(cell => neighbors(cell, width, height).map(Edge(cell, _)))
      .toSet
    val shuffledEdges = Random.shuffle(edges.toList)

    @tailrec
    def step(
        remainingEdges: List[Edge],
        sets: Map[Cell, Cell],
        maze: Set[Edge]
    ): Set[Edge] = {
      remainingEdges match {
        case Nil => maze
        case edge :: tail =>
          val Edge(a, b) = edge
          if (find(sets, a) != find(sets, b)) {
            step(tail, union(sets, a, b), maze + edge)
          } else {
            step(tail, sets, maze)
          }
      }
    }

    val initialSets = cells.values.map(c => c -> c).toMap
    val finalMazeEdges = step(shuffledEdges, initialSets, Set.empty)

    Maze(width, height, finalMazeEdges, entrance, exit, cells)
  }

  private def union(
      sets: Map[Cell, Cell],
      a: Cell,
      b: Cell
  ): Map[Cell, Cell] = {
    val rootA = find(sets, a)
    val rootB = find(sets, b)
    sets.updated(rootA, rootB)
  }
}
