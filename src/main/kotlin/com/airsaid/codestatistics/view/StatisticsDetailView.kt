package com.airsaid.codestatistics.view

import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.controller.StatisticsController
import com.airsaid.codestatistics.data.StatisticsDetail
import javafx.scene.control.TabPane
import tornadofx.*

/**
 * @author airsaid
 */
class StatisticsDetailView : View() {

  private val statisticsController: StatisticsController by inject()

  override val root = tabpane {
    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

    tab(messages[Messages.STATISTICS_DETAIL]) {
      tableview(statisticsController.statisticsDetails) {
        placeholder = label(messages[Messages.NO_DATA])
        readonlyColumn(messages[Messages.FILE_NAME], StatisticsDetail::name)
        readonlyColumn(messages[Messages.FILE_TYPE], StatisticsDetail::extension)
        readonlyColumn(messages[Messages.FILE_DIRECTORY], StatisticsDetail::directory)
        readonlyColumn(messages[Messages.FILE_SIZE], StatisticsDetail::fileSize)
        readonlyColumn(messages[Messages.TOTAL_LINE], StatisticsDetail::totalLine)
        readonlyColumn(messages[Messages.CODE_LINE], StatisticsDetail::codeLine)
        readonlyColumn(messages[Messages.COMMENT_LINE], StatisticsDetail::commentLine)
        readonlyColumn(messages[Messages.BLANK_LINE], StatisticsDetail::blankLine)
      }
    }
  }

}