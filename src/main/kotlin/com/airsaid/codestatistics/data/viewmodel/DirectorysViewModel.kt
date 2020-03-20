package com.airsaid.codestatistics.data.viewmodel

import com.airsaid.codestatistics.data.CodeDirectory
import tornadofx.ItemViewModel

/**
 * @author airsaid
 */
class DirectorysViewModel : ItemViewModel<CodeDirectory>() {
  val directory = bind { item?.directoryProperty() }
  val selected = bind { item?.selectedProperty() }
}