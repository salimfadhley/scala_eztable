case class FunnyColumn[+T](data:List[T])
case class FunnyTable(items:List[FunnyColumn[Any]])

val intItem:FunnyColumn[Int] = new FunnyColumn(List(1,2,3))
val stringItem:FunnyColumn[String] = new FunnyColumn(List("A", "B", "C"))
val untypedItem:FunnyColumn[Any] = new FunnyColumn(List(1,2,3))

// works but seems icky
val funny0 = new FunnyTable(List(untypedItem))

// Fails because it wants List[FunnyColumn[Any]] but I gave it List[FunnyColumn[Int]]
val funny1 = new FunnyTable(List(intItem))

// Fails because it wants List[FunnyColumn[Any]] but I gave it List[FunnyColumn[_ :> String with Int]]
val funny2 = new FunnyTable(List(intItem, stringItem))