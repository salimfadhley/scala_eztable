case class FunnyThing[T](data:List[T]) {

  // <: captures the concept of 'is a'
  def append[S <: T](vv:S):FunnyThing[T] = {
    val newList:List[T] = data ::: vv :: Nil
    new FunnyThing[T](newList)
  }

  // And presumably this is the reverse?
  def append2[S >: T](vv:S):FunnyThing[S] = {
    new FunnyThing[S](data ::: vv :: Nil)
  }
}

val funny1 = FunnyThing[Int](List(1,2,3))
val funny2 = funny1.append(29).append(30)
val funny3 = funny2.append2("X")
val untypedTying:Any = 9L
val funny4 = funny1.append2(untypedTying)
val funny5 = funny1.append2(9L)
val funny6 = FunnyThing[Numeric](List(1,2))

