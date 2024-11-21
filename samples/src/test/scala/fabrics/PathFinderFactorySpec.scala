package fabrics

import creatures.PathFinder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PathFinderFactorySpec extends AnyFlatSpec with Matchers {

  "PathFinderFactory" should "create AStarSearch when 'astar'" in {
    val pathFinder = PathFinderFactory.createPathFinder("astar")
    pathFinder shouldBe a[Right[IllegalArgumentException, PathFinder]]
  }

  it should "create BreadthSearch when 'bfs'" in {
    val pathFinder = PathFinderFactory.createPathFinder("bfs")
    pathFinder shouldBe a[Right[IllegalArgumentException, PathFinder]]
  }

  it should "throw IllegalArgumentException for unknown" in {
    val pathFinder = PathFinderFactory.createPathFinder("123")
    pathFinder shouldBe a[Left[IllegalArgumentException, PathFinder]]
  }
}
