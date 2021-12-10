import cats.effect.IO

import scala.annotation.tailrec

object Day6_2 {

  def solve: IO[String] = DataSource
    .dataForDay(6)
    .map(_.split(",").filter(_.nonEmpty).map(_.toInt).toList)
    .map { fishes =>
      val startingState = fishes
        .groupBy(identity)
        .toList
        .map { case (k, v) =>
          FishGroup(k, v.size)
        }

      (1 to 256)
        .foldLeft(startingState) { (acc, _) =>
          simulateDay(acc)
        }
        .map(_.count)
        .sum
    }
    .map { fishes =>
      s"fishes_size(${fishes})"
    }
    .compile
    .lastOrError

  case class FishGroup(remainingDay: Int, count: Long)

  def simulateDay(groups: List[FishGroup]): List[FishGroup] = {
    val duplicatedGroup = groups.flatMap { group =>
      group.remainingDay match {
        case 0 =>
          List(
            FishGroup(remainingDay = 8, count = group.count),
            FishGroup(remainingDay = 6, count = group.count)
          )
        case positiveRemainingDay =>
          List(group.copy(remainingDay = positiveRemainingDay - 1))
      }
    }

    duplicatedGroup
      .groupBy(_.remainingDay)
      .view
      .mapValues(_.map(_.count).sum)
      .toList
      .map { case (remaining, count) =>
        FishGroup(remainingDay = remaining, count = count)
      }

  }

}
