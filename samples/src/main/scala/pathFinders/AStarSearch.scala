package pathFinders

import creatures.*

import scala.annotation.tailrec

class AStarSearch extends PathFinder {

  def findPath(maze: Maze, start: Cell, finish: Cell): List[Cell] = {

    @tailrec
    def find(
        openSet: List[(Cell, Double)],
        gScore: Map[Cell, Double],
        fScore: Map[Cell, Double],
        cameFrom: Map[Cell, Cell]
    ): Map[Cell, Cell] = openSet match {
      case Nil => cameFrom
      case _ =>
        val (current, _) = openSet.minBy(_._2)
        val remainingSet = openSet.filterNot(_._1 == current)

        if (current == finish) cameFrom
        else {
          val neighbors = maze.edges.collect {
            case Edge(a, b) if a == current => b
            case Edge(a, b) if b == current => a
          }

          val updatedScores =
            neighbors.foldLeft((remainingSet, gScore, fScore, cameFrom)) {
              case ((queue, g, f, path), neighbor) =>
                val weight = cellWeight(neighbor)
                val tentativeG = g(current) + weight

                if (tentativeG < g.getOrElse(neighbor, Double.MaxValue)) {
                  val newF = tentativeG + manhattanDistance(neighbor, finish)
                  (
                    (neighbor, newF) :: queue,
                    g.updated(neighbor, tentativeG),
                    f.updated(neighbor, newF),
                    path.updated(neighbor, current)
                  )
                } else (queue, g, f, path)
            }

          find(
            updatedScores._1,
            updatedScores._2,
            updatedScores._3,
            updatedScores._4
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

    val cameFrom = find(
      List((start, manhattanDistance(start, finish))),
      Map(start -> 0.0),
      Map(start -> manhattanDistance(start, finish)),
      Map()
    )
    if (!cameFrom.contains(finish)) List()
    else buildPath(finish, cameFrom, List())
  }

  private def cellWeight(cell: Cell): Double = cell match {
    case _: DefaultCell => 2.0
    case c: DesertCell  => c.weight
    case c: CoinCell    => c.weight
    case _              => 2.0
  }

  private def manhattanDistance(cell: Cell, finish: Cell): Double =
    math.abs(cell.x - finish.x) + math.abs(cell.y - finish.y)
}
