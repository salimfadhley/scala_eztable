package table

import org.scalatest._

class LiteralTableSpec extends FlatSpec with Matchers {

  "LiteralTable" should "have a method which produces a string representation" in {
    val intCol = new Column[Int]("A").extend(List(3))
    val T = new Table(List(intCol))

    val expected: String =
      """|| A (int) |
        || 3       |""".stripMargin

    T.toLiteral should be(expected)
  }

  it should "show all the columns" in {
    val name = new Column[String]("Name").extend(List("Squirtle", "Bulbasaur", "Charmander"))
    val level = new Column[Integer]("Level").extend(List(1, 1, 2))
    val pokedex = new Table(List(name, level))

    val expected: String =
      """|| Name (String) | Level (Integer) |
        || Squirtle      | 1               |
        || Bulbasaur     | 1               |
        || Charmander    | 2               |""".stripMargin

    val result = pokedex.toLiteral

    result should be(expected)
  }

  it should "be possible to make a table from a literal" in {

    val tl =
      """|| Name (String) | Level (Integer) |
        || Squirtle      | 1               |
        || Bulbasaur     | 1               |
        || Charmander    | 2               |""".stripMargin


    val t0 = new Table(tl)
  }

}
