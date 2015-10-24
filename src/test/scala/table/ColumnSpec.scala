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

  it should "know the width of the widest column" in {
    val emptyCol = new Column[String]("Zyz").extend(List())
    emptyCol.columnCharWidth should be(0)
  }

  it should "allow strings to be added as strings" in {
    val c0 = new Column[String]("Zyz").appendString("a").appendString("b")
    c0._values should be(List("a", "b"))
  }

  it should "allow Integers to be added as strings" in {
    val c0 = new Column[Integer]("abc").appendString("1").appendString("2")
    c0._values should be(List(1, 2))
  }

  it should "allow Ints to be added as strings" in {
    val c0 = new Column[Int]("abc").appendString("2").appendString("3")
    c0._values should be(List(2, 3))
  }

  it should "allow Floats to be added as strings" in {
    val c0 = new Column[Float]("abc").appendString("1.23").appendString("2.34")
    c0._values should be(List(1.23f, 2.34f))
  }

  it should "allow booleans to be added as strings" in {
    val c0 = new Column[Boolean]("abc").appendString("true").appendString("false")
    c0._values should be(List(true, false))
  }

  it should "know that columns with the same name, type" in {
    val c0 = new Column[Boolean]("abc")
    val c1 = new Column[Boolean]("abc")
    assert(c0.equals(c1))
  }

  it should "know that columns with the same name, type and values are equal" in {
    val c0 = new Column[String]("abc").extend(List("Foo", "Bar", "Baz"))
    val c1 = new Column[String]("abc").extend(List("Foo", "Bar", "Baz"))
    assert(c0.equals(c1))
  }

  it should "know that columns different names are never equal" in {
    val c0 = new Column[Boolean]("def")
    val c1 = new Column[Boolean]("abc")
    assert(!c0.equals(c1))
  }

  it should "know that columns different values are never equal" in {
    val c0 = new Column[Boolean]("def").extend(List(true, true, false))
    val c1 = new Column[Boolean]("def").extend(List(true, true, true))
    assert(!c0.equals(c1))
  }

  it should "know that columns different types are never equal" in {
    val c0 = new Column[Int]("def").extend(List(1, 2, 3))
    val c1 = new Column[String]("def").extend(List("a", "b", "c"))
    assert(!c0.equals(c1))
  }

  it should "be able to parse a description" in {
    Column.parseDescription("XYZ (Int)") should equal(("XYZ", "Int"))
  }

  it should "reject invalid descriptions" in {

    intercept[RuntimeException] {
      Column.parseDescription("(Int)")
      }
  }

  it should "be able to initialize a column from a string description" in {
    val result: Column[String] = Column.fromDescription("ABC (String)").asInstanceOf[Column[String]]
    assert(result.name === "ABC")
    assert(result._values === List[String]())
    assert(result.getTypeName === "String")
  }

  it should "be able to initialize a column from complex string descriptions" in {
    val result: Column[Int] = Column.fromDescription("My House (Int)").asInstanceOf[Column[Int]]
    assert(result.name === "My House")
    assert(result._values === List[Int]())
    assert(result.getTypeName === "int")
  }

  it should "be able to regenerate the original description back from the column" in {
    val result: Column[_] = Column.fromDescription("ABC (String)")
    assert(result.description === "ABC (String)")
  }


}
