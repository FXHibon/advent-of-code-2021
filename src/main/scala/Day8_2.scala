import cats.effect.IO

object Day8_2 {

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
      case str if str.size == 2 => str -> '1'
      case str if str.size == 3 => str -> '7'
      case str if str.size == 4 => str -> '4'
      case str if str.size == 7 => str -> '8'
    }

    val one   = freeResolutions.find(_._2 == '1').get._1
    val four  = freeResolutions.find(_._2 == '4').get._1
    val seven = freeResolutions.find(_._2 == '7').get._1
    val eight = freeResolutions.find(_._2 == '8').get._1

    val three = entry.uniquePatterns
      .diff(List(one, four, seven, eight))
      .filter(_.size == 5)
      .collectFirst {
        case xs if (xs & one) == one => xs
      }
      .get

    val nine = entry.uniquePatterns
      .diff(List(one, four, seven, eight, three))
      .filter(_.size == 6)
      .collectFirst {
        case xs if (xs & three) == three => xs
      }
      .get

    val zero = entry.uniquePatterns
      .diff(List(one, four, seven, eight, three, nine))
      .filter(_.size == 6)
      .collectFirst {
        case xs if (xs & seven) == seven => xs
      }
      .get

    val five = entry.uniquePatterns
      .diff(List(one, four, seven, eight, three, nine, zero))
      .filter(_.size == 5)
      .collectFirst {
        case xs if (xs & (four -- one)) == (four -- one) => xs
      }
      .get

    val two = entry.uniquePatterns
      .diff(List(one, four, seven, eight, three, nine, zero, five))
      .filter(_.size == 5)
      .head

    val six = entry.uniquePatterns
      .diff(List(one, four, seven, eight, three, nine, zero, five, two))
      .head

    entry.digits
      .map {
        case `zero`  => '0'
        case `one`   => '1'
        case `two`   => '2'
        case `three` => '3'
        case `four`  => '4'
        case `five`  => '5'
        case `six`   => '6'
        case `seven` => '7'
        case `eight` => '8'
        case `nine`  => '9'
      }
      .mkString
      .toInt
  }

  case class Entry(uniquePatterns: List[Set[Char]], digits: List[Set[Char]])

  object Entry {
    def fromString(str: String): Entry = {
      str match {
        case s"$patterns | $digits" =>
          Entry(
            uniquePatterns =
              patterns.split(" ").toList.map(_.toCharArray.toSet),
            digits = digits.split(" ").toList.map(_.toCharArray.toSet)
          )
      }
    }
  }

}
