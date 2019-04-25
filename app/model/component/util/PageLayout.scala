/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.component.util

import play.api.i18n.Messages

// データ定義: レイアウト
//~~~~~~~~~~~~~~~~~~~~~~~~
case class ViewValuePageLayout(
  id:          String,                                       // ページ識別子
  breadcrumb:  Option[ViewValuePageBreadcrumb] = None,       // ページ階層表現
  pagination:  Option[ViewValuePagination]     = None,       // ページ情報
  messageAttr: Seq[(String, String)]           = Seq.empty   // メッセージに渡す変数値
) {

  // --[ メッセージ管理 ]-------------------------------------------------------
  /**
   * メッセージの取得
   */
  def messages(key: String, args: Any*)(implicit messages: Messages): String =
    messageAttr.foldLeft(
      messages(traversePath(_ + key), args))({
        case (message, (key, value)) => message.replaceAll(key, value)
      })

  /**
   * メッセージURNの階層を巡回する
   * 仕様) URLを設定PATHに変換する
   * 例)   /app/lp/li => app.lp.li
   */
  protected def traversePath(block: String => String): Seq[String] =
    id.replaceFirst("""^/""", "")
      .replaceAll("/", ".")
      .split('.')
      .inits.toSeq
      .map(parts => block(parts.length match {
        case 0 => ""
        case _ => parts.mkString(".") + "."
      }))
}
