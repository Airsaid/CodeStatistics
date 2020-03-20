package com.airsaid.codestatistics.data

import tornadofx.getProperty
import tornadofx.property

/**
 * @author airsaid
 */
open class Statistics(fileSize: Long = 0L, fileCount: Long = 0L, totalLine: Long = 0L, codeLine: Long = 0L,
                 commentLine: Long = 0L, blankLine: Long = 0L, time: Long = 0L) {
  var fileSize: Long by property(fileSize)
  fun fileSizeProperty() = getProperty(Statistics::fileSize)

  var fileCount: Long by property(fileCount)
  fun fileCountProperty() = getProperty(Statistics::fileCount)

  var totalLine: Long by property(totalLine)
  fun totalLineProperty() = getProperty(Statistics::totalLine)

  var codeLine: Long by property(codeLine)
  fun codeLineProperty() = getProperty(Statistics::codeLine)

  var commentLine: Long by property(commentLine)
  fun commentLineProperty() = getProperty(Statistics::commentLine)

  var blankLine: Long by property(blankLine)
  fun blankLineProperty() = getProperty(Statistics::blankLine)

  var time: Long by property(time)
  fun timeProperty() = getProperty(Statistics::time)
}