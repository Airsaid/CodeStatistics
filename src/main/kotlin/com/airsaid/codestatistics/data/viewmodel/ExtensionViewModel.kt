package com.airsaid.codestatistics.data.viewmodel

import com.airsaid.codestatistics.data.StringSelected
import tornadofx.ItemViewModel

/**
 * @author airsaid
 */
class ExtensionViewModel : ItemViewModel<StringSelected>() {
  val extension = bind { item?.textProperty() }
  val selected = bind { item?.selectedProperty() }
}