/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers

import play.api.mvc.InjectedController

// 基本処理
//~~~~~~~~~~
class ApplicationController extends InjectedController {

  /** リダイレクト処理 */
  def redirect(to: String, from: String = "") = Action {
    Redirect(to)
  }
}
