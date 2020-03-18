package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.StringSelected
import com.airsaid.codestatistics.data.viewmodel.DirectorysViewModel
import javafx.collections.FXCollections
import tornadofx.Controller
import tornadofx.chooseDirectory

/**
 * @author airsaid
 */
class DirectorysController : Controller() {

  val directorys = FXCollections.observableArrayList<StringSelected>()

  val selectedDirectory = DirectorysViewModel()

  /**
   * 添加要扫描的代码目录。
   */
  fun addDirectory() {
    val directory = chooseDirectory() ?: return
    directorys.add(StringSelected(directory.path))
  }

  /**
   * 删除选中的代码目录。
   */
  fun deleteSelectedDirectory() {
    val selectedDir = selectedDirectory.item ?: return
    directorys.remove(selectedDir)
  }
}