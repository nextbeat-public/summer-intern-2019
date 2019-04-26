/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.geo.model

import java.time.LocalDateTime

// 地域情報
//~~~~~~~~~~
case class Location(
  id:        Option[Location.Id],                // 管理ID
  updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Location {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long
}

