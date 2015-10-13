package table

class Table(columns: List[Column[_ <: Any]] = Nil) {
  val _columns = columns


  def +=(row: List[_ <: Any]): Table = {
    val rowAndColumns: List[(Any, Column[_])] = row.zip(_columns)
    val newColumns: List[Column[_ <: Any]] = rowAndColumns.map((rc: (Any, Column[_])) => rc._2.appendAny(rc._1))
    new Table(newColumns)
  }

  def addColumn(column: Column[_ <: Any]): Table = {
    val newColumns: List[Column[_ <: Any]] = columns ::: column :: Nil
    new Table(newColumns)
  }

  def apply(i: Int): List[Any] = {
    _columns.map((c: Column[_]) => c.apply(i))
  }

}
