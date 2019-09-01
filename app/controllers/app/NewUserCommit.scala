/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.app

import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{ AbstractController, MessagesControllerComponents }
import persistence.geo.dao.LocationDAO
import persistence.udb.dao.{ UserDAO, UserPasswordDAO }
import persistence.geo.model.Location
import model.site.app.SiteViewValueNewUser
import model.component.util.ViewValuePageLayout

// 登録: 新規ユーザ
//~~~~~~~~~~~~~~~~~~~~~
class NewUserCommitController @Inject()(
  val daoLocation: LocationDAO,
  val daoUser: UserDAO,
  val daoUserPassword: UserPasswordDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
   * 新規ユーザの登録
   */
  def application = Action.async { implicit request =>
    SiteViewValueNewUser.formNewUser.bindFromRequest.fold(
      errors => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        } yield {
          val vv = SiteViewValueNewUser(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq,
            form     = errors
          )
          BadRequest(views.html.site.app.new_user.Main(vv))
        }
      },
      form   => {
        for {
          id <- daoUser.add(form.toUser)
          _  <- daoUserPassword.add(form.toUserPassword(id))
        } yield {
          Redirect("/recruit/intership-for-summer-21")
            .withSession(
              request.session + ("user_id" -> id.toString)
            )
        }
      }
    )
  }

}
