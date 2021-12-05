import cats.effect.IO
import fs2.io.file.Path

object Day3_1 {

  def run: IO[String] = fs2.io.file.Files[IO].readAll(Path("/Users/francois-xavierhibon/fxhibon/advent-of-code/src/main/resources/day_3"))
    .through(fs2.text.utf8.decode)
    .through(fs2.text.lines)
    .filter(_.nonEmpty)
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
        val epsilon = Integer.parseInt(epsilonList.mkString, 2)
        val gamma = Integer.parseInt(gammaList.mkString, 2)
        s"gamma($gamma) x epsilon($epsilon) == power_consumption(${gamma * epsilon})"
      }
    }


  def rotateLines(lines: List[String]): List[List[String]] = {
    (0 until lines.head.length)
      .map { index =>
        lines.map(_.apply(index)).map(_.toString)
      }.toList
  }
}

