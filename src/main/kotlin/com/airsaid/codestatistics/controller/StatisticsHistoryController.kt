package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.Statistics
import com.airsaid.codestatistics.data.StatisticsHistory
import tornadofx.Controller
import tornadofx.observable
import tornadofx.toJSON
import java.time.LocalDateTime
import java.util.*

/**
 * @author airsaid
 */
class StatisticsHistoryController : Controller() {

  val historys = LinkedList<StatisticsHistory>().observable()

  init {
    loadLocalData()
  }

  private fun loadLocalData() {
    runAsync {
      val jsonArray = config.jsonArray(KEY_HISTORYS)
      jsonArray?.map {
        val history = StatisticsHistory()
        history.updateModel(it.asJsonObject())
        history
      }
    } ui {
      it?.let { historys.addAll(it) }
    }
  }

  fun addHistory(dirs: String, types: String, statistics: Statistics) {
    val history = StatisticsHistory(LocalDateTime.now(), dirs, types)
    history.fileSize = statistics.fileSize
    history.fileCount = statistics.fileCount
    history.totalLine = statistics.totalLine
    history.codeLine = statistics.codeLine
    history.commentLine = statistics.commentLine
    history.blankLine = statistics.blankLine
    history.timeConsuming = statistics.timeConsuming
    historys.add(0, history)
    save()
  }

  fun removeHistory(history: StatisticsHistory) {
    historys.remove(history)
  }

  fun save() {
    runAsync {
      val json = historys.toJSON().toString()
      if (json.isNotEmpty()) {
        config[KEY_HISTORYS] = json
        config.save()
      }
    }
  }

  companion object {
    private const val KEY_HISTORYS = "statisticsHistorys"
  }

}