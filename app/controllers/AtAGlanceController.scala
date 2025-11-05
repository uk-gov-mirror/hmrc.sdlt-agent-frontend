/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import controllers.actions.{DataRequiredAction, DataRetrievalAction, IdentifierAction, StornRequiredAction}

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.govukfrontend.views.Aliases.{Card, CardTitle, Empty, HtmlContent, SummaryList, SummaryListRow, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.Key
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import views.html.AtAGlanceView


@Singleton
class AtAGlanceController @Inject()(
                                     override val messagesApi: MessagesApi,
                                     identify: IdentifierAction,
                                     //                                            getData: DataRetrievalAction,
                                     //                                            requireData: DataRequiredAction,
                                     //                                            stornRequired: StornRequiredAction,
                                     val controllerComponents: MessagesControllerComponents,
                                     view: AtAGlanceView
                                   )
  extends FrontendBaseController with I18nSupport {

  def onPageLoad(): Action[AnyContent] = identify { // (identify andThen getData andThen requireData andThen stornRequired)
    implicit request =>

      val card = SummaryList(
        classes = "govuk-summary-list",
        card = Some(Card(
          title = Some(CardTitle(Text("Returns management"), Some(2)))
        )),
        rows = Seq(
          SummaryListRow(
            Key(HtmlContent("<a href='#'>In-progress returns</a>"))
          ),
          SummaryListRow(
            Key(HtmlContent("<a href='#'>Submitted returns</a>"))
          ),
          SummaryListRow(
            Key(HtmlContent("<a href='#'>Returns due for deletion</a>"))
          ),
          SummaryListRow(
            Key(HtmlContent("<a href='#'>Start a new return</a>"))
          )
        )
      )

      Ok(view(request.storn, card))
  }
}