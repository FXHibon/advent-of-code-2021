ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "advent-of-code",
    // available for 2.12, 2.13, 3.0
    libraryDependencies ++= Seq(
      "co.fs2" %% "fs2-core" % "3.2.2",
      "co.fs2" %% "fs2-io" % "3.2.2",
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test
    )
  )
