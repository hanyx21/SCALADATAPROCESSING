ThisBuild / scalaVersion := "3.7.2"

lazy val sparkVersion = "3.5.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion cross CrossVersion.for3Use2_13,
  "org.apache.spark" %% "spark-sql"  % sparkVersion cross CrossVersion.for3Use2_13
)

// Allow access to internal Java APIs (sun.nio.ch) blocked by Java modules system
Compile / run / javaOptions ++= Seq(
  "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
  "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
)
