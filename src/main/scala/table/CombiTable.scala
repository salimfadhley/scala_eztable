package table

/**
 * Created by sal on 30/10/15.
 */
class CombiTable(tables: List[Table]) extends Tabular {
  val _tables = tables

  def this(tables: Table*) = this(tables.toList)

  def getGetRowFromCombinationTable(_tables: List[Table], i: Int): List[Any] = {
    if (i < _tables.head.length) {
      _tables.head(i)
    } else {
      getGetRowFromCombinationTable(_tables.tail, i - tables.head.length)
    }
  }

  override def apply(i: Int): List[Any] = {
    getGetRowFromCombinationTable(_tables, i)
  }

  override def length: Int = _tables.map {
    case (t: Table) => t.length
  }.sum

  override def iterator: Iterator[Map[String, Any]] = {
    val iterators: List[Iterator[Map[String, Any]]] = _tables.map {
      case (t: Table) => t.iterator
    }
    iterators.reduce((a, b) => a ++ b)
  }

  override def width: Int = {
    _tables.head.width
  }

  override def getColumns: List[Column[_]] = _tables.head.getColumns

}
