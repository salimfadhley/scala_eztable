package table

trait DescribableColumn {
  def name: String

  def _values: List[_ <: Any]

  def getTypeName: String

  def description: String = s"$name ($getTypeName)"

  def columnCharWidth: Int = {
    if (_values.isEmpty) {
      0
    } else {
      _values.map(x => x.toString.length).max
    }
  }

  def charWidth: Int = {
    math.max(columnCharWidth, description.length)
  }

  override def toString = {
    val vvs: String = _values.mkString(", ")

    s"""<${description}, [${vvs}]>"""
  }

}
