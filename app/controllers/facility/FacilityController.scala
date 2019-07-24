/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.facility

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.facility.dao.FacilityDAO
import persistence.facility.model.Facility.formForFacilitySearch
import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import model.site.facility.SiteViewValueFacilityList
import model.component.util.ViewValuePageLayout


import persistence.facility.model.Facility


// 施設
//~~~~~~~~~~~~~~~~~~~~~
class FacilityController @javax.inject.Inject()(
  val facilityDao: FacilityDAO,
  val daoLocation: LocationDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
    * 施設一覧ページ
    */
  def list = Action.async { implicit request =>
    for {
      locSeq      <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
      facilitySeq <- facilityDao.findAll
    } yield {
      val vv = SiteViewValueFacilityList(
        layout     = ViewValuePageLayout(id = request.uri),
        location   = locSeq,
        facilities = facilitySeq
      )
      Ok(views.html.site.facility.list.Main(vv, formForFacilitySearch))
    }
  }

  /**
    * 施設詳細
    */

  def edit(id: String) = Action.async { implicit request =>
    for{
      facility <- facilityDao.get(id.toLong)
    }yield{
      val vv = ViewValuePageLayout(id = request.uri)
      println(facility)
      Ok(views.html.site.facility.edit.Main(vv, facility.get))

    }
  }


  def update(id: String) = Action { implicit request =>


    //formForFacilityEdit.bindFrom.fold(
      //errors => {
        //  BadRequest(errors)
      //},
      //form => {

        //println(form.name)
        //println(form.address)
        //println(form.description)
        //String[] input = requestParam.get("name");
        //String name = input[0];

        //for {

        //} yield {
        println("################################################")
        //println(name)
        //println(address)
        //println(description)

        Ok("sasa")
        //}

      //}
    //)

  }




  /**
   * 施設検索
   */
  def search = Action.async { implicit request =>
    formForFacilitySearch.bindFromRequest.fold(
      errors => {
       for {
          locSeq      <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
          facilitySeq <- facilityDao.findAll
        } yield {
          val vv = SiteViewValueFacilityList(
            layout     = ViewValuePageLayout(id = request.uri),
            location   = locSeq,
            facilities = facilitySeq
          )
          BadRequest(views.html.site.facility.list.Main(vv, errors))
        }
      },
      form   => {
        for {
          locSeq      <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
          facilitySeq <- form.locationIdOpt match {
            case Some(id) =>
              for {
                locations   <- daoLocation.filterByPrefId(id)
                facilitySeq <- facilityDao.filterByLocationIds(locations.map(_.id))
              } yield facilitySeq
            case None     => facilityDao.findAll
          }
        } yield {
          val vv = SiteViewValueFacilityList(
            layout     = ViewValuePageLayout(id = request.uri),
            location   = locSeq,
            facilities = facilitySeq
          )
          Ok(views.html.site.facility.list.Main(vv, formForFacilitySearch.fill(form)))
        }
      }
    )
  }

}
