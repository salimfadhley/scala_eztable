package table

trait LiterateTable extends Iterable[List[_]] {

  val _columns:List[Column[_]]

  def description: List[String] = {
    _columns.map(c => c.description)
  }

  def columnCharWidths: List[Int] = {
    _columns.map(c => c.charWidth)
  }

  private def makeLiteralLine(widths: List[Int], text: List[String]): String = {
    val middle: String = widths.zip(text).map((t: (Int, String)) => {
      val paddingLength = t._1 - t._2.length
      t._2 + " " * paddingLength
    }).mkString(" | ")
    "| " + middle + " |"
  }

  def toLiteral: String = {
    val widths = columnCharWidths
    val headerLine: String = makeLiteralLine(widths, description)

    val otherLines: List[String] = this.iterator.map((value: List[_]) => {
      makeLiteralLine(widths, value.map(v => v.toString))
    }).toList

    (headerLine :: otherLines).mkString("\n")

  }

}
