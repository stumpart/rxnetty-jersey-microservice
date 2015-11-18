name := """rx-websockets"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "junit"             % "junit"           % "4.12"  % "test",
  "com.novocode"      % "junit-interface" % "0.11"  % "test",
  "io.reactivex" % "rxnetty" % "0.4.13",
  "org.glassfish.jersey.core" % "jersey-server" % "2.22.1"
)


fork in run := true