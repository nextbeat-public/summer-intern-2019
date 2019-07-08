package form.facility

import play.api.data._
import play.api.data.Forms._
import persistence.geo.model.Location

// 施設検索Formパラメータ
case class FacilitySearch(
  locationIdOpt: Option[Location.Id]
)

// 施設検索フォーム
trait FacilitySearchForm {
  // --[ フォーム定義 ]---------------------------------------------------------
  lazy val formForFacilitySearch = Form(
    mapping(
      "locationId" -> optional(text),
    )(FacilitySearch.apply)(FacilitySearch.unapply)
  )
}

