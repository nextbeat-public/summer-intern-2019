package form.user

import persistence.udb.model.User
import play.api.data._
import play.api.data.Forms._

// 施設検索フォーム
trait NewUserForm {
  // --[ フォーム定義 ]---------------------------------------------------------
  lazy val formForNewUser = Form(
    mapping(
      "nameLast"  -> nonEmptyText,
      "nameFirst" -> nonEmptyText,
      "email"     -> email,
      "pref"      -> nonEmptyText,
      "address"   -> nonEmptyText,
    )(Function.untupled(
      t => User(None, t._1, t._2, t._3, t._4, t._5)
    ))(User.unapply(_).map(
      t => (t._2, t._3, t._4, t._5, t._6)
    ))
  )
}
