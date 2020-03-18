package com.airsaid.codestatistics.data

import tornadofx.getProperty
import tornadofx.property

/**
 * @author airsaid
 */
open class Statistics(fileSize: Long = 0L, totalLine: Long = 0L, codeLine: Long = 0L,
                 commentLine: Long = 0L, blankLine: Long = 0L, time: Long = 0L) {
  var fileSize by property(fileSize)
  fun fileSizeProperty() = getProperty(Statistics::fileSize)

  var totalLine by property(totalLine)
  fun totalLineProperty() = getProperty(Statistics::totalLine)

  var codeLine by property(codeLine)
  fun codeLineProperty() = getProperty(Statistics::codeLine)

  var commentLine by property(commentLine)
  fun commentLineProperty() = getProperty(Statistics::commentLine)

  var blankLine by property(blankLine)
  fun blankLineProperty() = getProperty(Statistics::blankLine)

  var time by property(time)
  fun timeProperty() = getProperty(Statistics::time)
}