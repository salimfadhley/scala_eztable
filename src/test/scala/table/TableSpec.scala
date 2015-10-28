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

  it should "have a length and a width" in {
    val T = new Table()
    T.length should be(0)
    T.width should be(0)
  }

  it should "have a length and a width that changes when items are added" in {
    val intCol = new Column[Int]("A").extend(List(3, 3, 9, 8, 2))
    val T = new Table(List(intCol, intCol, intCol))

    T.length should be(intCol.length)
    T.width should be(3)
  }

  it can "be accessed as an iterator" in {
    val intCol0 = new Column[Int]("A").extend(List(3, 4, 9, 8, 2))
    val intCol1 = new Column[Int]("B").extend(List(3, 4, 9, 8, 2))
    val intCol2 = new Column[Int]("C").extend(List(3, 4, 9, 8, 2))
    val T = new Table(List(intCol0, intCol1, intCol2))
    val Ti: Iterator[Map[String, Any]] = T.iterator
    Ti.next() should be(Map("A" -> 3, "B" -> 3, "C" -> 3))
    //    Ti.next() should be(List(4, 4, 4))
    //    Ti.next() should be(List(9, 9, 9))
    //    Ti.next() should be(List(8, 8, 8))
    //    Ti.next() should be(List(2, 2, 2))
  }

  it should "throw an exception if we iterate out of bounds" in {
    val intCol = new Column[Int]("A").extend(List())
    val T = new Table(List(intCol, intCol, intCol))

    try {
      T.iterator.next()
      throw new RuntimeException("Failed to raise appropriate error")
    } catch {
      case _: java.util.NoSuchElementException =>
    }

  }

  it should "provide a column descripton" in {
    val intCol = new Column[Int]("A").extend(List(3, 4, 9, 8, 2))
    val T = new Table(List(intCol))
    T.description should be(List("A (int)"))
  }

  it should "the description should include all types" in {
    val intCol = new Column[Int]("A").extend(List(3, 4, 9))
    val strCol = new Column[String]("B").extend(List("A", "B", "C"))
    val T = new Table(List(intCol, strCol))
    T.description should be(List("A (int)", "B (String)"))
  }

  it should "have a method which gives the maximum char width of every column" in {
    val intCol = new Column[Int]("A").extend(List(3, 4, 9))
    val strCol = new Column[String]("B").extend(List("A", "B", "C"))
    val T = new Table(List(intCol, strCol))
    val result:List[Int] = T.columnCharWidths
    result should be(List(7, 10))
  }

  it should "should give the maximum of description or max column width" in {
    val strCol0 = new Column[String]("Overly Long Column Name").extend(List("A", "B"))
    val strCol1 = new Column[String]("x").extend(List("Hyperbole antagonism", "Crummulet Foxglobe Indignity"))
    val T = new Table(List(strCol0, strCol1))
    val result: List[Int] = T.columnCharWidths
    result should be(List(32, 28))
  }

  //TODO: Check out of bounds on a table


}
