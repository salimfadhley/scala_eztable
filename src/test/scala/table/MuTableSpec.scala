package table

import org.scalatest._

class MuTableSpec extends FlatSpec {

  "MuTable" can "be created without any columns at all" in {
    val T = new MuTable()
  }

  it should "have an attribute called table" in {
    val T = new MuTable()
    val _: Table = T.table
  }

  it can "be initialized with columns" in {
    val intCol = new Column[Int]("int")
    val floatCol = new Column[Float]("float")
    val columns: List[Column[_ <: Any]] = List(intCol, floatCol)
    val T = new MuTable(columns)
  }

}