package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.StatisticsDetail
import com.airsaid.codestatistics.statistics.CodeStatistics
import com.airsaid.codestatistics.statistics.CodeStatisticsListener
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import tornadofx.Controller
import java.io.File

/**
 * @author airsaid
 */
class StatisticsController : Controller(), CodeStatisticsListener {

  val statisticsDetails = FXCollections.observableArrayList<StatisticsDetail>()

  val isRunnable = SimpleBooleanProperty(false)

  private val codeStatistics = CodeStatistics(this)

  fun startStatistics(files: List<File>, extensions: HashSet<String>) {
    if (isRunnable.get()) return

    codeStatistics.startStatistics(files, extensions)
  }

  fun stopStatistics() {
    codeStatistics.stopStatistics()
  }

  override fun beforeStatistics() {
    isRunnable.set(true)
    statisticsDetails.clear()
  }

  override fun statistics(statistics: StatisticsDetail) {
    statisticsDetails.add(statistics)
  }

  override fun afterStatistics() {
    isRunnable.set(false)
  }

}