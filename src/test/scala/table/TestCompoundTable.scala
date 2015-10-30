package table

import org.scalatest.{FunSuite, Matchers}

/**
 * Created by sal on 30/10/15.
 */
class TestCompoundTable extends FunSuite with Matchers {

  test("The length of a combitable is the sum of it's lengths") {

    val t0 = Table.fromLiteral(
      """|| A (str) | B (Int) |
        || Hello   | 92      |""".stripMargin)

    val t1 = Table.fromLiteral(
      """|| A (str) | B (Int) |
        || Goodbye  | 29      |""".stripMargin)

    val ct0 = new CombiTable(t0, t1)
    assert(ct0.length === (t0.length + t1.length))
  }

  test("A CombiTable wrapping a single table looks exactly like that table") {

    val t0 = Table.fromLiteral(
      """|| A (str) | B (Int) |
        || Hello   | 92      |""".stripMargin)

    val ct0 = new CombiTable(t0)

    assert(ct0.description === t0.description)
    assert(ct0.toLiteral === t0.toLiteral)


  }

  test("The contents of a combitable appears to be the concatenation of it's parts") {

    val t0 = Table.fromLiteral(
      """|| A (str) | B (int) |
        || Hello   | 92      |""".stripMargin)

    val t1 = Table.fromLiteral(
      """|| A (str) | B (int) |
        || Goodbye  | 29      |""".stripMargin)

    val ct0 = new CombiTable(t0, t1)

    val expected = (
      """|| A (String) | B (int) |
        || Hello      | 92      |
        || Goodbye    | 29      |""".stripMargin
      )

    val result = ct0.toLiteral

    assert(result === expected)
  }

}
