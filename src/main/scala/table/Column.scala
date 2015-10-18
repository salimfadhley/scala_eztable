package table

import scala.reflect.ClassTag

class Column[T: ClassTag](columnName: String, values: List[_ <: T] = Nil) extends DescribableColumn {
  val _values: List[T] = values
  val _columnName: String = columnName


  def name: String = _columnName

  def extend(newValues: List[T]) = {
    new Column(_columnName, newValues)
  }

  def apply(i: Int): T = {
    _values(i)
  }

  def append(i: T): Column[T] = {
    val newValues = _values ::: i :: Nil
    extend(newValues)

  }

  def appendAny[R <: Any](i: R) = {
    append(i.asInstanceOf[T])

  }

  def getTypeName: String = {
    getRuntimeClass.getSimpleName
  }

  def appendString(v: String): Column[T] = {
    val converted: Any = getTypeName.toLowerCase match {
      case "string" => v.toString
      case "integer" => v.toInt
      case "int" => v.toInt
      case "float" => v.toFloat
      case "double" => v.toDouble
      case "boolean" => v.toBoolean
      case t: String => throw new RuntimeException(s"Cannot convert from type $t")
    }

    appendAny(converted)
  }


  def getRuntimeClass: Class[_] = {
    implicitly[ClassTag[T]].runtimeClass
  }

  def length: Int = {
    _values.length
  }

  override def equals(o: Any): Boolean = {
    o match {
      case o: Column[_] => (o.name eq this.name) && (o._values == this.values)
      case _ => false
    }
  }
}


object Column {
  def fromDescription(description: String): Column[_] = {
    val parsedDescription = parseDescription(description)
    val columnName: String = parsedDescription._1
    val typeName: String = parsedDescription._2

    typeName.toLowerCase match {
      case "string" => new Column[String](columnName)
      case "integer" => new Column[Integer](columnName)
      case "int" => new Column[Int](columnName)
      case x: String => throw new RuntimeException(s"Cannot build column type $x from string")
    }
  }

  def parseDescription(description: String): (String, String) = {
    val matchDescription = """([\w\s]+)\s+\((\w+)\)""".r
    description match {
      case matchDescription(n, t) => (n, t)
      case x => throw new RuntimeException(s"Cannot Parse: $x")
    }
  }

  def compareColumns(a: Column[_], b: Column[_]): Map[String, Boolean] = {
    val typeOK: Boolean = a.getRuntimeClass == b.getRuntimeClass
    val valuesOK: Boolean = a._values == b._values
    val nameOK: Boolean = a.name == b.name
    Map[String,Boolean](
      "type" -> typeOK,
      "values" -> valuesOK,
      "name" -> nameOK
    )

  }

}