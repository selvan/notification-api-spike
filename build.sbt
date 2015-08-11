name := "Notification API"

version := "0.1"

scalaVersion := "2.11.4"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.3.12",
	"com.typesafe.akka" % "akka-stream-experimental_2.11" % "1.0",
	"com.typesafe.akka" % "akka-http-core-experimental_2.11" % "1.0",
	"com.typesafe.akka" % "akka-http-experimental_2.11" % "1.0",
	"org.mongodb" %% "casbah" % "2.8.2"
)
