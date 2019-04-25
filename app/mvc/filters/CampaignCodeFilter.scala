/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc.filters

import javax.inject.Inject
import akka.stream.Materializer
import scala.concurrent.{ Future, ExecutionContext }
import play.api.mvc.{ Filter, Result, RequestHeader, Cookie }

// 流入経路管理: Cookie焼込
// GETパラメーターutm_sourceに値を仕込むことでアクセスログに流入経路を記録する
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class CampaignCodeFilter @Inject()(
  implicit val mat: Materializer,
  implicit val ex:  ExecutionContext
) extends Filter {

  val FMT_CAMPAIN_CODE = """^((?![\cA-\cZ\s",;\\])[\x00-\x7F])+$""".r
  val COOKIE_NAME      = """campaign_code"""
  val COOKIE_MAX_AGE   = Some(1800)
  val REQUEST_ATTR_KEY = play.api.libs.typedmap.TypedKey[Option[String]]("CampaignCode")

  /** フィルター処理 */
  def apply(invocation: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {
    val codeOpt1 = rh.getQueryString("utm_source")
    val codeOpt2 = rh.cookies.get(COOKIE_NAME).map(_.value)
    for {
      ok <- invocation({
        rh.addAttr(REQUEST_ATTR_KEY, codeOpt1.orElse(codeOpt2))
      })
    } yield codeOpt1 match {
      case Some(code @ FMT_CAMPAIN_CODE(_)) =>
        ok.withCookies(Cookie(COOKIE_NAME, code, COOKIE_MAX_AGE))
      case _ =>
        ok
    }
  }
}
