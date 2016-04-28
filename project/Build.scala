import sbt.Keys._
import sbt._

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("netrift-root", file("."))
    .aggregate(netriftCore, netriftRegistry, netriftCoreTests, netriftRegistryTests)
    .settings(basicSettings: _*)
    .settings(noPublishing: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val netriftCore = Project("netrift-core", file("netrift-core"))
    .settings(netriftModuleSettings: _*)
    .settings(libraryDependencies ++=
      compile(thrift) ++
        compile(netty) ++
        test(scalatest)
    )


  lazy val netriftRegistry = Project("netrift-registry", file("netrift-registry"))
    .dependsOn(netriftCore)
    .settings(netriftModuleSettings: _*)
    .settings(libraryDependencies ++=
        compile(eurekaClient) ++
        test(scalatest)
    )


  lazy val netriftCoreTests = Project("netrift-core-tests", file("netrift-core-tests"))
    .dependsOn(netriftCore)
    .settings(netriftModuleSettings: _*)
    .settings(noPublishing: _*)
    .settings(libraryDependencies ++= test(scalatest))

  lazy val netriftRegistryTests = Project("netrift-registry-tests", file("netrift-registry-tests"))
    .dependsOn(netriftRegistry)
    .settings(netriftModuleSettings: _*)
    .settings(noPublishing: _*)
    .settings(libraryDependencies ++= test(scalatest))
}
