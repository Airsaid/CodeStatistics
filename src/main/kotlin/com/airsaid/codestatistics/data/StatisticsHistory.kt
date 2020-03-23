package com.airsaid.codestatistics.data

import tornadofx.*
import java.time.LocalDateTime
import javax.json.JsonObject

/**
 * 历史统计信息。
 *
 * @param date 统计的时间。
 * @param dirs 统计的目录，多个用 , 分隔。
 * @param types 统计的代码类型，多个时用 , 分隔。
 *
 * @author airsaid
 */
class StatisticsHistory(date: LocalDateTime = LocalDateTime.MIN, dirs: String = "", types: String = "") : Statistics(), JsonModel {

  var date: LocalDateTime by property(date)
  fun dateProperty() = getProperty(StatisticsHistory::date)

  var dirs: String by property(dirs)
  fun dirsProperty() = getProperty(StatisticsHistory::dirs)

  var types: String by property(types)
  fun typesProperty() = getProperty(StatisticsHistory::types)

  override fun updateModel(json: JsonObject) {
    super<Statistics>.updateModel(json)
    with(json) {
      date = json.datetime(KEY_DATE) ?: date
      dirs = json.string(KEY_DIRS) ?: dirs
      types = json.string(KEY_TYPES) ?: types
    }
  }

  override fun toJSON(json: JsonBuilder) {
    super<Statistics>.toJSON(json)
    with(json) {
      add(KEY_DATE, date)
      add(KEY_DIRS, dirs)
      add(KEY_TYPES, types)
    }
  }

  companion object {
    private const val KEY_DATE = "date"
    private const val KEY_DIRS = "dirs"
    private const val KEY_TYPES = "types"
  }
}