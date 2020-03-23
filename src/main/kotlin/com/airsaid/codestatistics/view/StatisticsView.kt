package com.airsaid.codestatistics.view

import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.controller.StatisticsController
import com.airsaid.codestatistics.controller.StatisticsHistoryController
import com.airsaid.codestatistics.data.StatisticsDetail
import com.airsaid.codestatistics.data.StatisticsHistory
import javafx.scene.control.TabPane
import tornadofx.*
import tornadofx.FX.Companion.messages
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * @author airsaid
 */
class StatisticsView : View(messages[Messages.APPLICATION_NAME]) {

  private val requiredView: RequiredView by inject()

  private val statisticsController: StatisticsController by inject()
  private val historysController: StatisticsHistoryController by inject()

  private val dateFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

  override val root = borderpane {
    paddingAll = 10

    left = requiredView.root

    center = tabpane {
      tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

      tab(messages[Messages.DETAILS]) {
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

      tab(messages[Messages.TOTAL]) {
        form {
          fieldset {
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
            field(messages[Messages.STATISTICS_TIME_CONSUMING]) {
              textfield(statisticsController.statisticsTotal.timeConsuming) { isEditable = false }
            }
          }
        }
      }

      tab(messages[Messages.HISTORY]) {
        tableview(historysController.historys) {
          placeholder = label(messages[Messages.NO_DATA])

          readonlyColumn(messages[Messages.DATE], StatisticsHistory::date) {
            cellFormat { text = it.format(dateFormat) }
          }
          readonlyColumn(messages[Messages.FILE_DIRECTORY], StatisticsHistory::dirs)
          readonlyColumn(messages[Messages.FILE_TYPE], StatisticsHistory::types)
          readonlyColumn(messages[Messages.FILE_SIZE], StatisticsHistory::fileSize)
          readonlyColumn(messages[Messages.TOTAL_LINE], StatisticsHistory::totalLine)
          readonlyColumn(messages[Messages.CODE_LINE], StatisticsHistory::codeLine)
          readonlyColumn(messages[Messages.COMMENT_LINE], StatisticsHistory::commentLine)
          readonlyColumn(messages[Messages.BLANK_LINE], StatisticsHistory::blankLine)

          contextmenu {
            item(messages[Messages.DELETE]).action {
              selectedItem?.apply {
                historysController.removeHistory(this)
              }
            }
          }
        }
      }

    }

  }
}