package com.airsaid.codestatistics.data.viewmodel

import com.airsaid.codestatistics.data.Statistics
import javafx.beans.property.SimpleLongProperty
import tornadofx.ItemViewModel

/**
 * @author airsaid
 */
class StatisticsViewModel : ItemViewModel<Statistics>() {
  val fileSize = bind { SimpleLongProperty(item?.fileSize ?: 0L) }
  val fileCount = bind { SimpleLongProperty(item?.fileCount ?: 0L) }
  val totalLine = bind { SimpleLongProperty(item?.totalLine ?: 0L) }
  val codeLine = bind { SimpleLongProperty(item?.codeLine ?: 0L) }
  val commentLine = bind { SimpleLongProperty(item?.commentLine ?: 0L) }
  val blankLine = bind { SimpleLongProperty(item?.blankLine ?: 0L) }
  val timeConsuming = bind { SimpleLongProperty(item?.timeConsuming ?: 0L) }
}