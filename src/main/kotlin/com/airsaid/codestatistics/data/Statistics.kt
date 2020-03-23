package com.airsaid.codestatistics.data

import tornadofx.*
import javax.json.JsonObject

/**
 * @author airsaid
 */
open class Statistics(fileSize: Long = 0L, fileCount: Long = 0L, totalLine: Long = 0L, codeLine: Long = 0L,
                      commentLine: Long = 0L, blankLine: Long = 0L, timeConsuming: Long = 0L) : JsonModel {
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

  var timeConsuming: Long by property(timeConsuming)
  fun timeConsumingProperty() = getProperty(Statistics::timeConsuming)

  override fun updateModel(json: JsonObject) {
    with(json) {
      fileSize = json.long(KEY_FILE_SIZE) ?: fileSize
      fileCount = json.long(KEY_FILE_COUNT) ?: fileCount
      totalLine = json.long(KEY_TOTAL_LINE) ?: totalLine
      codeLine = json.long(KEY_CODE_LINE) ?: codeLine
      commentLine = json.long(KEY_COMMENT_LINE) ?: commentLine
      blankLine = json.long(KEY_BLANK_LINE) ?: blankLine
      timeConsuming = json.long(KEY_TIME_CONSUMING) ?: timeConsuming
    }
  }

  override fun toJSON(json: JsonBuilder) {
    with(json) {
      add(KEY_FILE_SIZE, fileSize)
      add(KEY_FILE_COUNT, fileCount)
      add(KEY_TOTAL_LINE, totalLine)
      add(KEY_CODE_LINE, codeLine)
      add(KEY_COMMENT_LINE, commentLine)
      add(KEY_BLANK_LINE, blankLine)
      add(KEY_TIME_CONSUMING, timeConsuming)
    }
  }

  companion object {
    private const val KEY_FILE_SIZE = "fileSize"
    private const val KEY_FILE_COUNT = "fileCount"
    private const val KEY_TOTAL_LINE = "totalLine"
    private const val KEY_CODE_LINE = "codeLine"
    private const val KEY_COMMENT_LINE = "commentLine"
    private const val KEY_BLANK_LINE = "blankLine"
    private const val KEY_TIME_CONSUMING = "timeConsuming"
  }
}