import cats.effect.IO

object Day7_2 {

  def solve: IO[String] = DataSource
    .dataForDay(7)
    .map(_.split(",").filter(_.nonEmpty).map(_.toLong).toList)
    .map { list =>
      val (min, max) = (list.min, list.max)

      (min to max)
        .map { center =>
          center -> computeCost(list, center)
        }
        .minBy(_._2)
        ._2
    }
    .map(result => s"fuel($result)")
    .compile
    .lastOrError

  def computeCost(list: List[Long], center: Long): Long = list.foldLeft(0L) {
    (acc, current) => acc + computeCostForDistance(math.abs(current - center))
  }

  def computeCostForDistance(distance: Long): Long = {
    (1L to distance).foldLeft(0L) { (acc, cur) =>
      acc + cur
    }
  }

}
