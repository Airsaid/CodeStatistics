package com.airsaid.codestatistics.data

import tornadofx.*
import javax.json.JsonObject

/**
 * @author airsaid
 */
class StringSelected(text: String = "", selected: Boolean = true) : Selected(selected), JsonModel {
  var text by property(text)
  fun textProperty() = getProperty(StringSelected::text)

  override fun updateModel(json: JsonObject) {
    with(json) {
      text = json.string(KEY_TEXT)
      selected = json.bool(KEY_SELECTED)
    }
  }

  override fun toJSON(json: JsonBuilder) {
    with(json) {
      add(KEY_TEXT, text)
      add(KEY_SELECTED, selected)
    }
  }

  companion object {
    const val KEY_TEXT = "text"
    const val KEY_SELECTED = "selected"
  }
}