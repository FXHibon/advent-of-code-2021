import cats.effect.IO
import fs2.Chunk

import scala.annotation.tailrec

object Day4_1 {

  val GRID_SIZE = 5

  case class Grid(cells: List[List[Cell]]) {
    lazy val isComplete: Boolean = cells.exists(cells =>
      cells.count {
        case Cell.Marked(_)   => false
        case Cell.Unmarked(_) => true
      } == 0
    ) || (0 until GRID_SIZE).exists { index =>
      cells.map(line => line(index)).count {
        case Cell.Marked(_)   => false
        case Cell.Unmarked(_) => true
      } == 0
    }
  }

  sealed trait Cell {
    val value: Int
  }

  object Cell {
    case class Marked(value: Int)   extends Cell
    case class Unmarked(value: Int) extends Cell
  }

  def toGrid(lines: Chunk[String]): Grid = {
    Grid(cells = lines.map { line =>
      line
        .split(" ")
        .filter(_.trim.nonEmpty)
        .toList
        .map(_.toInt)
        .map(Cell.Unmarked.apply)
    }.toList)
  }

  def fillGrid(grid: Grid, number: Int): Grid = {
    grid.copy(cells = grid.cells.map { line =>
      line.map {
        case Cell.Unmarked(`number`) => Cell.Marked(number)
        case untouched               => untouched
      }
    })
  }

  @tailrec
  def fillGrids(
      grids: List[Grid],
      randomNumbers: List[Int],
      lastPickedNumber: Int = -1
  ): IO[Int] = {
    grids
      .collectFirst {
        case grid if grid.isComplete =>
          computeScore(lastPickedNumber = lastPickedNumber, winner = grid)
      } match {
      case Some(value) => IO(value)
      case None =>
        val pickedNumber = randomNumbers.head
        val nextNumbers  = randomNumbers.drop(1)
        val filledGrids  = grids.map(fillGrid(_, pickedNumber))
        fillGrids(filledGrids, nextNumbers, pickedNumber)
    }
  }

  private def computeScore(lastPickedNumber: Int, winner: Grid) = {
    winner.cells.flatten.collect { case Cell.Unmarked(value) =>
      value
    }.sum * lastPickedNumber
  }

  def solve: IO[String] = for {
    randomNumbers <- DataSource
      .dataForDay(4)
      .take(1)
      .map(_.split(",").map(_.toInt))
      .map(_.toList)
      .compile
      .lastOrError

    grids <- DataSource
      .dataForDay(4)
      .drop(1)
      .chunkN(n = GRID_SIZE, allowFewer = false)
      .map(toGrid)
      .compile
      .toList

    result <- fillGrids(grids, randomNumbers)

  } yield s"score($result)"
}
