package table

/**
 * Created by sal on 14/10/15.
 */
case class MuTable(table: Table = new Table()) {
  var _table: Table = table

  def this(columns: List[Column[_ <: Any]]) = this(
    new Table(columns)
  )

  def iterator: Iterator[List[_]] = _table.iterator

  def addColumn(column: Column[_]): MuTable = {
    _table = _table.addColumn(column)
    this
  }
}
