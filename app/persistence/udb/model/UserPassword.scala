/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.udb.model

import java.time.LocalDateTime

// ユーザ情報
//~~~~~~~~~~~~~
case class UserPassword(
  id:        User.Id,
  hash:      String,
  updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
)
