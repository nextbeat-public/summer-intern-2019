/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.app

import model.component.util.ViewValuePageLayout
import model.site.app.SiteViewValueAuthLogin.LoginForm
import play.api.data.Form
import play.api.data.Forms._

// ログイン
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueAuthLogin (
  layout: ViewValuePageLayout,
  form:   Form[LoginForm]
)

object SiteViewValueAuthLogin {

  // ログインフォーム
//~~~~~~~~~~~~~~~~~~~~~
  case class LoginForm(
    email:    String,
    password: String,
  )

  val formLogin = Form(
    mapping(
      "email"    -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )
}
