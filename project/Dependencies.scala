import sbt._

object Dependencies {

  val resolutionRepos = Seq(
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  val scalaReflect  = "org.scala-lang"                          %   "scala-reflect"               % "2.11.8"
  val scalatest     = "org.scalatest"                           %%  "scalatest"                   % "2.2.6"
  val specs2        = "org.specs2"                              %%  "specs2"                      % "3.7"
  val logback       = "ch.qos.logback"                          %   "logback-classic"             % "1.1.7"
  val thrift        = "org.apache.thrift"                       %   "libthrift"                   % "0.9.3"
  val netty         = "io.netty"                                %   "netty-all"                   % "4.0.36.Final"
  val eurekaClient  = "com.netflix.eureka"                      %   "eureka-client"               % "1.4.6"
}

