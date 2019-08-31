/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.app

import com.github.t3hnar.bcrypt._
import model.component.util.ViewValuePageLayout
import model.site.app.SiteViewValueNewUser.NewUserForm
import persistence.geo.model.Location
import persistence.udb.model.{ User, UserPassword }
import play.api.data.Form
import play.api.data.Forms._

// 登録: 新規ユーザー
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueNewUser(
  layout:   ViewValuePageLayout,
  location: Seq[Location],
  form:     Form[NewUserForm]
)

object SiteViewValueNewUser {

  // ユーザ登録フォーム
  //~~~~~~~~~~~~~
  case class NewUserForm(
    nameLast:  String,      // 名前 (姓)
    nameFirst: String,      // 名前 (名)
    email:     String,      // メールアドレス(重複あり)
    pref:      Location.Id, // 都道府県
    address:   String,      // 住所
    password:  String,      // パスワード
  ){
    def toUser =
      User(None, nameLast, nameFirst, email, pref, address)

    def toUserPassword(id: User.Id) =
      UserPassword(id, password.bcrypt)
  }

  // --[ フォーム定義 ]---------------------------------------------------------
  val formNewUser = Form(
    mapping(
      "nameLast"  -> nonEmptyText,
      "nameFirst" -> nonEmptyText,
      "email"     -> email,
      "pref"      -> nonEmptyText,
      "address"   -> nonEmptyText,
      "password"  -> nonEmptyText,
    )(NewUserForm.apply)(NewUserForm.unapply)
  )
}