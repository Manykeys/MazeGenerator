package fabrics

import creatures.MazeGenerator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MazeGeneratorFactorySpec extends AnyFlatSpec with Matchers {

  "MazeGeneratorFactory" should "create MazePrimAlgo when 'prim'" in {
    val generator = MazeGeneratorFactory.createMazeGenerator("prim")
    generator shouldBe a[Right[IllegalArgumentException, MazeGenerator]]
  }

  it should "create MazeKruskalAlgo when 'kruskal'" in {
    val generator = MazeGeneratorFactory.createMazeGenerator("kruskal")
    generator shouldBe a[Right[IllegalArgumentException, MazeGenerator]]
  }

  it should "throw IllegalArgumentException for unknown" in {
    val generator = MazeGeneratorFactory.createMazeGenerator("123")
    generator shouldBe a[Left[IllegalArgumentException, MazeGenerator]]
  }
}
