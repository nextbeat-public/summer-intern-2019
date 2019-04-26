/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.udb.model

import java.time.LocalDateTime
import persistence.geo.model.Location

// ユーザ情報
//~~~~~~~~~~~~~
case class User(
  id:        Option[User.Id],                          // ユーザID
  nameFirst: String,                                   // 名前 (姓)
  nameLast:  String,                                   // 名前 (名)
  email:     Option[String]      = None,               // メールアドレス(重複あり)
  pref:      Option[Location.Id] = None,               // 都道府県
  address:   Option[String]      = None,               // 住所
  updatedAt: LocalDateTime       = LocalDateTime.now,  // データ更新日
  createdAt: LocalDateTime       = LocalDateTime.now   // データ作成日
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object User {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long
}

