package com.airsaid.codestatistics.data

import tornadofx.getProperty
import tornadofx.property

/**
 * @author airsaid
 */
open class Selected(selected: Boolean = true) {
  var selected by property(selected)
  fun selectedProperty() = getProperty(Selected::selected)
}