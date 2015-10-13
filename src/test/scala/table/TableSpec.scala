package table

import org.scalatest._

class TableSpec extends FlatSpec with Matchers {

  "Table" can "be created without any columns at all" in {
    val T = new Table()
  }

  it can "be initialized with columns" in {
    val intCol = new Column[Int]("int")
    val floatCol = new Column[Float]("float")
    val columns: List[Column[_ <: Any]] = List(intCol, floatCol)
    val T = new Table(columns)
  }

  it can "have columns added to it" in {
    val t0 = new Table()
    val t1 = t0.addColumn(new Column[Int]("int"))
  }

  it can "have rows added to it" in {
    val intCol = new Column[Int]("A")
    val floatCol = new Column[Float]("B")
    val columns: List[Column[_ <: Any]] = List(intCol, floatCol)
    val T = new Table(columns)

    val row: List[Any] = List(3, "hello")
    val T1 = T += row
  }

  it can "be indexed" in {
    val intCol = new Column[Int]("A").extend(List(3, 3, 9, 8, 2))
    val floatCol = new Column[String]("B").extend(List("A", "B", "C", "D", "E"))
    val T = new Table(List(intCol, floatCol))
    val result: List[Any] = T(0)
    val expected: List[Any] = List(3, "A")
    result should be(expected)
  }

  it can "be indexed with non-zero values" in {
    val intCol = new Column[Int]("A").extend(List(3, 3, 9, 8, 2))
    val floatCol = new Column[String]("B").extend(List("A", "B", "C", "D", "E"))
    val T = new Table(List(intCol, floatCol))
    val result: List[Any] = T(3)
    val expected: List[Any] = List(8, "D")
    result should be(expected)
  }

}
