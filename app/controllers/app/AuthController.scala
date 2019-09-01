/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.app

import model.component.util.ViewValuePageLayout
import model.site.app.SiteViewValueAuthLogin
import persistence.udb.dao.{ UserDAO, UserPasswordDAO }
import play.api.i18n.I18nSupport
import play.api.mvc.{ AbstractController, MessagesControllerComponents }
import com.github.t3hnar.bcrypt._

import scala.concurrent.Future

// 認証処理
//~~~~~~~~~~~~~~~~~~~~~
class AuthController @javax.inject.Inject()(
  val daoUser: UserDAO,
  val daoUserPassword: UserPasswordDAO,
  val cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
   * ページの表示
   */
  def login = Action { implicit request =>
    val vv = SiteViewValueAuthLogin(
      layout = ViewValuePageLayout(id = request.uri),
      form   = SiteViewValueAuthLogin.formLogin
    )
    Ok(views.html.site.app.auth.Login(vv))
  }

  def loginCommit = Action.async { implicit request =>
    SiteViewValueAuthLogin.formLogin.bindFromRequest.fold(
      errors => {
        val vv = SiteViewValueAuthLogin(
          layout = ViewValuePageLayout(id = request.uri),
          form   = errors
        )
        Future.successful(
          BadRequest(views.html.site.app.auth.Login(vv))
        )
      },
      form => {
        for {
          userOpt     <- daoUser.findByEmail(form.email)
          passwordOpt <- userOpt match {
            case Some(user) => daoUserPassword.get(user.id.get)
            case None       => Future.successful(None)
          }
        } yield
          passwordOpt match {
            case Some(password) if form.password.isBcryptedSafe(password.hash).getOrElse(false) =>
              Redirect("/home/")
                .withSession(
                  request.session + ("user_id" -> password.id.toString)
                )
            case _ =>
              val vv = SiteViewValueAuthLogin(
                layout = ViewValuePageLayout(id = request.uri),
                form   = SiteViewValueAuthLogin.formLogin
              )
              BadRequest(views.html.site.app.auth.Login(vv))
          }
      }
    )
  }
}
