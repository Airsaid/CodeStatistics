package com.airsaid.codestatistics.view

import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.controller.StatisticsController
import com.airsaid.codestatistics.data.StatisticsDetail
import javafx.scene.control.TabPane
import tornadofx.*
import tornadofx.FX.Companion.messages

/**
 * @author airsaid
 */
class StatisticsView : View(messages[Messages.APPLICATION_NAME]) {

  private val requiredView: RequiredView by inject()

  private val statisticsController: StatisticsController by inject()

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
            field(messages[Messages.STATISTICS_TIME]) {
              textfield(statisticsController.statisticsTotal.time) { isEditable = false }
            }
          }
        }
      }

    }

  }
}