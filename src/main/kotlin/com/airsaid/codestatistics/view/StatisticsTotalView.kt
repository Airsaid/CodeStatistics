package com.airsaid.codestatistics.view

import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.controller.StatisticsController
import tornadofx.*

/**
 * @author airsaid
 */
class StatisticsTotalView : View() {

  private val statisticsController: StatisticsController by inject()

  override val root = form {
    fieldset(messages[Messages.STATISTICS]) {
      field(messages[Messages.TOTAL_LINE]) {
        textfield(statisticsController.statisticsTotal.totalLine) { isEditable = false }
      }
      field(messages[Messages.CODE_LINE]) {
        textfield(statisticsController.statisticsTotal.codeLine) { isEditable = false }
      }
      field(messages[Messages.COMMENT_LINE]) {
        textfield(statisticsController.statisticsTotal.commentLine) { isEditable = false }
      }
      field(messages[Messages.BLANK_LINE]) {
        textfield(statisticsController.statisticsTotal.blankLine) { isEditable = false }
      }
      field(messages[Messages.FILE_SIZE]) {
        textfield(statisticsController.statisticsTotal.fileSize) { isEditable = false }
      }
      field(messages[Messages.FILE_COUNT]) {
        textfield(statisticsController.statisticsTotal.fileCount) { isEditable = false }
      }
      field(messages[Messages.STATISTICS_TIME]) {
        textfield(statisticsController.statisticsTotal.time) { isEditable = false }
      }
    }
  }

}