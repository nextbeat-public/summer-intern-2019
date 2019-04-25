/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc.filters

import javax.inject.Inject
import scala.concurrent.{ Future, ExecutionContext }
import akka.stream.Materializer

import play.api.Logging
import play.api.mvc.{ Filter, Result, RequestHeader }
import play.api.libs.json.Json
import org.uaparser.scala.Parser

// アクセスログ解析
//~~~~~~~~~~~~~~~~~~
class AccessLogFilter @Inject()(
  implicit val mat: Materializer,
  implicit val ex:  ExecutionContext
) extends Filter with Logging {

  val IGNORE_PREFIX_LIST = Seq("/healthcheck", "/assets", "/ping")
  val MOBILE_UA_REGEX    = "(iPhone|webOS|iPod|Android|BlackBerry|mobile|SAMSUNG|IEMobile|OperaMobi)".r.unanchored

  /** フィルター処理 */
  def apply(invocation: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {
    val start = System.currentTimeMillis

    invocation(rh).map(result => {
      if (!IGNORE_PREFIX_LIST.exists(rh.path.startsWith(_))) {
        val elapsedTime = System.currentTimeMillis - start
        val client      = rh.headers.get("User-Agent").map(Parser.get.parse(_))
        val remoteAddr  = (
          rh.headers.get("X-Real-IP")       flatMap(_.split(",").map(_.trim).headOption) orElse
          rh.headers.get("X-Forwarded-For") flatMap(_.split(",").map(_.trim).headOption) getOrElse
          rh.remoteAddress
        )
        val data = Json.obj(
          "rid"                 -> rh.id,
          "host"                -> rh.host,
          "version"             -> rh.version,
          "method"              -> rh.method,
          "status"              -> result.header.status,
          "path"                -> rh.path,
          "uri"                 -> rh.uri,
          "uuid"                -> rh.cookies.get("_ga").map(_.value),
          "session_id"          -> rh.cookies.get("_gid").map(_.value),
          "uid"                 -> rh.cookies.get("_uid").map(_.value),
          "geoid_current"       -> rh.cookies.get("_lcid").map(_.value),
          "geoid_interested"    -> rh.cookies.get("_liid").map(_.value),
          "campaign_code"       -> rh.cookies.get("campaign_code").map(_.value),
          "remote_address"      -> remoteAddr,
          "client_os"           -> client.map(_.os.family),
          "client_os_verion"    -> client.map(v => Seq(v.os.major, v.os.minor, v.os.patch, v.os.patchMinor).flatten.mkString(".")),
          "client_device"       -> client.map(_.device.family),
          "client_device_brand" -> client.map(_.device.brand),
          "client_device_model" -> client.map(_.device.model),
          "client_ua"           -> client.map(_.userAgent.family),
          "client_ua_version"   -> client.map(v => Seq(v.userAgent.major, v.userAgent.minor, v.userAgent.patch).flatten.mkString(".")),
          "client_is_mobile"    -> rh.headers.get("User-Agent").map(MOBILE_UA_REGEX.findFirstIn(_).isDefined),
          "user_agent"          -> rh.headers.get("User-Agent"),
          "referer"             -> rh.headers.get("Referer"),
          "elapsed_time"        -> elapsedTime,
          "headers"             -> rh.headers.headers.map(v => Json.obj(v._1 -> v._2)),
          "cookies"             -> rh.cookies.map(v => Json.obj(v.name -> v.value)),
        )
        logger.info(data.toString)
      }
      result
    })
  }
}
