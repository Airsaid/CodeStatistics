package com.airsaid.codestatistics.app

import com.airsaid.codestatistics.view.MainView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import tornadofx.App
import tornadofx.FX

/**
 * @author airsaid
 */
class MainApp : App(MainView::class, Styles::class) {
  init {
    // 设置布局调试器的快捷键为 controller + F12
    FX.layoutDebuggerShortcut = KeyCodeCombination(KeyCode.F12, KeyCombination.CONTROL_DOWN)
  }
}
