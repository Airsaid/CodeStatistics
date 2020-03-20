package com.airsaid.codestatistics.data

import tornadofx.getProperty
import tornadofx.property

/**
 * @author airsaid
 */
class StatisticsDetail(name: String = "", extension: String = "", directory: String = "") : Statistics() {
  var name: String by property(name)
  fun nameProperty() = getProperty(StatisticsDetail::name)

  var extension: String by property(extension)
  fun extensionProperty() = getProperty(StatisticsDetail::extension)

  var directory: String by property(directory)
  fun directoryProperty() = getProperty(StatisticsDetail::directory)
}