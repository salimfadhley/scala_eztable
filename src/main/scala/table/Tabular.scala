package table

/**
 * Created by sal on 17/10/15.
 */
trait Tabular extends Iterable[List[_]] {

  val _columns:List[Column[_]]

  def description: List[String] = {
    _columns.map(c => c.description)
  }

  def columnCharWidths: List[Int] = {
    description.map(d=>d.length)
  }


}
