package com.airsaid.codestatistics.data.viewmodel

import com.airsaid.codestatistics.data.CodeType
import tornadofx.ItemViewModel

/**
 * @author airsaid
 */
class CodeTypeViewModel : ItemViewModel<CodeType>() {
  val extensions = bind { item?.extensionProperty() }
  val singleComments = bind { item?.singleCommentsProperty() }
  val multiCommentsStart = bind { item?.multiCommentsStartProperty() }
  val multiCommentsEnd = bind { item?.multiCommentsEndProperty() }
  val selected = bind { item?.selectedProperty() }
}