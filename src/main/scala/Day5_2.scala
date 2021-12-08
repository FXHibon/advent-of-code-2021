import cats.effect.IO

import scala.io.StdIn
import scala.util.Try

object Day5_2 {

  case class Point(x: Int, y: Int)
  object Point {
    // "x,y"
    def unsafeParse(str: String): Point = str match {
      case s"$x,$y" => Point(x.toInt, y.toInt)
    }
  }

  case class Segment(from: Point, to: Point) {

    lazy val deltaX: Int = math.abs(from.x - to.x)
    lazy val deltaY: Int = math.abs(from.y - to.y)

    lazy val xDirection: Direction = (from.x, to.x) match {
      case (x1, x2) if x1 < x2 => Direction.Positive
      case (x1, x2) if x1 > x2 => Direction.Negative
      case (_, _)              => Direction.Neutral
    }

    lazy val yDirection: Direction = (from.y, to.y) match {
      case (y1, y2) if y1 < y2 => Direction.Positive
      case (y1, y2) if y1 > y2 => Direction.Negative
      case (_, _)              => Direction.Neutral
    }

    lazy val minX: Int = math.min(from.x, to.x)
    lazy val maxX: Int = math.max(from.x, to.x)

    lazy val minY: Int = math.min(from.y, to.y)
    lazy val maxY: Int = math.max(from.y, to.y)
  }

  object Segment {
    // val str = "959,53 -> 959,739"
    def unsafeParse(str: String): Option[Segment] = str match {
      case s"$p1 -> $p2" =>
        Try(Segment(Point.unsafeParse(p1), Point.unsafeParse(p2))).toOption
    }
  }
  sealed trait Direction

  object Direction {
    case object Positive extends Direction
    case object Negative extends Direction
    case object Neutral  extends Direction
  }

  case class Cell(depth: Int)

  object Cell {
    def empty: Cell = Cell(depth = 0)
  }

  def showGrid(cells: List[List[Cell]]): String = {
    cells
      .map { line =>
        line
          .map {
            case Cell(0) => "."
            case Cell(n) => n.toString
          }
          .mkString("")
      }
      .mkString("\n")
  }

  def solve: IO[String] = DataSource
    .dataForDay(5)
    .map(Segment.unsafeParse)
    .collect { case Some(value) =>
      value
    }
    .compile
    .toList
    .map { segments =>
      {
        val maxX = segments.maxBy(_.maxX).maxX
        val maxY = segments.maxBy(_.maxY).maxY
        val grid = List.fill(maxY + 1)(List.fill(maxX + 1)(Cell.empty))

        segments.foldLeft(grid) { (curGrid, segment) =>
          drawGenericSegment(curGrid, segment)
        }

      }
    }
    .map { lists =>
      lists.flatten.count { case Cell(depth) =>
        depth >= 2
      }
    }
    .map(score => s"score($score)")

  private def drawGenericSegment(
      curGrid: List[List[Cell]],
      segment: Segment
  ): List[List[Cell]] = {

    val xs = segment.xDirection match {
      case Direction.Positive => (segment.from.x to segment.to.x).toList
      case Direction.Negative => (segment.from.x to segment.to.x by (-1)).toList
      case Direction.Neutral  => List.fill(segment.deltaY + 1)(segment.from.x)
    }

    val ys = segment.yDirection match {
      case Direction.Positive => (segment.from.y to segment.to.y).toList
      case Direction.Negative => (segment.from.y to segment.to.y by (-1)).toList
      case Direction.Neutral  => List.fill(segment.deltaX + 1)(segment.from.y)
    }

    val coordToUpdate = xs.zip(ys)

    coordToUpdate.foldLeft(curGrid) { (cur, coord) =>
      val (x, y) = coord
      val updatedLine =
        cur(y).updated(x, cur(y)(x).copy(depth = cur(y)(x).depth + 1))
      cur.updated(y, updatedLine)
    }
  }

}
