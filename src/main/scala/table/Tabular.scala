package table

/**
 * Created by sal on 30/10/15.
 */
trait Tabular extends LiterateTable {
  def apply(i: Int): List[Any]

  def iterator: Iterator[Map[String, Any]]

  def length: Int

  def width: Int
}
