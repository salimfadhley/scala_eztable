

val foo:List[_ >: Any] = List[Int](1,2,3) ::: List[Long](4L, 5L)

println(foo)

foo.foreach(_ match {
  case l:Long => println(s"Long ${l}")
  case l:Int => println(s"Int ${l}")
  case _ => println("Unknown")
})


