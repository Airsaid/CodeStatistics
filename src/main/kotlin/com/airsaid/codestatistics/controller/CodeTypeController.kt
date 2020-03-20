package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.CodeType
import com.airsaid.codestatistics.data.viewmodel.CodeTypeViewModel
import javafx.collections.FXCollections
import tornadofx.Controller
import tornadofx.toJSON

/**
 * @author airsaid
 */
class CodeTypeController : Controller() {

  val types = FXCollections.observableArrayList<CodeType>()

  val selectedType = CodeTypeViewModel()

  init {
    loadLocalData()
  }

  private fun loadLocalData() {
    runAsync {
      val jsonArray = config.jsonArray(KEY_ADD_CODE_TYPES)
      jsonArray?.map {
        val type = CodeType()
        type.updateModel(it.asJsonObject())
        type
      }
    } ui {
      it?.let { types.addAll(it) }
    }
  }

  fun addCodeType(type: CodeType) {
    types.add(type)
  }

  fun deleteSelectedType() {
    val selectedType = selectedType.item ?: return
    types.remove(selectedType)
  }

  fun save() {
    runAsync {
      val json = types.toJSON().toString()
      if (json.isNotEmpty()) {
        config[KEY_ADD_CODE_TYPES] = json
        config.save()
      }
    }
  }

  fun getTypeMap(): Map<String, CodeType> {
    return types.filter { it.selected }.associate { it.extension to it }
  }

  companion object {
    const val KEY_ADD_CODE_TYPES = "addCodeTypes"
  }
}