ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "advent-of-code",
    libraryDependencies ++= Dependencies.all
  )
