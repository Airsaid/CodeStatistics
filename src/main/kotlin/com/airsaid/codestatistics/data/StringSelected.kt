package com.airsaid.codestatistics.data

import tornadofx.getProperty
import tornadofx.property

/**
 * @author airsaid
 */
class StringSelected(text: String = "", selected: Boolean = true) : Selected(selected) {
  var text by property(text)
  fun textProperty() = getProperty(StringSelected::text)
}