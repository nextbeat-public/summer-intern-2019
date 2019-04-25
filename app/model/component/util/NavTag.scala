/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.component.util

// データ定義: リンク情報
//~~~~~~~~~~~~~~~~~~~~~~~~
case class ViewValueNavTag(
  title:      String,
  navigateTo: Option[String] = None,
  ellipsis:   Option[Int]    = None,
  forcewrap:  Boolean        = false
)
