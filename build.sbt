name := "activiti-identities"

organization := "com.lunatech"

version := "0.1-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies += "org.activiti" % "activiti-engine" % "5.10"

publishTo <<= version { (v: String) =>
  val path = if(v.trim.endsWith("SNAPSHOT")) "snapshots-public" else "releases-public"
  Some(Resolver.url("Lunatech Artifactory", new URL("http://artifactory.lunatech.com/artifactory/%s/" format path)))
}