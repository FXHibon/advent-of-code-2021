import cats.effect.{IO, IOApp}
import fs2.io.file.Path

object Main extends IOApp.Simple {

  override def run: IO[Unit] = fs2.io.file.Files[IO].readAll(Path("/Users/francois-xavierhibon/fxhibon/advent-of-code/src/main/resources/positions"))
    .through(fs2.text.utf8.decode)
    .through(fs2.text.lines)
    .filter(_.nonEmpty)
    .map(_.split(" ").toList)
    .map {
      case List(cmd, count) => cmd -> count.toInt
    }
    .compile
    .toList
    .map(_.groupBy(_._1))
    .map(_.view.mapValues(_.map(_._2)).mapValues(_.sum).toMap)
    .flatTap(IO.println)
    .map { values =>
      val up = values("up")
      val down = values("down")
      val forward = values("forward")
      (down - up) * forward
    }
    .flatMap(IO.println)


}
