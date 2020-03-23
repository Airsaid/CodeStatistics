package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.CodeType
import tornadofx.Controller
import tornadofx.observable
import tornadofx.toJSON
import java.util.*

/**
 * @author airsaid
 */
class CodeTypeController : Controller() {

  val types = LinkedList<CodeType>().observable()

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

  fun deleteCodeType(type: CodeType) {
    types.remove(type)
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

  fun getSelectedExtensions(): String {
    return types.filter { it.selected }.joinToString { it.extension }
  }

  companion object {
    private const val KEY_ADD_CODE_TYPES = "addCodeTypes"
  }
}