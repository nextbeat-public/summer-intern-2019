/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import javax.inject.{Inject, Provider}
import play.api.{Configuration, Environment, OptionalSourceMapper}
import play.api.http.DefaultHttpErrorHandler
import play.api.routing.Router

@javax.inject.Singleton
class ErrorHandler @Inject() (
  env:          Environment,
  config:       Configuration,
  sourceMapper: OptionalSourceMapper,
  router:       Provider[Router]
) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {
}
