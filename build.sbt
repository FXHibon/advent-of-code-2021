ThisBuild / scalaVersion := "3.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "advent-of-code",
    libraryDependencies ++= Dependencies.all
  )
