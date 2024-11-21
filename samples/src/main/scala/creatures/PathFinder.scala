package creatures

trait PathFinder {
  def findPath(maze: Maze, start: Cell, finish: Cell): List[Cell]
}
