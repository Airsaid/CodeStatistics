package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.data.CodeType
import com.airsaid.codestatistics.data.Statistics
import com.airsaid.codestatistics.data.StatisticsDetail
import com.airsaid.codestatistics.data.viewmodel.StatisticsViewModel
import com.airsaid.codestatistics.statistics.CodeStatistics
import com.airsaid.codestatistics.statistics.CodeStatisticsListener
import com.airsaid.codestatistics.utils.NotificationUtil
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.Controller
import tornadofx.get
import tornadofx.observable
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author airsaid
 */
class StatisticsController : Controller(), CodeStatisticsListener {

  val statisticsDetails = LinkedList<StatisticsDetail>().observable()
  val isRunnable = SimpleBooleanProperty(false)
  val statisticsTotal = StatisticsViewModel()

  private lateinit var statisticsTemp: Statistics

  private val codeStatistics = CodeStatistics(this)
  private var startTime: Long = 0

  fun startStatistics(files: List<File>, types: Map<String, CodeType>) {
    if (isRunnable.get()) return

    statisticsTemp = Statistics()
    codeStatistics.startStatistics(files, types)
  }

  fun stopStatistics() {
    codeStatistics.stopStatistics()
  }

  override fun beforeStatistics() {
    isRunnable.set(true)
    statisticsDetails.clear()
    startTime = System.nanoTime()
  }

  override fun statistics(statistics: StatisticsDetail) {
    statisticsDetails.add(0, statistics)
    statisticsTemp.fileCount += 1
    statisticsTemp.fileSize += statistics.fileSize
    statisticsTemp.totalLine += statistics.totalLine
    statisticsTemp.codeLine += statistics.codeLine
    statisticsTemp.commentLine += statistics.commentLine
    statisticsTemp.blankLine += statistics.blankLine
  }

  override fun afterStatistics() {
    isRunnable.set(false)
    statisticsTotal.item = statisticsTemp
    statisticsTotal.timeConsuming.value = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime)
    NotificationUtil.show(messages[Messages.STATISTICS_COMPLETED])
  }

}