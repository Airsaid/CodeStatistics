package com.airsaid.codestatistics.data.viewmodel

import com.airsaid.codestatistics.data.StringSelected
import tornadofx.ItemViewModel

/**
 * @author airsaid
 */
class DirectorysViewModel : ItemViewModel<StringSelected>() {
  val directory = bind { item?.textProperty() }
  val selected = bind { item?.selectedProperty() }
}