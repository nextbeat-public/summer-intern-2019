/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.geo.model

import java.time.LocalDateTime
import mvc.util.EnumStatus

// 地域情報
//~~~~~~~~~~
case class Location(
  id:           Location.Id,          // 全国地方公共団体コード
  level:        Short,                // 階層レベル
  typedef:      Location.Typedef,     // 市区町村種別
  parent:       Option[Location.Id],  // 親となる地域情報ID
  urn:          String,               // 地域情報URL
  nameRegion:   String,               // 地方区分
  nameRegionEn: String,               // 地方区分(ラテン表記)
  namePref:     String,               // 都道府県
  namePrefEn:   String,               // 都道府県(ラテン表記)
  nameCity:     Option[String],       // 市
  nameCityEn:   Option[String],       // 市(ラテン表記)
  nameWard:     Option[String],       // 区
  nameWardEn:   Option[String],       // 区(ラテン表記)
  nameCounty:   Option[String],       // 郡
  nameCountyEn: Option[String],       // 郡(ラテン表記)
  updatedAt:    LocalDateTime = LocalDateTime.now, // データ更新日
  createdAt:    LocalDateTime = LocalDateTime.now  // データ作成日
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Location {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = String

  // --[ 地域情報の種別 ]-------------------------------------------------------
  sealed abstract class Typedef(val code: Short) extends EnumStatus
  object Typedef extends EnumStatus.Of[Typedef] {
    case object IS_PREF   extends Typedef(code = 1) // 都道府県
    case object IS_CITY   extends Typedef(code = 2) // 市
    case object IS_WARD   extends Typedef(code = 3) // 区
    case object IS_COUNTY extends Typedef(code = 4) // 群
    case object IS_TOWN   extends Typedef(code = 5) // 町村
  }

  // --[ 主要都市のエントリ ]---------------------------------------------------
  /**
   * 都道府県情報
   */
  object Pref {
    val IS_HOKKAIDO  = "01000"; val IS_SAITAMA   = "11000"; val IS_GIFU      = "21000"
    val IS_AOMORI    = "02000"; val IS_CHIBA     = "12000"; val IS_SHIZUOKA  = "22000"
    val IS_IWATE     = "03000"; val IS_TOKYO     = "13000"; val IS_AICHI     = "23000"
    val IS_MIYAGI    = "04000"; val IS_KANAGAWA  = "14000"; val IS_MIE       = "24000"
    val IS_AKITA     = "05000"; val IS_NIIGATA   = "15000"; val IS_SHIGA     = "25000"
    val IS_YAMAGATA  = "06000"; val IS_TOYAMA    = "16000"; val IS_KYOTO     = "26000"
    val IS_FUKUSHIMA = "07000"; val IS_ISHIKAWA  = "17000"; val IS_OSAKA     = "27000"
    val IS_IBARAKI   = "08000"; val IS_FUKUI     = "18000"; val IS_HYOGO     = "28000"
    val IS_TOCHIGI   = "09000"; val IS_YAMANASHI = "19000"; val IS_NARA      = "29000"
    val IS_GUMMA     = "10000"; val IS_NAGANO    = "20000"; val IS_WAKAYAMA  = "30000"

    val IS_TOTTORI   = "31000"; val IS_SAGA      = "41000"
    val IS_SHIMANE   = "32000"; val IS_NAGASAKI  = "42000"
    val IS_OKAYAMA   = "33000"; val IS_KUMAMOTO  = "43000"
    val IS_HIROSHIMA = "34000"; val IS_OITA      = "44000"
    val IS_YAMAGUCHI = "35000"; val IS_MIYAZAKI  = "45000"
    val IS_TOKUSHIMA = "36000"; val IS_KAGOSHIMA = "46000"
    val IS_KAGAWA    = "37000"; val IS_OKINAWA   = "47000"
    val IS_EHIME     = "38000"
    val IS_KOCHI     = "39000"
    val IS_FUKUOKA   = "40000"
  }

  // --[ データ変換マップ ]-----------------------------------------------------
  /**
   * 地域情報
   */
  sealed abstract class Region(val code: Short, val name: String) extends EnumStatus
  object Region extends EnumStatus.Of[Region] {
    case object IS_KANTO   extends Region(code = 1, name = "関東")
    case object IS_KINKI   extends Region(code = 2, name = "近畿")
    case object IS_TOKAI   extends Region(code = 3, name = "東海")
    case object IS_TOHOKU  extends Region(code = 4, name = "北海道・東北")
    case object IS_CHUBU   extends Region(code = 5, name = "北陸・甲信越")
    case object IS_CHUGOKU extends Region(code = 6, name = "中国")
    case object IS_SHIKOKU extends Region(code = 7, name = "四国")
    case object IS_KYUSHU  extends Region(code = 8, name = "九州・沖縄")

    /** 所属都道府県 */
    import Pref._
    lazy val IS_PREF_KANTO   = Seq(IS_TOKYO,     IS_KANAGAWA, IS_SAITAMA,   IS_CHIBA,    IS_IBARAKI,   IS_TOCHIGI,  IS_GUMMA)
    lazy val IS_PREF_KINKI   = Seq(IS_OSAKA,     IS_HYOGO,    IS_KYOTO,     IS_SHIGA,    IS_NARA,      IS_WAKAYAMA          )
    lazy val IS_PREF_TOKAI   = Seq(IS_AICHI,     IS_SHIZUOKA, IS_GIFU,      IS_MIE                                          )
    lazy val IS_PREF_TOHOKU  = Seq(IS_HOKKAIDO,  IS_MIYAGI,   IS_FUKUSHIMA, IS_AOMORI,   IS_IWATE,     IS_YAMAGATA, IS_AKITA)
    lazy val IS_PREF_CHUBU   = Seq(IS_NIIGATA,   IS_NAGANO,   IS_ISHIKAWA,  IS_TOYAMA,   IS_YAMANASHI, IS_FUKUI             )
    lazy val IS_PREF_CHUGOKU = Seq(IS_HIROSHIMA, IS_OKAYAMA,  IS_YAMAGUCHI, IS_SHIMANE,  IS_TOTTORI                         )
    lazy val IS_PREF_SHIKOKU = Seq(IS_EHIME,     IS_KAGAWA,   IS_TOKUSHIMA, IS_KOCHI                                        )
    lazy val IS_PREF_KYUSHU  = Seq(IS_FUKUOKA,   IS_KUMAMOTO, IS_KAGOSHIMA, IS_NAGASAKI, IS_OITA, IS_MIYAZAKI, IS_SAGA, IS_OKINAWA)
    lazy val IS_PREF_ALL     = IS_PREF_KANTO ++ IS_PREF_KINKI   ++ IS_PREF_TOKAI   ++ IS_PREF_TOHOKU ++
                               IS_PREF_CHUBU ++ IS_PREF_CHUGOKU ++ IS_PREF_SHIKOKU ++ IS_PREF_KYUSHU

    /** データマップ */
    lazy val map = Seq(
      Region.IS_KANTO   -> IS_PREF_KANTO,
      Region.IS_KINKI   -> IS_PREF_KINKI,
      Region.IS_TOKAI   -> IS_PREF_TOKAI,
      Region.IS_TOHOKU  -> IS_PREF_TOHOKU,
      Region.IS_CHUBU   -> IS_PREF_CHUBU,
      Region.IS_CHUGOKU -> IS_PREF_CHUGOKU,
      Region.IS_SHIKOKU -> IS_PREF_SHIKOKU,
      Region.IS_KYUSHU  -> IS_PREF_KYUSHU
    )

    /** 市区町村管理コード => 地域 */
    def fromLocationId(lid: Location.Id): Option[Region] = {
      val prefId = "%02d000".format(lid.take(2).toInt)
      map.collectFirst({ case v if v._2.contains(prefId) => v._1 })
    }

    /** 地域 => 市区町村管理コード */
    def findLocationIds(region: Region): Seq[Location.Id] = {
      map.collect({ case v if v._1 == region => v._2 }).flatten
    }
  }

}

