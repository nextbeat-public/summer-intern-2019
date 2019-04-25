/*
 * Copyright ixias.net All Rights Reserved.
 *
 * Use of this source code is governed by an MIT-style license
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers

import play.api.{ Mode, Environment, Configuration }
import play.api.mvc.{ Action, AnyContent }
import play.api.http.HttpErrorHandler
import play.api.Logging

@javax.inject.Singleton
class UIAssets @javax.inject.Inject() (
  env:  Environment,
  conf: Configuration,
  meta: AssetsMetadata,
  errorHandler: HttpErrorHandler
) extends AssetsBuilder(errorHandler, meta) with Logging {

  import controllers.Assets._

  protected val CF_ASSETS_DEV_DIR = "assets.dev.dirs"

  /** 開発モード時にAssetsを提供するディレクトリ・リスト */
  protected val basePaths: Seq[java.io.File] =
    conf.getOptional[Seq[String]](CF_ASSETS_DEV_DIR) match {
      case Some(dirs) => dirs.map(env.getFile).filter(_.exists)
      case None       => Seq(
        env.getFile("ui"),
        env.getFile("ui/src"),
        env.getFile("ui/build"),
        env.getFile("ui/dist"),
        env.getFile("target/web/public/main")
      ).filter(_.exists)
    }

  /** Assetsハンドラー */
  override def versioned(path: String, file: Asset): Action[AnyContent] =
    env.mode match {
      case Mode.Prod => super.versioned(path, file)
      case _         => basePaths.find(path => {
        val fullPath = path + "/" + file
        val resource = new java.io.File(fullPath)
        resource.isFile
      }) match {
        case Some(path) => {
          logger.info("serving %s/%s".format(path, file))
          super.versioned(path.toString, file)
        }
        case None => Action.async { implicit request =>
          errorHandler.onClientError(request, NOT_FOUND, "Resource not found by Assets controller")
        }
      }
    }
}
