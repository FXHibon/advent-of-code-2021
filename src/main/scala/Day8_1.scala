import cats.effect.IO

object Day8_1 {

  val standardMatrix: Map[Int, List[Char]] = Map(
    0 -> "abcefg",
    1 -> "cf",
    2 -> "acdeg",
    3 -> "acdfg",
    4 -> "bcdf",
    5 -> "abdfg",
    6 -> "abdefg",
    7 -> "acf",
    8 -> "abcdefg",
    9 -> "abcdfg"
  ).view.mapValues(_.toList).toMap

  def solve: IO[String] = DataSource
    .dataForDay(8)
    .map(Entry.fromString)
    .flatMap { entry =>
      fs2.Stream.emits(entry.digits.collect {
        case str if str.length == 2 => str
        case str if str.length == 4 => str
        case str if str.length == 3 => str
        case str if str.length == 7 => str
      })
    }
    .compile
    .toList
    .map(_.size)
    .map(result => s"size($result)")

  case class Entry(uniquePatterns: List[String], digits: List[String])

  object Entry {
    def fromString(str: String): Entry = {
      str match {
        case s"$patterns | $digits" =>
          Entry(
            patterns.split(" ").toList,
            digits.split(" ").toList
          )
      }
    }
  }

}
