package com.airsaid.codestatistics.app

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.px

/**
 * @author airsaid
 */
class Styles : Stylesheet() {
  companion object {
    val subTitle by cssclass()
  }

  init {
    label and subTitle {
      fontSize = 16.px
      fontWeight = FontWeight.BOLD
    }
  }
}