/*
 * Copyright ixias.net All Rights Reserved.
 *
 * Use of this source code is governed by an MIT-style license
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc.util

import scala.reflect._

/**
 * The Enums based on sealed classes
 *
 * < Example >
 * sealed abstract class Color(red: Double, green: Double, blue: Double)
 * object Color extends EnumOf[Color] {
 *   case object Red   extends Color(1, 0, 0)
 *   case object Green extends Color(0, 1, 0)
 *   case object Blue  extends Color(0, 0, 1)
 *   case object White extends Color(0, 0, 0)
 *   case object Black extends Color(1, 1, 1)
 * }
 *
 */
trait Enum       extends Serializable
trait EnumStatus extends Enum { val code: Short }

/**
 * The operation of EnumStatus
 */
object EnumStatus {
  abstract class Of[T <: EnumStatus]
    (implicit ttag: ClassTag[T]) extends Enum.Of[T] {
    def apply(code: Short): T = this.find(_.code == code).get
  }
}

/**
 * The based operation of Enum
 */
object Enum {
  abstract class Of[T <: Enum](implicit ttag: ClassTag[T]) { self =>
    import runtime.universe._

    // --[ Methods ]------------------------------------------------------------
    /**
     * The list of values for Enumeration.
     */
    lazy val values: Seq[T] = {
      val mirror = runtimeMirror(self.getClass.getClassLoader)
      val symbol = mirror.classSymbol(self.getClass)
      symbol.toType.members.sorted.filter(_.isModule)
        .map(m => symbol.isModuleClass match {
          case true  => mirror.reflectModule(m.asModule).instance
          case false => mirror.reflect(self).reflectModule(m.asModule).instance
        })
        .collect({ case v: T => v })
    }

    // --[ Methods ]-------------------------------------------------------------=
    /**
     * Finds the first element of the sequence satisfying a predicate, if any.
     */
    def find(f: T => Boolean): Option[T] = values.find(f)

    /**
     * Selects all elements of this Enum which satisfy a predicate.
     */
    def filter(f: T => Boolean): Seq[T] = values.filter(f)

    /**
     * Retrieve the index number of the member passed in the values picked up by this enum
     */
    def indexOf[M >: T](member: M): Int = values.indexOf(member)

    // --[ Methods ]-------------------------------------------------------------=
    /**
     * Optionally returns an enum's member for a given name.
     */
    def withNameOption(name: String): Option[T] =
      values.find(v => v.toString == name)

    /**
     * Optionally returns an enum's member for a given name, disregarding case
     */
    def withNameInsensitiveOption(name: String): Option[T] =
      values.find(v => v.toString.toLowerCase == name.toLowerCase)

    /**
     * Retrieve an enum's member for a given name
     */
    def withName(name: String): T =
      withNameOption(name).getOrElse(
        throw new NoSuchElementException(s"$name is not a member of Enum $this"))

    /**
     * Retrieve an enum's member for a given name, disregarding case
     */
    def withNameInsensitive(name: String): T =
      withNameInsensitiveOption(name).getOrElse(
        throw new NoSuchElementException(s"$name is not a member of Enum $this"))
  }
}

