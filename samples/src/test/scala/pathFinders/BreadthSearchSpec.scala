package pathFinders

import creatures.{Cell, DefaultCell, Edge, Maze}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BreadthSearchSpec extends AnyFlatSpec with Matchers {

  "BreadthSearch" should "find the shortest path in a maze" in {
    val start = DefaultCell(0, 0)
    val finish = DefaultCell(2, 2)
    val cells = Map(
      (0, 0) -> DefaultCell(0, 0),
      (1, 0) -> DefaultCell(1, 0),
      (2, 0) -> DefaultCell(2, 0),
      (2, 1) -> DefaultCell(2, 1),
      (2, 2) -> DefaultCell(2, 2)
    )
    val maze = Maze(
      width = 3,
      height = 3,
      edges = Set(
        Edge(cells((0, 0)), cells((1, 0))),
        Edge(cells((1, 0)), cells((2, 0))),
        Edge(cells((2, 0)), cells((2, 1))),
        Edge(cells((2, 1)), cells((2, 2)))
      ),
      entrance = start,
      exit = finish,
      cells = cells
    )

    val pathFinder = new BreadthSearch()
    val path = pathFinder.findPath(maze, start, finish)

    path shouldBe List(
      cells((0, 0)),
      cells((1, 0)),
      cells((2, 0)),
      cells((2, 1)),
      cells((2, 2))
    )
  }

  it should "return an empty path if no path exists" in {
    val start = DefaultCell(0, 0)
    val finish = DefaultCell(2, 2)
    val cells = Map(
      (0, 0) -> DefaultCell(0, 0),
      (1, 0) -> DefaultCell(1, 0),
      (2, 0) -> DefaultCell(2, 0),
      (2, 1) -> DefaultCell(2, 1),
      (2, 2) -> DefaultCell(2, 2)
    )
    val maze = Maze(
      width = 3,
      height = 3,
      edges = Set(Edge(cells((0, 0)), cells((1, 0)))),
      entrance = start,
      exit = finish,
      cells = cells
    )

    val pathFinder = new BreadthSearch()
    val path = pathFinder.findPath(maze, start, finish)

    path shouldBe List()
  }
}
