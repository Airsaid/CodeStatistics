package com.airsaid.codestatistics.utils

import javafx.geometry.Pos
import javafx.util.Duration
import org.controlsfx.control.Notifications
import tornadofx.FX

/**
 * @author airsaid
 */
object NotificationUtil {
  fun show(text: String, owner: Any = FX.primaryStage) {
    Notifications.create()
        .hideAfter(Duration.seconds(2.0))
        .position(Pos.BOTTOM_CENTER)
        .owner(owner)
        .text(text)
        .show()
  }
}