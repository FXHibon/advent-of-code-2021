import cats.effect.IO

object Day7_1 {

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
    (acc, current) => acc + math.abs(current - center)
  }

}
