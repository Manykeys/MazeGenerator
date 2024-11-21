package renderer

import creatures.*

import scala.annotation.tailrec

object Renderer {
  private val wall = "\uD83D\uDFE8"
  private val space = "⬛"
  private val start = "S "
  private val finish = "F "
  private val pathColor = "🟥"
  private val cellSymbols = Map[Class[_ <: Cell], String](
    classOf[DefaultCell] -> space,
    classOf[CoinCell] -> "💰",
    classOf[DesertCell] -> "🏜️"
  )

  def buildMaze(maze: Maze): Array[String] = {
    buildMazeRec(0, maze) :+ buildBottomBorder(maze)
  }

  def buildMazeWithPath(maze: Maze, path: List[Cell]): Array[String] = {
    buildMazeRec(0, maze, Some(path)) :+ buildBottomBorder(maze)
  }

  private def buildMazeRec(
      row: Int,
      maze: Maze,
      path: Option[List[Cell]] = None
  ): Array[String] = {
    @tailrec
    def loop(row: Int, acc: Array[String]): Array[String] = {
      if (row < maze.height) {
        val newAcc =
          acc :+ buildRow(row, maze, path) :+ buildCellRow(row, maze, path)
        loop(row + 1, newAcc)
      } else acc
    }
    loop(0, Array())
  }

  private def buildRow(
      row: Int,
      maze: Maze,
      path: Option[List[Cell]] = None
  ): String = {
    @tailrec
    def loop(col: Int, acc: String): String = {
      if (col == maze.width) acc + wall
      else {
        val currentCell = maze.cellAt(col, row).get
        val cellAbove = maze.cellAt(col, row - 1)
        val isWall = row == 0 || maze.isWallBetween(currentCell, cellAbove.get)
        val cellSymbol =
          if (isWall) wall
          else {
            path match {
              case Some(p)
                  if p.contains(currentCell) && p.contains(cellAbove.get) =>
                pathColor
              case _ => space
            }
          }
        loop(col + 1, acc + wall + cellSymbol)
      }
    }
    loop(0, "")
  }

  private def buildCellRow(
      row: Int,
      maze: Maze,
      path: Option[List[Cell]] = None
  ): String = {
    @tailrec
    def loop(col: Int, acc: String): String = {
      if (col == maze.width) acc + wall
      else {
        val currentCell = maze.cellAt(col, row)
        val cellLeft = maze.cellAt(col - 1, row)
        val isWall =
          col == 0 || maze.isWallBetween(cellLeft.get, currentCell.get)
        val symbol =
          if (isWall) wall
          else {
            path match {
              case Some(p)
                  if p.contains(cellLeft.get) && p.contains(currentCell.get) =>
                pathColor
              case _ => space
            }
          }
        loop(
          col + 1,
          acc + symbol + currentCell
            .map(cellSymbol(_, maze, path))
            .getOrElse(space)
        )
      }
    }
    loop(0, "")
  }

  private def cellSymbol(
      cell: Cell,
      maze: Maze,
      path: Option[List[Cell]] = None
  ): String = {
    if (cell == maze.entrance) start
    else if (cell == maze.exit) finish
    else if (path.exists(_.contains(cell))) pathColor
    else cellSymbols.getOrElse(cell.getClass, space)
  }

  private def buildBottomBorder(maze: Maze): String = {
    @tailrec
    def loop(col: Int, acc: String): String = {
      if (col == maze.width) acc + wall
      else loop(col + 1, acc + wall + wall)
    }
    loop(0, "")
  }
}
