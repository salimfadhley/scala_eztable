package table
import scala.reflect.ClassTag

class Column[T: ClassTag](columnName: String, values: List[_ <: T] = Nil) extends DescribableColumn {
  val _values:List[T] = values
  val _columnName:String = columnName


  def name: String = _columnName

  def extend(newValues: List[T]) = {
    new Column(_columnName, newValues)
  }

  def apply(i: Int):T = {
    _values(i)
  }

  def append(i:T): Column[T] = {
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

  def equals(o: Column[T]): Boolean = {
    o match {
      case o: Column[T] => (o.name eq this.name) && (o._values == this.values)
      case _ => false
    }
  }


}
