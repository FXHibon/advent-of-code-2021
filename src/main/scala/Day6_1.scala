import cats.effect.IO

import scala.annotation.tailrec
import scala.util.Try

object Day6_1 {

  def solve: IO[String] = DataSource
    .dataForDay(6)
    .map(_.split(",").filter(_.nonEmpty).map(_.toInt).toList)
    .map { fishes =>
      fishes
        .groupBy(identity)
        .toList
        .sortBy(_._1)
        .map { case (key, value) =>
          simulateDays(fishes = List(key), days = 80).size * value.size
        }
        .sum

    }
    .map { fishes =>
      s"fishes_size(${fishes})"
    }
    .compile
    .lastOrError

  @tailrec
  def simulateDays(fishes: List[Int], days: Int): List[Int] = {
    println(s"Day $days")
    days match {
      case 0 => fishes
      case _ =>
        val updatedFished = fishes.foldLeft(List.empty[Int]) {
          case (acc, 0)        => acc :+ 6 :+ 8
          case (acc, positive) => acc :+ (positive - 1)
        }
        simulateDays(updatedFished, days - 1)
    }
  }

}
