import cats.effect.IO

object Day8_2 {

  val standardMatrix: Map[Char, List[Char]] = Map(
    '1' -> "cf",

    '7' -> "acf",

    '4' -> "bcdf",

    '8' -> "abcdefg",

    '2' -> "acdeg",
    '3' -> "acdfg",
    '5' -> "abdfg",

    '0' -> "abcefg",
    '6' -> "abdefg",
    '9' -> "abcdfg"

  ).view.mapValues(_.toList).toMap

  def solve: IO[String] = DataSource
    .dataForDay(8)
    .map(Entry.fromString)
    .map(solveMatrix)
    .compile
    .toList
    .map(_.sum)
    .map(result => s"size($result)")

  def solveMatrix(entry: Entry): Int = {
    val freeResolutions = entry.uniquePatterns.collect {
      case str if str.length == 2 => str -> '1'
      case str if str.length == 3 => str -> '7'
      case str if str.length == 4 => str -> '4'
      case str if str.length == 7 => str -> '8'
    }


    ???
  }

  case class Entry(uniquePatterns: List[String], digits: List[String])

  object Entry {
    def fromString(str: String): Entry = {
      str match {
        case s"$patterns | $digits" =>
          Entry(
            uniquePatterns = patterns.split(" ").toList.map(_.sorted),
            digits = digits.split(" ").toList.map(_.sorted)
          )
      }
    }
  }

}
