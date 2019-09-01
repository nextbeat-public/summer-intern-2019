/*
 *   This file is part of the OMOTENASHI services.
 *
 *   For the full copyright and license information,
 *   please view the LICENSE file that was distributed with this source code.
 */

organization := "nextbeat"
name         := "nextbeat-summer-internship"
scalaVersion := "2.12.6"

// setting for resolvers
resolvers ++= Seq(
  "Typesafe Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Release"   at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshot"  at "https://oss.sonatype.org/content/repositories/snapshots/",
  "keyczar"            at "https://raw.githubusercontent.com/google/keyczar/master/java/maven/"
)

// required libraries
libraryDependencies ++= Seq(
  "com.typesafe.play"    %% "play-slick"               % "4.0.0",
  "com.typesafe.play"    %% "play-slick-evolutions"    % "4.0.0",
  "org.uaparser"         %% "uap-scala"                % "0.1.0",
  "com.mohiva"           %% "play-html-compressor"     % "0.7.1",
  "net.logstash.logback"  % "logstash-logback-encoder" % "5.1",
  "org.asynchttpclient"   % "async-http-client"        % "2.5.3",
  "com.h2database"        % "h2"                       % "1.4.199",
  "com.github.t3hnar"    %% "scala-bcrypt"             % "4.1",
  guice
)

// setting for project
lazy val root = (project in file("."))
  .enablePlugins(FlywayPlugin)
  .enablePlugins(SbtWeb)
  .enablePlugins(PlayScala)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
unmanagedResourceDirectories in Compile += (baseDirectory { _ / "app" / "views" }).value

// Template Engine.
// TwirlKeys.templateImports := Seq.empty
sourceDirectories in (Compile, TwirlKeys.compileTemplates) := Seq(baseDirectory.value / "app")

// Play routes setting.
import play.sbt.routes.RoutesKeys
RoutesKeys.routesImport := Seq.empty

// scala compile options
scalacOptions ++= Seq(
  "-deprecation",            // Emit warning and location for usages of deprecated APIs.
  "-feature",                // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",              // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings",        // Fail the compilation if there are any warnings.
//"-Xlint",                  // Enable recommended additional warnings.
  "-Xlint:-unused,_",        // Enable recommended additional warnings.
  "-Ywarn-adapted-args",     // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code",        // Warn when dead code is identified.
//"-Ywarn-unused:imports",   // Warn if an import selector is not referenced.
  "-Ywarn-inaccessible",     // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen"     // Warn when numerics are widened.
)

javaOptions ++= Seq(
  "-Dconfig.file=conf/application.conf",
  "-Dlogger.file=conf/logback.xml"
)
fork in run := true

// Database Setup
flywayDriver    := "org.h2.Driver"
flywayUrl       := "jdbc:h2:tcp://localhost/./nextbeat;MODE=MySQL"
flywayUser      := "nextbeat"
flywayPassword  := "pass"
flywayLocations += "db/migration"

// Setting for prompt
import com.scalapenos.sbt.prompt._
val defaultTheme = PromptTheme(List(
  text("[SBT] ", fg(green)),
  text(state => { Project.extract(state).get(organization) + "@" }, fg(magenta)),
  text(state => { Project.extract(state).get(name) },               fg(magenta)),
  text(":", NoStyle),
  gitBranch(clean = fg(green), dirty = fg(yellow)).padLeft("[").padRight("]"),
  text(" > ", NoStyle)
))
promptTheme := defaultTheme
shellPrompt := (implicit state => promptTheme.value.render(state))
