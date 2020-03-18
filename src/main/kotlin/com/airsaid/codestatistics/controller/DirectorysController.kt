package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.StringSelected
import com.airsaid.codestatistics.data.viewmodel.DirectorysViewModel
import javafx.collections.FXCollections
import tornadofx.Controller
import tornadofx.chooseDirectory
import tornadofx.toJSON
import java.io.File

/**
 * @author airsaid
 */
class DirectorysController : Controller() {

  val directorys = FXCollections.observableArrayList<StringSelected>()

  val selectedDirectory = DirectorysViewModel()

  init {
    loadLocalData()
  }

  private fun loadLocalData() {
    runAsync {
      val jsonArray = config.jsonArray(KEY_ADD_DIRECTORYS)
      jsonArray?.map {
        val directory = StringSelected()
        directory.updateModel(it.asJsonObject())
        directory
      }
    } ui {
      it?.let { directorys.addAll(it) }
    }
  }

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

  /**
   * 将添加的目录保存到本地中。
   */
  fun save() {
    runAsync {
      val json = directorys.toJSON().toString()
      if (json.isNotEmpty()) {
        config[KEY_ADD_DIRECTORYS] = json
        config.save()
      }
    }
  }

  fun getDirectoryFiles(): List<File> {
    return directorys.filter { it.selected }.map { File(it.text) }
  }

  companion object {
    const val KEY_ADD_DIRECTORYS = "addFileDir"
  }
}