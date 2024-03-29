organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val akkaV = "2.1.4"
  val sprayV = "1.1.0"
  Seq(
    "io.spray"            %   "spray-can"           % sprayV,
    "io.spray"            %   "spray-routing"       % sprayV,
    "io.spray"            %   "spray-testkit"       % sprayV,
    "com.typesafe.akka"   %%  "akka-actor"          % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"        % akkaV,
    "io.spray"            %%  "spray-json"          % "1.2.5",
    "com.typesafe"        %%  "scalalogging-slf4j"  % "1.0.1",
    "com.typesafe"        %   "config"              % "1.0.2",
    "org.specs2"          %%  "specs2"              % "2.2.3" % "test"
  )
}

seq(Revolver.settings: _*)
