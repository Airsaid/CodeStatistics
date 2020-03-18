package com.airsaid.codestatistics.data

import tornadofx.getProperty
import tornadofx.property

/**
 * @author airsaid
 */
class StatisticsDetail(name: String = "", extension: String = "", directory: String = "") : Statistics() {
  var name by property(name)
  fun nameProperty() = getProperty(StatisticsDetail::name)

  var extension by property(extension)
  fun extensionProperty() = getProperty(StatisticsDetail::extension)

  var directory by property(directory)
  fun directoryProperty() = getProperty(StatisticsDetail::directory)
}