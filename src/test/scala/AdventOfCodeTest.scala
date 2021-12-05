import Day3_2.{BitCriteria, FilteringState}

class AdventOfCodeTest extends munit.CatsEffectSuite {

  test("day_3.1_run") {
    val obtained = Day3_1.run
    val expected = "gamma(190) x epsilon(3905) == power_consumption(741950)"
    assertIO(obtained = obtained, returns = expected)
  }

  test("day_3.2_filterByBitCriteria") {
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
    val obtained1 = Day3_2.filterByBitCriteria(FilteringState.fromList(testData, bitCriteria = BitCriteria.Most()))
    val obtained2 = Day3_2.filterByBitCriteria(FilteringState.fromList(testData, bitCriteria = BitCriteria.Least()))

    assertEquals(obtained = obtained1, expected = "10111")
    assertEquals(obtained = obtained2, expected = "01010")
  }

  test("day_3.2_run") {
    val obtained = Day3_2.run
    val expected = "oxygenRating(282) x co2Rating(3205) == life_support_rating(903810)"
    assertIO(obtained, expected)
  }

}
