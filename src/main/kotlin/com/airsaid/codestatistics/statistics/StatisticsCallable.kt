package com.airsaid.codestatistics.statistics

import com.airsaid.codestatistics.data.CodeType
import com.airsaid.codestatistics.data.StatisticsDetail
import java.io.File
import java.util.concurrent.Callable

/**
 * @author airsaid
 */
class StatisticsCallable(private val file: File, private val type: CodeType) : Callable<StatisticsDetail?> {

  private val startTime = System.nanoTime()

  override fun call(): StatisticsDetail? {
    val name = file.nameWithoutExtension
    val extension = ".${file.extension}"
    val directory = file.parent
    val fileSize = file.length()
    var totalLine = 0L
    var codeLine = 0L
    var commentLine = 0L
    var blankLine = 0L

    var inComment = false // 是否处在注释代码块中
    file.forEachLine { lineStr ->
      val line = lineStr.trim()
      // 处理空行情况
      if (line.isEmpty()) {
        if (inComment) {
          commentLine++
        } else {
          blankLine++
        }
      } else {
        // 处理注释和代码行情况
        val single = type.singleComments
        val multiStart = type.multiCommentsStart
        val multiEnd = type.multiCommentsEnd
        if (!inComment) {
          if (single.isNotEmpty() && line.startsWith(single)) { // 单行注释
            commentLine++
          } else if (multiStart.isNotEmpty() && line.startsWith(multiStart)) { // 多行注释开始
            // 判断多行注释是否不在同一行中
            if (multiEnd.isEmpty() || !line.endsWith(multiEnd)) {
              inComment = true // 多行注释不在同一行中，设置为处在多行注释块中
            }
            commentLine++
          } else {
            codeLine++
          }
        } else {
          if (multiEnd.isNotEmpty() && line.endsWith(multiEnd)) {
            inComment = false // 多行注释结束
          }
          commentLine++
        }
      }
      totalLine++
    }

    val statistics = StatisticsDetail()
    statistics.name = name
    statistics.extension = extension
    statistics.directory = directory
    statistics.fileSize = fileSize
    statistics.totalLine = totalLine
    statistics.codeLine = codeLine
    statistics.commentLine = commentLine
    statistics.blankLine = blankLine
    statistics.time = System.nanoTime() - startTime
    return statistics
  }
}