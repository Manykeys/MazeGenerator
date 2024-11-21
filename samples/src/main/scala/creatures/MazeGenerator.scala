package creatures

trait MazeGenerator {
  def generateMaze(width: Int, height: Int, entrance: Cell, exit: Cell): Maze
}
