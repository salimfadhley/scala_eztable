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
    implicitly[ClassTag[T]].runtimeClass.getSimpleName
  }

  def length: Int = {
    _values.length
  }


}
