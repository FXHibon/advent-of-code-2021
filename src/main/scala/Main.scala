import cats.effect.{IO, IOApp}
import fs2.io.file.Path

object Main extends IOApp.Simple {

  override def run: IO[Unit] = fs2.io.file.Files[IO].readAll(Path("/Users/francois-xavierhibon/fxhibon/advent-of-code/src/main/resources/positions"))
    .through(fs2.text.utf8.decode)
    .through(fs2.text.lines)
    .filter(_.nonEmpty)
    .map(_.split(" ").toList)
    .map {
      case List("up", count) => Cmd.Aim(-count.toInt)
      case List("down", count) => Cmd.Aim(+count.toInt)
      case List("forward", count) => Cmd.Forward(count.toInt)
    }
    .fold(State.empty) { (state, cmd) =>
      cmd match {
        case Cmd.Aim(x) => state.copy(aim = state.aim + x)
        case Cmd.Forward(x) => state.copy(horizontal = state.horizontal + x, depth = state.depth + (state.aim * x))
      }
    }
    .compile
    .lastOrError
    .flatMap(state => IO.println(state.horizontal * state.depth))


  sealed trait Cmd

  object Cmd {
    case class Aim(x: Int) extends Cmd

    case class Forward(x: Int) extends Cmd
  }

  case class State(horizontal: Int, depth: Int, aim: Int)

  object State {
    def empty: State = State(horizontal = 0, depth = 0, aim = 0)
  }


}

