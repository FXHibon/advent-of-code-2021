import cats.effect.IO
import cats.implicits._

object Day9_1 {

  def solve: IO[String] = DataSource
    .dataForDay(9)
    .map(_.toCharArray.map(_.toString.toInt).toList)
    .zipWithPreviousAndNext
    .collect { case (oPrev, current, oNext) =>
      current.zipWithIndex.collect {
        case (firstValue, i)
            if i == 0 && isLower(
              candidate = firstValue,
              up = oPrev.map(_(i)),
              down = oNext.map(_(i)),
              left = None,
              right = current(i + 1).pure[Option]
            ) =>
          firstValue

        case (middleValue, i)
            if i < (current.length - 1) && i > 0 && isLower(
              candidate = middleValue,
              up = oPrev.map(_(i)),
              down = oNext.map(_(i)),
              left = current(i - 1).pure[Option],
              right = current(i + 1).pure[Option]
            ) =>
          middleValue

        case (lastValue, i)
            if i == (current.length - 1) && isLower(
              candidate = lastValue,
              up = oPrev.map(_(i)),
              down = oNext.map(_(i)),
              left = current(i - 1).pure[Option],
              right = None
            ) =>
          lastValue

      }
    }
    .map(_.map(_ + 1))
    .map(_.sum)
    .foldMonoid
    .compile
    .lastOrError
    .map(r => s"sum_lower_points($r)")

  def isLower(
      candidate: Int,
      up: Option[Int],
      down: Option[Int],
      left: Option[Int],
      right: Option[Int]
  ): Boolean = {
    up.forall(_ > candidate) &&
    down.forall(_ > candidate) &&
    left.forall(_ > candidate) &&
    right.forall(_ > candidate)
  }

}
