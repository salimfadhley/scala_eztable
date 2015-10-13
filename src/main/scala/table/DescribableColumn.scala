package table

trait DescribableColumn {
  def name: String

  def getTypeName: String

  def description: String = s"${name} (${getTypeName})"

}
