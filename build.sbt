name := "adentis_challenge_sbt"

version := "1.0"

scalaVersion := "2.13.5"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6"

assemblyJarName in assembly := "order.jar"
target in assembly:= file("build/")

