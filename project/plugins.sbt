/*
 *   This file is part of the Nextbeat services.
 *
 *   For the full copyright and license information,
 *   please view the LICENSE file that was distributed with this source code.
 */

addSbtPlugin("com.typesafe.play"     % "sbt-plugin"   % "2.7.1")
addSbtPlugin("com.scalapenos"        % "sbt-prompt"   % "1.0.2")
addSbtPlugin("com.mintbeans"         % "sbt-ecr"      % "0.10.0")
addSbtPlugin("com.github.gseitz"     % "sbt-release"  % "1.0.7")
addSbtPlugin("io.get-coursier"       % "sbt-coursier" % "1.1.0-M7")
addSbtPlugin("io.github.davidmweber" % "flyway-sbt"   % "5.2.0")
classpathTypes += "maven-plugin"
