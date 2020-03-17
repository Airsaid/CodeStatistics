package com.airsaid.codestatistics.view

import com.airsaid.codestatistics.app.Styles
import tornadofx.*
import tornadofx.FX.Companion.messages

/**
 * @author airsaid
 */
class MainView : View(messages["applicationName"]) {
  override val root = hbox {
    label(title) {
      addClass(Styles.heading)
    }
  }
}