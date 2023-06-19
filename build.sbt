//ThisBuild / version := "0.1.0-SNAPSHOT"
//
ThisBuild / scalaVersion := "3.2.1"
//
//lazy val root = (project in file("."))
//  .settings(
//    name := "Wines"
//  )
name := "Wines"

val akkaHttpVersion = "10.5.0"
val akkaVersion    = "2.8.0"
val scala3Version = "3.1.3"


libraryDependencies ++= Seq(
  ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion),
  ("com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion),
  ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion),
  ("com.typesafe.akka" %% "akka-stream" % akkaVersion),
  "org.postgresql" % "postgresql" % "42.5.4",
  "ch.qos.logback" % "logback-classic" % "1.4.7",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % Test
)
