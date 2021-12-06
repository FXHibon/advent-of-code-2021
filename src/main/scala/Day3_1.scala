import cats.effect.IO

object Day3_1 {

  val solve: IO[String] = DataSource
    .dataForDay(dayNumber = 3)
    .compile
    .toList
    .flatMap { lines =>
      IO {
        val epsilonAndGamma = rotateLines(lines).map { column =>
          val List(epsilon, gamma) = column
            .groupBy(identity)
            .toList
            .sortBy(_._2.size)
            .map(_._1)

          epsilon -> gamma
        }

        val (epsilonList, gammaList) = epsilonAndGamma.unzip
        val epsilon                  = Integer.parseInt(epsilonList.mkString, 2)
        val gamma                    = Integer.parseInt(gammaList.mkString, 2)
        s"gamma($gamma) x epsilon($epsilon) == power_consumption(${gamma * epsilon})"
      }
    }

  def rotateLines(lines: List[String]): List[List[String]] = {
    (0 until lines.head.length).map { index =>
      lines.map(_.apply(index)).map(_.toString)
    }.toList
  }
}
