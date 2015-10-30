package table

/**
 * Created by sal on 30/10/15.
 */
class CombiTable(tables: List[Table]) extends Tabular {
  val _tables = tables

  def this(tables: Table*) = this(tables.toList)

  override def apply(i: Int): List[Any] = ???

  override def length: Int = _tables.map {
    case (t: Table) => t.length
  }.sum

  override def iterator: Iterator[Map[String, Any]] = {
    val iterators: List[Iterator[Map[String, Any]]] = _tables.map {
      case (t: Table) => t.iterator
    }
    iterators.reduce((a, b) => a ++ b)
  }

  override def width: Int = ???

  override def getColumns: List[Column[_]] = _tables.head.getColumns
}
