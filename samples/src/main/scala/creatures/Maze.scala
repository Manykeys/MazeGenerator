package creatures

case class Maze(
    width: Int,
    height: Int,
    edges: Set[Edge],
    entrance: Cell,
    exit: Cell,
    cells: Map[(Int, Int), Cell]
) {

  def cellAt(x: Int, y: Int): Option[Cell] = cells.get((x, y))

  def isWallBetween(a: Cell, b: Cell): Boolean =
    !edges.contains(Edge(a, b)) && !edges.contains(Edge(b, a))
}
