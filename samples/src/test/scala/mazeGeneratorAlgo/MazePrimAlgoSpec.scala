package mazeGeneratorAlgo

import creatures.{Cell, DefaultCell, Edge}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MazePrimAlgoSpec extends AnyFlatSpec with Matchers {

  "MazePrimAlgo" should "generate a maze with correct dimensions and entrance/exit" in {
    val width = 4
    val height = 4
    val entrance = DefaultCell(0, 0)
    val exit = DefaultCell(3, 3)

    val mazeGenerator = new MazePrimAlgo()
    val maze = mazeGenerator.generateMaze(width, height, entrance, exit)

    maze.entrance shouldBe entrance
    maze.exit shouldBe exit

    val allCells = for {
      x <- 0 until width
      y <- 0 until height
    } yield DefaultCell(x, y)

    allCells.foreach { cell =>
      maze.edges.exists(edge => edge.a == cell || edge.b == cell) shouldBe true
    }
  }

  it should "generate a connected maze" in {
    val width = 5
    val height = 5
    val entrance = DefaultCell(0, 0)
    val exit = DefaultCell(4, 4)

    val mazeGenerator = new MazePrimAlgo()
    val maze = mazeGenerator.generateMaze(width, height, entrance, exit)

    def connectedComponents(
        cells: Set[DefaultCell],
        edges: Set[Edge]
    ): Set[Set[Cell]] = {
      def collectNeighbors(cell: Cell, visited: Set[Cell]): Set[Cell] = {
        val neighbors = edges
          .collect {
            case Edge(a, b) if a == cell => b
            case Edge(a, b) if b == cell => a
          }
          .diff(visited)

        neighbors.foldLeft(visited + cell)((acc, n) => collectNeighbors(n, acc))
      }

      cells.foldLeft(Set.empty[Set[Cell]]) { (components, cell) =>
        if (components.flatten.contains(cell)) components
        else components + collectNeighbors(cell, Set.empty)
      }
    }

    val allCells = (for {
      x <- 0 until width
      y <- 0 until height
    } yield DefaultCell(x, y)).toSet

    connectedComponents(allCells, maze.edges).size shouldBe 1
  }
}
