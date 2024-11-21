package creatures

sealed abstract class Cell(val x: Int, val y: Int, val weight: Double)

case class DefaultCell(override val x: Int, override val y: Int)
    extends Cell(x, y, 2.0)
case class DesertCell(override val x: Int, override val y: Int)
    extends Cell(x, y, 3.0)
case class CoinCell(override val x: Int, override val y: Int)
    extends Cell(x, y, 1.0)
case class SwampCell(override val x: Int, override val y: Int)
    extends Cell(x, y, 4.0)
