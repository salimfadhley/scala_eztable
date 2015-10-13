import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.runtime.Tuple3Zipped
import scala.util.Random

val a = List("A", "B", "C")

val b = List(1L, 2L, 3L)

val c = List(3,4,5)

val num = Random.nextInt(5) + 3

val x1 = List(a,b,c)
val x2 = new ListBuffer[List[Any]]()

(0 to num).foreach((int)=>{
  x2 += x1(Random.nextInt(x1.length))
})

