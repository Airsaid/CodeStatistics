package com.airsaid.codestatistics.data

import tornadofx.*
import javax.json.JsonObject

/**
 * @author airsaid
 */
open class Selected(selected: Boolean = true) : JsonModel {
  var selected: Boolean by property(selected)
  fun selectedProperty() = getProperty(Selected::selected)

  override fun updateModel(json: JsonObject) {
    with(json) {
      selected = json.bool(KEY_SELECTED) ?: selected
    }
  }

  override fun toJSON(json: JsonBuilder) {
    with(json) {
      add(KEY_SELECTED, selected)
    }
  }

  companion object {
    private const val KEY_SELECTED = "selected"
  }
}