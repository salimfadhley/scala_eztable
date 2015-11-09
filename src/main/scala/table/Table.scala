package table


class Table(columns: List[Column[_ <: Any]] = Nil) extends Tabular {
  

  val _columns = columns

  def getColumns = columns


  class TableIterator extends Iterator[Map[String, Any]] {
    val ri: Iterator[Int] = rowIndexIterator
    def next() = {
      val index: Int = ri.next()
      _columns.map((c: Column[_]) => (c.name, c(index))).toMap[String, Any]
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

  def iterator: Iterator[Map[String, Any]] = {
    new TableIterator()
  }

  def getIndex[T](colNames: List[String]): TableIndex[T] = {
    new TableIndex[T](table = this, columnNames = colNames)
  }

}

object Table {

  def splitSingleRow(row: String) = {
    row.stripPrefix("|").stripSuffix("|").split('|').map(s => s.trim).toList
  }

  def makeColumnsFromHeader(headerRow: String): List[Column[_]] = {
    val descriptions = splitSingleRow(headerRow)
    descriptions.map(d => Column.fromDescription(d))
  }

  def tableLiteralToColumns(tableLiteral: String): List[Column[_]] = {
    val rows = tableLiteral.split("\n")
    val dataRows = rows.tail
    val columns = makeColumnsFromHeader(rows.head)

    val data: List[List[String]] = dataRows.toList.map((r) => {
      splitSingleRow(r)
    }).transpose



    columns.zip(data).map((cd: (Column[_], List[String])) => {
      val column = cd._1
      val elements = cd._2
      column.extendStrings(elements)
    })
  }

  def fromLiteral(literal: String): Table = {
    new Table(tableLiteralToColumns(literal))

  }
}

