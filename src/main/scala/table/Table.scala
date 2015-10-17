package table


class Table(columns: List[Column[_ <: Any]] = Nil) extends Tabular {
  val _columns = columns

  class TableIterator extends Iterator[List[_]] {

    val ri: Iterator[Int] = rowIndexIterator

    def next() = {
      val index: Int = ri.next()
      _columns.map((c: Column[_]) => c(index))
    }

    def hasNext = {
      ri.hasNext
    }
  }


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

  def length: Int = {
    if (_columns.nonEmpty) {
      _columns.map((c: Column[_]) => c.length).max
    } else {
      0
    }
  }

  def rowIndexIterator: Iterator[Int] = {
    if (length > 0) {
      (0 to (length - 1)).iterator
    } else {
      Iterator[Int]()
    }

  }

  def width: Int = _columns.length

  def iterator: Iterator[List[_]] = {
    new TableIterator()
  }

  def desription: List[String] = {
    _columns.map(c => c.description)
  }


}
