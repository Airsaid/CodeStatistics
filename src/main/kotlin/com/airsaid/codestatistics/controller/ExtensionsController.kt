package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.StringSelected
import com.airsaid.codestatistics.data.viewmodel.ExtensionViewModel
import javafx.collections.FXCollections
import tornadofx.Controller
import tornadofx.toJSON

/**
 * @author airsaid
 */
class ExtensionsController : Controller() {

  val extensions = FXCollections.observableArrayList<StringSelected>()

  val selectedExtension = ExtensionViewModel()

  init {
    loadLocalData()
  }

  private fun loadLocalData() {
    runAsync {
      val jsonArray = config.jsonArray(KEY_ADD_EXTENSIONS)
      jsonArray?.map {
        val extension = StringSelected()
        extension.updateModel(it.asJsonObject())
        extension
      }
    } ui {
      it?.let { extensions.addAll(it) }
    }
  }


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

  /**
   * 将添加的后缀名保存到本地中。
   */
  fun save() {
    runAsync {
      val json = extensions.toJSON().toString()
      if (json.isNotEmpty()) {
        config[KEY_ADD_EXTENSIONS] = json
        config.save()
      }
    }
  }

  fun getExtensionSet(): HashSet<String> {
    return extensions.filter { it.selected }.map { it.text }.toHashSet()
  }

  companion object {
    const val KEY_ADD_EXTENSIONS = "addExtensions"
  }
}