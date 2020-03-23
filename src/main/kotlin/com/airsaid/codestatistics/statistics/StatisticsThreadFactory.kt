package com.airsaid.codestatistics.statistics

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author airsaid
 */
class StatisticsThreadFactory : ThreadFactory {

  private val threadNumber = AtomicInteger(1)

  private var group: ThreadGroup
  private var namePrefix: String

  init {
    val s = System.getSecurityManager()
    group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
    namePrefix = "statistics-pool-${poolNumber.getAndIncrement()}-thread-"
  }

  override fun newThread(r: Runnable): Thread {
    val t = Thread(group, r, "$namePrefix$threadNumber", 0)
    if (t.isDaemon) {
      t.isDaemon = false
    }
    if (t.priority != Thread.NORM_PRIORITY) {
      t.priority = Thread.NORM_PRIORITY
    }
    return t
  }

  companion object {
    private val poolNumber = AtomicInteger(1)
  }
}