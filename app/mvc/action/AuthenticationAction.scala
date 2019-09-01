package mvc.action

import persistence.udb.model.User
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

case class UserRequest[A](
  userId:  User.Id,
  request: Request[A]
) extends WrappedRequest[A](request)

case class AuthenticationAction()(implicit
  val executionContext: ExecutionContext
) extends ActionRefiner[Request, UserRequest] {

  protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = {
    val sUserIdOpt = request.session.get("user_id")
    val next = sUserIdOpt match {
      case None          => Left(Redirect("/login"))
      case Some(sUserId) =>
        val userId      = sUserId.toLong
        val userRequest = UserRequest(userId, request)
        Right(userRequest)
    }

    Future.successful(next)
  }
}
