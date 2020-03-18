package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.StringSelected
import com.airsaid.codestatistics.data.viewmodel.ExtensionViewModel
import javafx.collections.FXCollections
import tornadofx.Controller

/**
 * @author airsaid
 */
class ExtensionsController : Controller() {

  val extensions = FXCollections.observableArrayList<StringSelected>()

  val selectedExtension = ExtensionViewModel()

  /**
   * 添加文件扩展名。
   */
  fun addExtension(extension: StringSelected) {
    extensions.add(extension)
  }

  /**
   * 删除选中的文件扩展名。
   */
  fun deleteSelectedExtension() {
    val selectedExtension = selectedExtension.item ?: return
    extensions.remove(selectedExtension)
  }
}