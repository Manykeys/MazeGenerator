package pathFinders

import creatures.*

import scala.annotation.tailrec
import scala.collection.immutable.Queue

class BreadthSearch extends PathFinder {

  def findPath(maze: Maze, start: Cell, finish: Cell): List[Cell] = {

    @tailrec
    def find(
        queue: Queue[(Cell, Double)],
        visited: Set[Cell],
        path: Map[Cell, Cell]
    ): Map[Cell, Cell] = queue.dequeueOption match {
      case None => path
      case Some(((current, cost), remainingQueue)) =>
        if (current == finish) path
        else {
          val neighbors = maze.edges.collect {
            case Edge(a, b) if a == current && !visited(b) =>
              (b, cellWeight(b) + cost)
            case Edge(a, b) if b == current && !visited(a) =>
              (a, cellWeight(a) + cost)
          }
          val updatedPath = path ++ neighbors.map(_._1 -> current)
          find(
            remainingQueue.enqueueAll(neighbors),
            visited + current,
            updatedPath
          )
        }
    }

    def buildPath(
        cell: Cell,
        path: Map[Cell, Cell],
        acc: List[Cell]
    ): List[Cell] =
      if (cell == start) start :: acc
      else
        path
          .get(cell)
          .map(prev => buildPath(prev, path, cell :: acc))
          .getOrElse(acc)

    val pathMap = find(Queue((start, 0.0)), Set(start), Map())
    if (!pathMap.contains(finish)) List()
    else buildPath(finish, pathMap, List())
  }

  private def cellWeight(cell: Cell): Double = cell match {
    case _: DefaultCell => 2.0
    case c: DesertCell  => c.weight
    case c: CoinCell    => c.weight
    case _              => 2.0
  }
}
