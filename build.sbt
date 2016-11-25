lazy val root = (project in file(".")).
  aggregate(app).
  settings(inThisBuild(List(
      organization := "org.refeed",
      scalaVersion := "2.11.8"
    )),
    name := "magic-root"
  )

lazy val app = (project in file("app")).
  settings(
    name := "magic"
  )

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies in ThisBuild += "com.github.mpilquist" %% "simulacrum" % "0.10.0"
