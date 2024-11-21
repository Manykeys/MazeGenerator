import sbt.*

object Dependencies {
  val scalastic = "org.scalactic" %% "scalactic" % Versions.scalaTest
  val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest % "test"
  val catsCore = "org.typelevel" %% "cats-core" % Versions.catsCore
  val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect

  object Versions {
    val scalaTest = "3.2.18"
    val catsCore = "2.9.0"
    val catsEffect = "3.5.0"
  }
}
