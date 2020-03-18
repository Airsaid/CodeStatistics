package com.airsaid.codestatistics.statistics

import com.airsaid.codestatistics.data.StatisticsDetail

/**
 * @author airsaid
 */
interface CodeStatisticsListener {
  fun beforeStatistics()

  fun statistics(statistics: StatisticsDetail)

  fun afterStatistics()
}