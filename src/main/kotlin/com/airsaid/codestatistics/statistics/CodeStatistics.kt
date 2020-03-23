package com.airsaid.codestatistics.statistics

import com.airsaid.codestatistics.data.CodeType
import com.airsaid.codestatistics.data.StatisticsDetail
import javafx.application.Platform
import java.io.File
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author airsaid
 */
class CodeStatistics(private var listener: CodeStatisticsListener? = null) {

  private lateinit var workThreadPool: ThreadPoolExecutor
  private lateinit var executorService: ExecutorCompletionService<StatisticsDetail>

  private lateinit var submitThread: Thread
  private lateinit var dispatchThread: Thread

  private lateinit var types: Map<String, CodeType>

  fun startStatistics(dirs: List<File>, types: Map<String, CodeType>) {
    listener?.beforeStatistics()

    this.types = types
    this.workThreadPool = makeThreadPool()
    this.executorService = ExecutorCompletionService(workThreadPool)

    // 由于任务的处理时间远比生产任务的耗时多，因此只使用一个线程生产任务
    this.submitThread = StatisticsThreadFactory().newThread {
      dirs.forEach { recurScanFile(it) }
      // 等待任务都执行完毕
      workThreadPool.shutdown()
      workThreadPool.awaitTermination(10, TimeUnit.MINUTES)
      dispatchThread.interrupt()
    }

    // 开启一个线程处理已经执行完成的任务
    this.dispatchThread = StatisticsThreadFactory().newThread {
      while (!dispatchThread.isInterrupted) {
        try {
          val statistics = executorService.take().get()
          Platform.runLater { listener?.statistics(statistics) }
        } catch (e: InterruptedException) {
          Thread.currentThread().interrupt()
          Platform.runLater { listener?.afterStatistics() }
        }
      }
    }

    submitThread.start()
    dispatchThread.start()
  }

  fun stopStatistics() {
    if (!submitThread.isInterrupted) {
      submitThread.interrupt()
    }
    if (!dispatchThread.isInterrupted) {
      dispatchThread.interrupt()
    }
    workThreadPool.shutdownNow()
  }

  fun setCodeStatisticsListener(listener: CodeStatisticsListener) {
    this.listener = listener
  }

  private fun makeThreadPool(): ThreadPoolExecutor {
    return ThreadPoolExecutor(corePoolSize, maxPoolSize,
        60, TimeUnit.SECONDS, LinkedBlockingQueue(workQueueSize),
        StatisticsThreadFactory(), ThreadPoolExecutor.CallerRunsPolicy())
  }

  private fun recurScanFile(file: File) {
    if (submitThread.isInterrupted || !file.exists()) return

    if (file.isDirectory) { // 是目录则递归扫描
      val listFile = file.listFiles()
      if (listFile != null && listFile.isNotEmpty()) {
        listFile.forEach {
          if (!submitThread.isInterrupted) recurScanFile(it)
        }
      }
    } else if (file.isFile && types.contains(file.extension)) {
      // 符合文件类型，将任务提交给线程池执行
      executorService.submit(StatisticsCallable(file, types[file.extension] as CodeType))
    }
  }

  companion object {
    val corePoolSize = Runtime.getRuntime().availableProcessors()
    val maxPoolSize = corePoolSize * (1 + (8 / 2))
    const val workQueueSize = 100
  }

}