/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.app

import play.api.i18n.I18nSupport
import play.api.mvc.{ AbstractController, MessagesControllerComponents }
import model.site.app.SiteViewValueNewUser
import model.component.util.ViewValuePageLayout

// 登録: 新規ユーザー
//~~~~~~~~~~~~~~~~~~~~~
class NewUserController @javax.inject.Inject()(
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {

  /**
   * ページの表示
   */
  def viewForApp = Action { implicit request =>
    val vv = SiteViewValueNewUser(
      layout = ViewValuePageLayout(id = request.uri)
    )
    Ok(views.html.site.app.new_user.Main(vv))
  }
}
