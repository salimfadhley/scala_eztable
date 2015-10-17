package table

trait Tabular extends Iterable[List[_]] {

  val _columns:List[Column[_]]

  def description: List[String] = {
    _columns.map(c => c.description)
  }

  def columnCharWidths: List[Int] = {
    _columns.map(c => c.charWidth)
  }

}
