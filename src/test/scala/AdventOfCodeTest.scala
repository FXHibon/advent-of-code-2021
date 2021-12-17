import Day3_2.{BitCriteria, FilteringState}

class AdventOfCodeTest extends munit.CatsEffectSuite {

  test("day_3.1_rotateLines".ignore) {
    val obtained = Day3_1.rotateLines(List("001", "101", "111", "000"))
    val expected = List(
      List("0", "1", "1", "0"),
      List("0", "0", "1", "0"),
      List("1", "1", "1", "0")
    )
    assertEquals(obtained, expected)
  }

  test("day_3.1_solve".ignore) {
    val obtained = Day3_1.solve
    val expected = "gamma(190) x epsilon(3905) == power_consumption(741950)"
    assertIO(obtained = obtained, returns = expected)
  }

  test("day_3.2_filterByBitCriteria".ignore) {
    val testData = List(
      "00100",
      "11110",
      "10110",
      "10111",
      "10101",
      "01111",
      "00111",
      "11100",
      "10000",
      "11001",
      "00010",
      "01010"
    )
    val obtainedMost = Day3_2.filterByBitCriteria(
      FilteringState(testData, bitCriteria = BitCriteria.Most())
    )
    val obtainedLeast = Day3_2.filterByBitCriteria(
      FilteringState(testData, bitCriteria = BitCriteria.Least())
    )

    assertEquals(obtained = obtainedMost, expected = "10111")
    assertEquals(obtained = obtainedLeast, expected = "01010")
  }

  test("day_3.2_solve".ignore) {
    val obtained = Day3_2.solve
    val expected =
      "oxygenRating(282) x co2Rating(3205) == life_support_rating(903810)"
    assertIO(obtained = obtained, returns = expected)
  }

  test("day_4.1_Grid.isComplete".ignore) {
    val testData = List(
      Day4_1.Grid(
        List(
          List(
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1)
          )
        )
      ) -> false,
      Day4_1.Grid(
        List(
          List(
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Marked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          )
        )
      ) -> true,
      Day4_1.Grid(
        List(
          List(
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          ),
          List(
            Day4_1.Cell.Marked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1),
            Day4_1.Cell.Unmarked(1)
          )
        )
      ) -> true
    )

    testData.foreach { case (data, expected) =>
      assertEquals(obtained = data.isComplete, expected = expected)
    }

  }

  test("day_4.1_fillGrid".ignore) {
    val grid = Day4_1.Grid(
      List(
        List(
          Day4_1.Cell.Marked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Unmarked(3),
          Day4_1.Cell.Unmarked(4),
          Day4_1.Cell.Unmarked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Marked(2),
          Day4_1.Cell.Unmarked(3),
          Day4_1.Cell.Unmarked(4),
          Day4_1.Cell.Unmarked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Marked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Marked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Marked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Marked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Marked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Marked(5)
        )
      )
    )
    val expected = Day4_1.Grid(
      List(
        List(
          Day4_1.Cell.Marked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Unmarked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Unmarked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Marked(2),
          Day4_1.Cell.Unmarked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Unmarked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Marked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Marked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Marked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Marked(5)
        ),
        List(
          Day4_1.Cell.Unmarked(1),
          Day4_1.Cell.Unmarked(2),
          Day4_1.Cell.Marked(3),
          Day4_1.Cell.Marked(4),
          Day4_1.Cell.Marked(5)
        )
      )
    )
    val obtained = Day4_1.fillGrid(grid, 4)
    assertEquals(obtained, expected)
  }

  test("day_4.1_solve".ignore) {
    val obtained = Day4_1.solve
    val expected = "score(25023)"
    assertIO(obtained, expected)
  }

  test("day_4.2_solve".ignore) {
    val obtained = Day4_2.solve
    val expected = "score(2634)"
    assertIO(obtained, expected)
  }

  test("day_5.1_printGrid".ignore) {

    val grid = List(
      List(Day5_1.Cell(0), Day5_1.Cell(1), Day5_1.Cell(0)),
      List(Day5_1.Cell(0), Day5_1.Cell(1), Day5_1.Cell(0)),
      List(Day5_1.Cell(0), Day5_1.Cell(2), Day5_1.Cell(2))
    )
    val obtained = Day5_1.showGrid(grid)
    val expected = """.1.
                     |.1.
                     |.22""".stripMargin
    assertNoDiff(obtained, expected)
  }

  test("day_5.1_solve".ignore) {
    val obtained = Day5_1.solve
    val expected = "score(5774)"
    assertIO(obtained, expected)
  }

  test("day_5.2_solve".ignore) {
    val obtained = Day5_2.solve
    val expected = "score(18423)"
    assertIO(obtained, expected)
  }

  test("day_6.1_solve".ignore) {
    val obtained = Day6_1.solve
    val expected = "fishes_size(394994)"
    assertIO(obtained, expected)
  }

  test("day_6.2_solve".ignore) {
    val obtained = Day6_2.solve
    val expected = "fishes_size(1765974267455)"
    assertIO(obtained, expected)
  }

  test("day_7.1_solve".ignore) {
    val obtained = Day7_1.solve
    val expected = "fuel(328187)"
    assertIO(obtained, expected)
  }

  test("day_7.2_solve".ignore) {
    val obtained = Day7_2.solve
    val expected = "fuel(91257582)"
    assertIO(obtained, expected)
  }

  test("day_8.1_solve".ignore) {
    val obtained = Day8_1.solve
    val expected = "size(456)"
    assertIO(obtained, expected)
  }

  test("day_8.2_solveMatrix") {
    val obtained = Day8_2.solveMatrix(
      Day8_2.Entry.fromString(
        "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
      )
    )
    val expected = 5353
    assertEquals(obtained, expected)
  }

  test("day_8.2_solve") {
    val obtained = Day8_2.solve
    val expected = "size(1091609)"
    assertIO(obtained, expected)
  }

}
