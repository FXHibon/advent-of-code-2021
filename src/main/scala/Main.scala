import cats.effect.{IO, IOApp}
import fs2.io.file.Path

object Main extends IOApp.Simple {


  override def run: IO[Unit] = fs2.io.file.Files[IO].readAll(Path("/Users/francois-xavierhibon/fxhibon/advent-of-code/src/main/resources/ex3"))
    .through(fs2.text.utf8.decode)
    .through(fs2.text.lines)
    .filter(_.nonEmpty)
    .compile
    .toList
    .map { lines =>
      val columnCount = lines.head.length

      val gammaRateBinary = (0 until columnCount)
        .map { index =>
          lines.map(_.apply(index))
        }.map { column =>

        val List(epsilon, gamma) = column
          .groupBy(_.toString)
          .toList
          .sortBy(_._2.size)
          .map(_._1)

        epsilon -> gamma
      }

      val (epsilonList, gammaList) = gammaRateBinary.unzip
      println(epsilonList.mkString)
      println(gammaList.mkString)
      //      println(gammaRateBinary)

    }


}

