/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.geo.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.geo.model.Location

// DAO: 地域情報
//~~~~~~~~~~~~~~~~~~
class LocationDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[LocationTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 地域情報の取得
   * 検索業件: ロケーションID (全国地方公共団体コード)
   */
  def get(id: Location.Id): Future[Option[Location]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * 地域情報の複数取得
   * 検索業件: ロケーションID (全国地方公共団体コード)
   */
  def filterByIds(ids: Seq[Location.Id]): Future[Seq[Location]] =
    db.run {
      slick
        .filter(_.id inSet ids)
        .result
    }

  /**
   * 都道府県IDより、地域情報を取得する
   */
  def filterByPrefId(id: Location.Id): Future[Seq[Location]] = {
    val prefCode = Some(id).filter(Location.Region.IS_PREF_ALL.contains(_)).map(_.take(2))
    db.run {
      slick
        .filter(_.id.take(2) === prefCode)
        .result
    }
  }

  // --[ テーブル定義 ] --------------------------------------------------------
  class LocationTable(tag: Tag) extends Table[Location](tag, "geo_location") {

    // MappedColumnType
    implicit val enumT1 = MappedColumnType.base[Location.Typedef, Short](v => v.code, Location.Typedef(_))

    // Table's columns
    /* @1  */ def id            = column[Location.Id]          ("id", O.PrimaryKey)
    /* @2  */ def level         = column[Short]                ("level")
    /* @3  */ def typedef       = column[Location.Typedef]     ("typedef")
    /* @4  */ def parent        = column[Option[Location.Id]]  ("parent")
    /* @5  */ def urn           = column[String]               ("urn")
    /* @6  */ def nameRegion    = column[String]               ("region")
    /* @7  */ def nameRegionEn  = column[String]               ("region_en")
    /* @8  */ def namePref      = column[String]               ("pref")
    /* @9  */ def namePrefEn    = column[String]               ("pref_en")
    /* @10 */ def nameCity      = column[Option[String]]       ("city")
    /* @11 */ def nameCityEn    = column[Option[String]]       ("city_en")
    /* @12 */ def nameWard      = column[Option[String]]       ("ward")
    /* @13 */ def nameWardEn    = column[Option[String]]       ("ward_en")
    /* @14 */ def nameCounty    = column[Option[String]]       ("county")
    /* @15 */ def nameCountyEn  = column[Option[String]]       ("county_en")
    /* @16 */ def updatedAt     = column[LocalDateTime]        ("updated_at")
    /* @17 */ def createdAt     = column[LocalDateTime]        ("created_at")

    // Indexes
    def ukey01 = index("ukey01", urn, unique = true)
    def  key01 = index("key02",  parent)

    // The * projection of the table
    def * = (
      id, level, typedef, parent, urn,
      nameRegion, nameRegionEn, namePref, namePrefEn,
      nameCity,   nameCityEn,   nameWard, nameWardEn, nameCounty, nameCountyEn, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Location.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Location.unapply(v).map(_.copy(
        _16 = LocalDateTime.now
      ))
    )
  }
}
