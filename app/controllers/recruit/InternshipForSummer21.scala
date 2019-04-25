/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.recruit

import play.api.i18n.I18nSupport
import play.api.mvc.{ AbstractController, MessagesControllerComponents }
import model.component.util.ViewValuePageLayout

// 21卒: サマーインターン
//~~~~~~~~~~~~~~~~~~~~~~~~~~
class InternshipForSummer21Controller @javax.inject.Inject()(
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {

  /**
   * ページの表示
   */
  def view = Action { implicit request =>
    val vv = ViewValuePageLayout(id = request.uri)
    Ok(views.html.site.recruit.internship_for_summer_21.Main(vv))
  }
}
