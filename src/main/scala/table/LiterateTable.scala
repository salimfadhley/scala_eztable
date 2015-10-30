package table


trait LiterateTable extends Iterable[Map[String, Any]] {

  def getColumns: List[Column[_]]

  def description: List[String] = {
    getColumns.map(c => c.description)
  }

  def columnCharWidths: List[Int] = {
    getColumns.map(c => c.charWidth)
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
    val columnNames: List[String] = getColumns.map(c => c.name)



    val otherLines: Iterator[String] = this.iterator.map {
      case rowMap: Map[String, Any] => {
        val rowStrings: List[String] = columnNames.map {
          case x: String => rowMap.getOrElse(x, "").toString
        }
        makeLiteralLine(widths, rowStrings)
      }

    }

    (headerLine :: otherLines.toList).mkString("\n")

  }

}
