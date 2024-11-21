package renderer

import creatures.{DefaultCell, Edge, Maze}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RendererSpec extends AnyFlatSpec with Matchers {

  "Renderer" should "render maze" in {
    val maze = Maze(
      width = 3,
      height = 3,
      edges = Set(
        Edge(DefaultCell(0, 0), DefaultCell(1, 0)),
        Edge(DefaultCell(1, 0), DefaultCell(2, 0)),
        Edge(DefaultCell(2, 0), DefaultCell(2, 1)),
        Edge(DefaultCell(2, 1), DefaultCell(2, 2))
      ),
      entrance = DefaultCell(0, 0),
      exit = DefaultCell(2, 2),
      cells = Map(
        (0, 0) -> DefaultCell(0, 0),
        (0, 1) -> DefaultCell(0, 1),
        (1, 0) -> DefaultCell(1, 0),
        (1, 1) -> DefaultCell(1, 1),
        (1, 2) -> DefaultCell(1, 2),
        (2, 1) -> DefaultCell(2, 1),
        (2, 2) -> DefaultCell(2, 2),
        (0, 2) -> DefaultCell(0, 2),
        (2, 0) -> DefaultCell(2, 0)
      )
    )

    val result = Renderer.buildMaze(maze)
    result shouldBe Array(
      "🟨🟨🟨🟨🟨🟨🟨",
      "🟨S ⬛⬛⬛⬛🟨",
      "🟨🟨🟨🟨🟨⬛🟨",
      "🟨⬛🟨⬛🟨⬛🟨",
      "🟨🟨🟨🟨🟨⬛🟨",
      "🟨⬛🟨⬛🟨F 🟨",
      "🟨🟨🟨🟨🟨🟨🟨"
    )
  }

  it should "render maze with a path" in {
    val maze = Maze(
      width = 3,
      height = 3,
      edges = Set(
        Edge(DefaultCell(0, 0), DefaultCell(1, 0)),
        Edge(DefaultCell(1, 0), DefaultCell(2, 0)),
        Edge(DefaultCell(2, 0), DefaultCell(2, 1)),
        Edge(DefaultCell(2, 1), DefaultCell(2, 2))
      ),
      entrance = DefaultCell(0, 0),
      exit = DefaultCell(2, 2),
      cells = Map(
        (0, 0) -> DefaultCell(0, 0),
        (0, 1) -> DefaultCell(0, 1),
        (1, 0) -> DefaultCell(1, 0),
        (1, 1) -> DefaultCell(1, 1),
        (1, 2) -> DefaultCell(1, 2),
        (2, 1) -> DefaultCell(2, 1),
        (2, 2) -> DefaultCell(2, 2),
        (0, 2) -> DefaultCell(0, 2),
        (2, 0) -> DefaultCell(2, 0)
      )
    )

    val path = List(
      DefaultCell(0, 0),
      DefaultCell(1, 0),
      DefaultCell(2, 0),
      DefaultCell(2, 1),
      DefaultCell(2, 2)
    )

    val result = Renderer.buildMazeWithPath(maze, path)
    result shouldBe Array(
      "🟨🟨🟨🟨🟨🟨🟨",
      "🟨S 🟥🟥🟥🟥🟨",
      "🟨🟨🟨🟨🟨🟥🟨",
      "🟨⬛🟨⬛🟨🟥🟨",
      "🟨🟨🟨🟨🟨🟥🟨",
      "🟨⬛🟨⬛🟨F 🟨",
      "🟨🟨🟨🟨🟨🟨🟨"
    )
  }
}
