import cats.effect.{IO, IOApp}
import cats.implicits.*
import creatures.{CellConverter, DefaultCell}
import fabrics.{MazeGeneratorFactory, PathFinderFactory}
import renderer.Renderer

import scala.util.Try

object MainApp extends IOApp.Simple {
  def run: IO[Unit] = for {
    _ <- IO.println("Введите ширину и высоту лабиринта (w h):")
    dims <- pairReader()
    width = dims(0)
    height = dims(1)

    _ <- IO.println("Введите координаты начала (x y):")
    startCoords <- pairReader()
    start = DefaultCell(startCoords(0), startCoords(1))

    _ <- IO.println("Введите координаты конца (x y):")
    finishCoords <- pairReader()
    finish = DefaultCell(finishCoords(0), finishCoords(1))

    _ <- IO.println("Выберите алгоритм генерации лабиринта (prim/kruskal):")
    generationAlgo <- IO.readLine
    mazeGenerator <- IO
      .fromEither(
        MazeGeneratorFactory.createMazeGenerator(generationAlgo)
      )
      .handleErrorWith(ex =>
        IO.raiseError(
          new Exception(s"Ошибка генерации лабиринта: ${ex.getMessage}")
        )
      )

    maze1 = mazeGenerator.generateMaze(width, height, start, finish)
    cellConverter = new CellConverter()
    maze = cellConverter.convertMaze(maze1)

    _ <- IO.println("Генерируем лабиринт:")
    renderedMaze = Renderer.buildMaze(maze)
    _ <- renderedMaze.toList.traverse_(IO.println)

    _ <- IO.println("Выберите алгоритм поиска пути (astar, bfs):")
    pathFinderAlgo <- IO.readLine

    pathFinderResult <- IO
      .fromEither(
        PathFinderFactory.createPathFinder(pathFinderAlgo)
      )
      .handleErrorWith(ex =>
        IO.raiseError(new Exception(s"Ошибка поиска пути: ${ex.getMessage}"))
      )

    path = pathFinderResult.findPath(maze, maze.entrance, maze.exit)

    _ <- IO.println("\nЛабиринт с найденным путём:")
    renderedMazeWithPath = Renderer.buildMazeWithPath(maze, path)
    _ <- renderedMazeWithPath.toList.traverse_(IO.println)
  } yield ()

  private def pairReader(): IO[Array[Int]] =
    IO.readLine.flatMap { line =>
      IO.fromOption(
        Try(line.split(" ").map(_.toInt)).toOption
      )(new Exception("Некорректный ввод, попробуйте снова"))
    }
}
