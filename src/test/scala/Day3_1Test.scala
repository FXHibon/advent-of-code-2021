class Day3_1Test extends munit.CatsEffectSuite {

  test("day_3.1") {
    val obtained = Day3_1.run
    val expected = "gamma(190) x epsilon(3905) == power_consumption(741950)"
    assertIO(obtained = obtained, returns = expected)
  }

}
