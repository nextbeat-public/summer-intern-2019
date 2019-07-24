/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import play.api.mvc._
import play.api.mvc.Results._
import play.api.http.HttpErrorHandler
import scala.concurrent.Future

// PlayFramework エラーハンドラー
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@javax.inject.Singleton
class ErrorHandler extends HttpErrorHandler {

  /** クライアント・エラー発生時の処理 */
  def onClientError(rh: RequestHeader, code: Int, message: String): Future[Result] =
    Future.successful(
      Status(code)("A client error occurred: " + message)
    )

  /** サーバ・エラー発生時の処理 */
  def onServerError(rh: RequestHeader, ex: Throwable): Future[Result] =
    Future.successful(
      InternalServerError("A server error occurred: " + ex.getMessage)
    )
}
