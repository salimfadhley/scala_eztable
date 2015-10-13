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

}