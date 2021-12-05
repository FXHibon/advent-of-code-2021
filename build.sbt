ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "advent-of-code",
    libraryDependencies ++= Seq(
      "co.fs2" %% "fs2-core" % "3.2.2",
      "co.fs2" %% "fs2-io" % "3.2.2",
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
    )
  )
