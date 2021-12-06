import sbt._

import scala.collection.immutable

object Dependencies {

  val dependencies = List(
    "co.fs2" %% "fs2-core" % "3.2.2",
    "co.fs2" %% "fs2-io"   % "3.2.2"
  )

  val testDependencies = List(
    "org.scalameta" %% "munit"               % "0.7.29" % Test,
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"  % Test
  )

  val all: immutable.Seq[ModuleID] = dependencies ++ testDependencies

}
