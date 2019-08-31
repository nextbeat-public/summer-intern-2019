/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.udb.dao

import java.time.LocalDateTime

import persistence.udb.model.{ User, UserPassword }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class UserPasswordDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[UserPasswordTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * ユーザのpassword情報の取得
   */
  def get(id: User.Id): Future[Option[UserPassword]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * ユーザのpasswordを追加する
   */
  def add(data: UserPassword): Future[Int] =
    db.run {
      slick += data
    }

  /**
   * ユーザを削除した時 passwordも削除
   */
  def delete(id: User.Id): Future[Int] =
    db.run {
      slick
        .filter(_.id === id)
        .delete
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class UserPasswordTable(tag: Tag) extends Table[UserPassword](tag, "udb_user_password") {

    // Table's columns
    /* @1 */ def id        = column[User.Id]       ("id", O.PrimaryKey)  //ここいらんかもだけど一応
    /* @2 */ def hash      = column[String]        ("password")         // password hash下させて保存
    /* @3 */ def updatedAt = column[LocalDateTime] ("updated_at")       // データ更新日
    /* @4 */ def createdAt = column[LocalDateTime] ("created_at")       // データ作成日

    // The * projection of the table
    def * = (
      id, hash, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (UserPassword.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => UserPassword.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
