package table

import org.scalatest._

class IndexableTableSpec extends FlatSpec with Matchers {

  "IndexableTable" should "have a method which gets an index" in {


    val columnType = new Column[String]("pokemon", List[String]("Pikachu", "Starmie", "Graveller", "Raichu"))
    val columnNickName = new Column[String]("nickname", List[String]("Pikachu", "Cutie", "Tough Guy", "Banzai"))
    val columnLevel = new Column[Int]("level", List(3, 5, 19, 12))
    val columnTrainer = new Column[String]("trainer", List[String]("Ash", "Misty", "Brock", "Lt. Surge"))

    val T0 = new Table(List(columnType, columnNickName, columnLevel, columnTrainer))

    val columnsToIndex: List[String] = List("pokemon", "level")
    val index0: TableIndex[(String, Integer)] = T0.getIndex[(String,Integer)](columnsToIndex)

    index0.getRowIndex(List("Pikachu", 3)) should be(Some(0))
    index0.getRowIndex(List("Starmie", 5)) should be(Some(1))
  }

}
