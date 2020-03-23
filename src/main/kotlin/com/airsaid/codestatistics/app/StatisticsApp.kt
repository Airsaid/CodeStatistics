package com.airsaid.codestatistics.app

import com.airsaid.codestatistics.controller.CodeTypeController
import com.airsaid.codestatistics.controller.DirectorysController
import com.airsaid.codestatistics.controller.StatisticsController
import com.airsaid.codestatistics.controller.StatisticsHistoryController
import com.airsaid.codestatistics.view.StatisticsView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import tornadofx.App
import tornadofx.FX

/**
 * @author airsaid
 */
class StatisticsApp : App(StatisticsView::class, Styles::class) {

  private val directoryController: DirectorysController by inject()
  private val codeTypeController: CodeTypeController by inject()
  private val statisticsController: StatisticsController by inject()
  private val historyController: StatisticsHistoryController by inject()

  init {
    // 设置布局调试器的快捷键为 controller + F12
    FX.layoutDebuggerShortcut = KeyCodeCombination(KeyCode.F12, KeyCombination.CONTROL_DOWN)
  }

  override fun start(stage: Stage) {
    super.start(stage)
    stage.setOnCloseRequest {
      statisticsController.stopStatistics()
      directoryController.save()
      codeTypeController.save()
      historyController.save()
    }
  }
}
