import sbt.{compilerPlugin, _}

import scala.collection.immutable

object Dependencies {

  val dependencies = List(
    "co.fs2"        %% "fs2-core"    % "3.2.3",
    "co.fs2"        %% "fs2-io"      % "3.2.3",
    "org.typelevel" %% "cats-effect" % "3.3.0"
  )

  val testDependencies = List(
    "org.scalameta" %% "munit"               % "0.7.29" % Test,
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"  % Test
  )

  val compilerDependencies = List(
    compilerPlugin(
      "org.polyvariant" % "better-tostring" % "0.3.11" cross CrossVersion.full
    )
  )

  val all: immutable.Seq[ModuleID] =
    dependencies ++ testDependencies ++ compilerDependencies

}
