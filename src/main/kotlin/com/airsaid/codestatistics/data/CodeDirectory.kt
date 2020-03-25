package com.airsaid.codestatistics.data

import tornadofx.*
import java.util.*
import javax.json.JsonObject

/**
 * @author airsaid
 */
class CodeDirectory(directory: String = "", selected: Boolean = true) : Selected(selected), JsonModel {
  var directory: String by property(directory)
  fun directoryProperty() = getProperty(CodeDirectory::directory)

  override fun updateModel(json: JsonObject) {
    super<Selected>.updateModel(json)
    with(json) {
      directory = json.string(KEY_DIRECTORY) ?: directory
    }
  }

  override fun toJSON(json: JsonBuilder) {
    super<Selected>.toJSON(json)
    with(json) {
      add(KEY_DIRECTORY, directory)
    }
  }

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is CodeDirectory) return false
    return other.directory == this.directory
  }

  override fun hashCode(): Int {
    return Objects.hash(directory)
  }

  companion object {
    private const val KEY_DIRECTORY = "directory"
  }
}