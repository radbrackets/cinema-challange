ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Cinema",
    idePackagePrefix := Some("com.velocit.cinema")
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.15" % "test"
libraryDependencies += "joda-time" % "joda-time" % "2.12.5"
