import cats.effect.IO
import fs2.io.file.Path

import scala.annotation.tailrec

object Day3_2 {

  def run: IO[String] = fs2.io.file.Files[IO].readAll(Path("/Users/francois-xavierhibon/fxhibon/advent-of-code/src/main/resources/day_3"))
    .through(fs2.text.utf8.decode)
    .through(fs2.text.lines)
    .filter(_.nonEmpty)
    .compile
    .toList
    .flatMap { lines =>
      IO {
        val oxygenRating = Integer.parseInt(filterByBitCriteria(FilteringState(lines, bitCriteria = BitCriteria.Most())), 2)
        val co2Rating = Integer.parseInt(filterByBitCriteria(FilteringState(lines, bitCriteria = BitCriteria.Least())), 2)
        s"oxygenRating($oxygenRating) x co2Rating($co2Rating) == life_support_rating(${oxygenRating * co2Rating})"
      }
    }

  sealed trait BitCriteria {
    val equalityResolution: Char
  }

  object BitCriteria {
    case class Most() extends BitCriteria {
      override val equalityResolution: Char = '1'
    }

    case class Least() extends BitCriteria {
      override val equalityResolution: Char = '0'
    }
  }

  case class FilteringState(lines: List[String], bitCriteria: BitCriteria, currentColumnIndex: Int = 0)


  @tailrec
  def filterByBitCriteria(state: FilteringState): String = {

    state.lines match {
      case List(line) => line
      case lines =>
        val count0 = lines
          .map(_(state.currentColumnIndex))
          .count(_ == '0')

        val count1 = lines
          .map(_(state.currentColumnIndex))
          .count(_ == '1')

        val winner = state.bitCriteria match {
          case _: BitCriteria.Most if count0 > count1 => '0'
          case _: BitCriteria.Most if count0 < count1 => '1'
          case criteria: BitCriteria.Most => criteria.equalityResolution
          case _: BitCriteria.Least if count0 < count1 => '0'
          case _: BitCriteria.Least if count0 > count1 => '1'
          case criteria: BitCriteria.Least => criteria.equalityResolution
        }


        val winningLines = state.lines.filter { line =>
          line.charAt(state.currentColumnIndex) == winner
        }

        filterByBitCriteria {
          FilteringState(
            lines = winningLines,
            bitCriteria = state.bitCriteria,
            currentColumnIndex = state.currentColumnIndex + 1
          )
        }
    }
  }
}

