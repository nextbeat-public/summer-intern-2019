/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.app

import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.geo.dao.LocationDAO
import persistence.udb.dao.UserDAO
import persistence.geo.model.Location
import persistence.udb.model.User.formForNewUser
import model.site.app.SiteViewValueNewUser
import model.component.util.ViewValuePageLayout

// 登録: 新規ユーザ
//~~~~~~~~~~~~~~~~~~~~~
class NewUserCommitController @Inject()(
  val daoLocation: LocationDAO,
  val userDAO: UserDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
   * 新規ユーザの登録
   */
  def application = Action.async { implicit request =>
    formForNewUser.bindFromRequest.fold(
      errors => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        } yield {
          val vv = SiteViewValueNewUser(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq
          )
          BadRequest(views.html.site.app.new_user.Main(vv, errors))
        }
      },
      user   => {
        for {
          userId <- userDAO.add(user)
        } yield {
          Redirect("/recruit/intership-for-summer-21")
            .withSession(
              request.session + ("user_id" -> userId.toString)
            )
        }
      }
    )
  }

}
