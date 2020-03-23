package com.airsaid.codestatistics.data

import tornadofx.*
import java.util.*
import javax.json.JsonObject

/**
 * 源代码类型。
 *
 * @param extension 代码扩展名。
 * @param singleComments 单行注释。
 * @param multiCommentsStart 多行注释开始。
 * @param multiCommentsEnd 多行注释结束。
 *
 * @author airsaid
 */
class CodeType(extension: String = "", singleComments: String = "",
               multiCommentsStart: String = "", multiCommentsEnd: String = "",
               selected: Boolean = true) : Selected(selected), JsonModel {

  var extension: String by property(extension)
  fun extensionProperty() = getProperty(CodeType::extension)

  var singleComments: String by property(singleComments)
  fun singleCommentsProperty() = getProperty(CodeType::singleComments)

  var multiCommentsStart: String by property(multiCommentsStart)
  fun multiCommentsStartProperty() = getProperty(CodeType::multiCommentsStart)

  var multiCommentsEnd: String by property(multiCommentsEnd)
  fun multiCommentsEndProperty() = getProperty(CodeType::multiCommentsEnd)

  override fun updateModel(json: JsonObject) {
    super<Selected>.updateModel(json)
    with(json) {
      extension = json.string(KEY_EXTENSION) ?: extension
      singleComments = json.string(KEY_SINGLE_COMMENTS) ?: singleComments
      multiCommentsStart = json.string(KEY_MULTI_COMMENTS_START) ?: multiCommentsStart
      multiCommentsEnd = json.string(KEY_MULTI_COMMENTS_END) ?: multiCommentsEnd
    }
  }

  override fun toJSON(json: JsonBuilder) {
    super<Selected>.toJSON(json)
    with(json) {
      add(KEY_EXTENSION, extension)
      add(KEY_SINGLE_COMMENTS, singleComments)
      add(KEY_MULTI_COMMENTS_START, multiCommentsStart)
      add(KEY_MULTI_COMMENTS_END, multiCommentsEnd)
    }
  }

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is CodeType) return false
    return other.extension == this.extension
  }

  override fun hashCode(): Int {
    return Objects.hash(extension)
  }

  companion object {
    private const val KEY_EXTENSION = "extension"
    private const val KEY_SINGLE_COMMENTS = "singleComments"
    private const val KEY_MULTI_COMMENTS_START = "multiCommentsStart"
    private const val KEY_MULTI_COMMENTS_END = "multiCommentsEnd"
  }
}