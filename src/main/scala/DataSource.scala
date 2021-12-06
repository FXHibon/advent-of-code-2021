import cats.effect.IO
import fs2.io.file.Path

object DataSource {

  def dataForDay(dayNumber: Int): fs2.Stream[IO, String] = fs2.io.file
    .Files[IO]
    .readAll(Path(s"./src/main/resources/day_$dayNumber"))
    .through(fs2.text.utf8.decode)
    .through(fs2.text.lines)
    .filter(_.nonEmpty)

}
