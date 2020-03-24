package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.data.StatisticsDetail
import com.airsaid.codestatistics.utils.NotificationUtil
import com.github.liaochong.myexcel.core.CsvBuilder
import com.github.liaochong.myexcel.core.DefaultExcelBuilder
import com.github.liaochong.myexcel.core.annotation.ExcelColumn
import com.github.liaochong.myexcel.core.annotation.ExcelModel
import com.github.liaochong.myexcel.core.annotation.IgnoreColumn
import com.github.liaochong.myexcel.exception.CsvBuildException
import com.github.liaochong.myexcel.utils.FileExportUtil
import tornadofx.Controller
import tornadofx.chooseDirectory
import tornadofx.get
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/**
 * @author airsaid
 */
class ExportController : Controller() {

  private val format by lazy {
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG)
  }

  fun exportExcel(list: List<StatisticsDetail>) {
    val dir = chooseDirectory() ?: return
    runAsync {
      val data = list.map { ExportInfo(it) }
      val workbook = DefaultExcelBuilder.of(ExportInfo::class.java)
          .build(data)
      val name = "Statistics-${LocalDateTime.now().format(format)}.xlsx"
      try {
        FileExportUtil.export(workbook, File(dir, name))
        true
      } catch (e: IOException) {
        e.printStackTrace()
        false
      }
    } ui { success ->
      if (success) {
        NotificationUtil.show(messages[Messages.EXPORT_COMPLETED])
      } else {
        NotificationUtil.show(messages[Messages.EXPORT_FAILED])
      }
    }
  }

  fun exportCsv(list: List<StatisticsDetail>) {
    val dir = chooseDirectory() ?: return
    runAsync {
      val data = list.map { ExportInfo(it) }
      try {
        val name = "Statistics-${LocalDateTime.now().format(format)}.csv"
        val csv = CsvBuilder.of(ExportInfo::class.java).build(data)
        csv.write(Paths.get(File(dir, name).path))
        true
      } catch (e: CsvBuildException) {
        e.printStackTrace()
        false
      }
    } ui { success ->
      if (success) {
        NotificationUtil.show(messages[Messages.EXPORT_COMPLETED])
      } else {
        NotificationUtil.show(messages[Messages.EXPORT_FAILED])
      }
    }
  }

}

@ExcelModel(sheetName = "Statistics")
class ExportInfo(@IgnoreColumn val statistics: StatisticsDetail) {
  @ExcelColumn(order = 0, title = "File Name")
  val name = statistics.name

  @ExcelColumn(order = 1, title = "File Type")
  val extension = statistics.extension

  @ExcelColumn(order = 2, title = "File Directory")
  val directory = statistics.directory

  @ExcelColumn(order = 3, title = "File Size (bytes)")
  val fileSize = statistics.fileSize

  @ExcelColumn(order = 4, title = "Total Line")
  val totalLine = statistics.totalLine

  @ExcelColumn(order = 5, title = "Code Line")
  val codeLine = statistics.codeLine

  @ExcelColumn(order = 6, title = "Comment Line")
  val commentLine = statistics.commentLine

  @ExcelColumn(order = 7, title = "Blank Line")
  val blankLine = statistics.blankLine
}