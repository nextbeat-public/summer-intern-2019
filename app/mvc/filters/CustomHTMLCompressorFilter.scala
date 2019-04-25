/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc.filters

import javax.inject.Inject
import akka.stream.Materializer
import play.api.Configuration

import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import com.mohiva.play.htmlcompressor.HTMLCompressorFilter

// HTMLの圧縮処理
//~~~~~~~~~~~~~~~~
class CustomHTMLCompressorFilter @Inject() (
  val configuration: Configuration,
  val mat:           Materializer
) extends HTMLCompressorFilter {

  override val compressor: HtmlCompressor = {
    val client = new HtmlCompressor()
    client.setRemoveSurroundingSpaces("br,p,div,span,li");
    client
  }
}
