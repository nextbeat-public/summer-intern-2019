/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.udb.dao

import java.time.LocalDateTime
import scala.concurrent.{ Future, ExecutionContext }

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.udb.model.{ User, UserSession }

// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class UserSessionDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
)(implicit ex: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[UserSessionsTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * セッション情報を保存する
   */
  def update(data: UserSession): Future[Unit] =
    db.run {
      val row = slick.filter(_.id === data.id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => slick += data
          case Some(_) => row.update(data)
        }
      } yield ()
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class UserSessionsTable(tag: Tag) extends Table[UserSession](tag, "udb_user_session") {

    // Table's columns
    /* @1 */ def id        = column[User.Id]       ("id", O.PrimaryKey)  // ユーザID
    /* @2 */ def token     = column[String]        ("token")             // トークン
    /* @3 */ def exprity   = column[LocalDateTime] ("exprity")           // 有効期限
    /* @4 */ def updatedAt = column[LocalDateTime] ("updated_at")        // データ更新日
    /* @5 */ def createdAt = column[LocalDateTime] ("created_at")        // データ作成日

    // Indexes
    def ukey01 = index("ukey01", token, unique = true)

    // The * projection of the table
    def * = (id, token, exprity, updatedAt, createdAt) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (UserSession.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => UserSession.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
