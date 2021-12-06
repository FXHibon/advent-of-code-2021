import cats.effect.IO
import fs2.Chunk

import scala.annotation.tailrec
import scala.util.Try

object Day5_1 {

  case class Point(x: Int, y: Int)
  object Point {
    // "x,y"
    def unsafeParse(str: String): Point = str match {
      case s"$x,$y" => Point(x.toInt, y.toInt)
    }
  }

  case class Segment(from: Point, to: Point) {
    lazy val direction: Direction = orientation match {
      case Orientation.Horizontal =>
        if (to.x > from.x) Direction.Positive else Direction.Negative
      case Orientation.Vertical =>
        if (to.y > from.y) Direction.Positive else Direction.Negative
    }
    val orientation: Orientation = (from, to) match {
      case (Point(x1, _), Point(x2, _)) if x1 == x2 => Orientation.Vertical
      case (Point(_, y1), Point(_, y2)) if y1 == y2 => Orientation.Horizontal
      case (_, _) =>
        throw new IllegalStateException(
          s"The PM said only horizontal or vertical 😭 $this"
        )
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

  sealed trait Orientation

  object Orientation {
    case object Horizontal extends Orientation
    case object Vertical   extends Orientation
  }

  sealed trait Direction

  object Direction {
    case object Positive extends Direction
    case object Negative extends Direction
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
          segment.orientation match {
            case Orientation.Horizontal =>
              val toBeUpdated =
                segment.from.x to segment.to.x by (if (
                                                     segment.direction == Direction.Positive
                                                   ) 1
                                                   else -1)

              curGrid.updated(
                segment.from.y,
                curGrid(segment.from.y).zipWithIndex.map {
                  case (c, indexToUpdate)
                      if toBeUpdated.contains(indexToUpdate) =>
                    c.copy(depth = c.depth + 1)
                  case (c, _) => c
                }
              )
            case Orientation.Vertical =>
              val toBeUpdated =
                segment.from.y to segment.to.y by (if (
                                                     segment.direction == Direction.Positive
                                                   ) 1
                                                   else -1)

              curGrid.zipWithIndex.map {
                case (line, indexToUpdate)
                    if toBeUpdated.contains(indexToUpdate) =>
                  val c = line(segment.from.x)
                  line.updated(
                    segment.from.x,
                    c.copy(depth = c.depth + 1)
                  )
                case (line, _) => line
              }

          }
        }

      }
    }
    .map { lists =>
      lists.flatten.count { case Cell(depth) =>
        depth >= 2
      }
    }
    .map(score => s"score($score)")
}
