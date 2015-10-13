package table

import org.scalatest._

class ColumnSpec extends FlatSpec with Matchers {

  "Column" should "can be created with a name, a type and a null initial value" in {
    val c = new Column[Int]("C")
    c._values should be(Nil)
  }

  it should "allow appends with an appropriate type" in {
    val c = new Column[Int]("C")
    val d: Column[Int] = c.append(3)
    val expected: List[Int] = List(3)
    d._values should be(expected)
  }

  it should "allow more specific kinds of thing to be added" in {
    class Foo {}
    class Bar extends Foo {}
    class Baz extends Bar {}
    val c = new Column[Foo]("X")
    val d = c.append(new Bar()).append(new Baz())
  }

  it should "allow indexed access" in {
    val c = new Column[String]("X").append("hello").append("world")
    c(0) should be("hello")
    c(1) should be("world")
  }

  it should "allow lots of items to be extended" in {
    val c = new Column[Float]("X").extend(List(1.1f, 2.2f, 3.3f))
    c(2) should be(3.3f)
  }

  it should "be able to get the colum's type as a string" in {
    val c = new Column[Float]("X").extend(List(1.1f, 2.2f, 3.3f))
    val result: String = c.getTypeName
    result should be("float")
  }

  it should "be able to get the colum's type as a string for String Columns" in {
    val c = new Column[String]("X").extend(List("A", "B", "C"))
    val result: String = c.getTypeName
    result should be("String")
  }

  it should "have a length" in {
    val c = new Column[String]("X").extend(List("A", "B", "C"))
    c.length should be
  }

  it should "be able to provide a text description of the column" in {
    val intCol = new Column[Int]("A").extend(List())
    intCol.description should be("A (int)")
  }

  it should "descriptions should contain the name and type of a column" in {
    val intCol = new Column[String]("Zyz").extend(List())
    intCol.description should be("Zyz (String)")
  }



}
