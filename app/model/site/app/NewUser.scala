/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.app

import play.api.data.Form
import model.component.util.ViewValuePageLayout
import persistence.udb.model.User

// 登録: 新規ユーザー
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueNewUser(
  layout: ViewValuePageLayout
) {
  /** フォーム定義 */
  lazy val form: Form[User] = User.form
}
