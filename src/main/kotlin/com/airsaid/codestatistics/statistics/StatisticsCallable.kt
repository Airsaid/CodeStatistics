package com.airsaid.codestatistics.statistics

import com.airsaid.codestatistics.data.StatisticsDetail
import java.io.File
import java.util.concurrent.Callable

/**
 * @author airsaid
 */
class StatisticsCallable(private val file: File) : Callable<StatisticsDetail?> {

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
        if (inComment) commentLine++
        else blankLine++
      } else { // 处理注释和代码行情况
        val len = line.length
        if (!inComment && line[0] == '/' && len >= 2 && line[1] == '/') {
          commentLine++
        } else if (!inComment && line[0] == '/' && len >= 2 && line[1] == '*') {
          // 判断块注释是否在同一行中
          if (!(line[len - 1] == '/' && line[len - 2] == '*')) {
            inComment = true // 块注释不在同一行中，设置为处在注释块中
          }
          commentLine++
        } else if (inComment) {
          if (len >= 2 && line[len - 1] == '/' && line[len - 2] == '*') {
            inComment = false // 注释块已经闭合
          }
          commentLine++
        } else {
          codeLine++
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