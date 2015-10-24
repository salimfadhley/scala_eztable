lazy val root = (project in file(".")).
  settings(
    name := "scala_eztable",
    version := "1.0",
    scalaVersion := "2.11.7"
  )

libraryDependencies += "junit" % "junit" % "4.10"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.5"

