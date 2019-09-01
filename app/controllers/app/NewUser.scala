/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.app

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import model.site.app.SiteViewValueNewUser
import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO

// 登録: 新規ユーザー
//~~~~~~~~~~~~~~~~~~~~~
class NewUserController @javax.inject.Inject()(
  val daoLocation: LocationDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
   * ページの表示
   */
  def viewForApp = Action.async { implicit request =>
    for {
      locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
    } yield {
      val vv = SiteViewValueNewUser(
        layout   = ViewValuePageLayout(id = request.uri),
        location = locSeq,
        form     = SiteViewValueNewUser.formNewUser
      )
      Ok(views.html.site.app.new_user.Main(vv))
    }
  }
}
