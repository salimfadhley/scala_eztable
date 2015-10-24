package table

class TableIndex[T](table: Table, columnNames: List[String]) {
  val _table = table
  val _columnNames = columnNames

  val _index: Map[List[Any], Int] = {

    _table.iterator.zipWithIndex.map((rowAndIndex: (Map[String, Any], Int)) => {
      val row = rowAndIndex._1
      val index = rowAndIndex._2
      val key = _columnNames.map((cn: String) => {
        row.get(cn) match {
          case Some(x) => x
          case None => throw new RuntimeException(s"Invalid column: $cn}")
        }
      })
      (key, index)
    }

    ).toMap
  }

  def getRowIndex(key: List[Any]): Option[Int] = {
    _index.get(key)
  }

}
