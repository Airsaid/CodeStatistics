package com.airsaid.codestatistics.view

import com.airsaid.codestatistics.app.Styles
import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.controller.DirectorysController
import com.airsaid.codestatistics.controller.ExtensionsController
import com.airsaid.codestatistics.controller.StatisticsController
import com.airsaid.codestatistics.data.StringSelected
import com.airsaid.codestatistics.extension.isLetter
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import tornadofx.*

/**
 * @author airsaid
 */
class RequiredView : View() {

  private val directoryController: DirectorysController by inject()
  private val extensionController: ExtensionsController by inject()
  private val statisticsController: StatisticsController by inject()

  private val startDisable = SimpleBooleanProperty(true)

  override val root = vbox {
    paddingRight = 10.0
    prefHeight = 600.0
    prefWidth = 200.0

    hbox {
      hgrow = Priority.ALWAYS
      alignment = Pos.CENTER_LEFT
      vboxConstraints { marginTopBottom(10.0) }

      label(messages[Messages.CODE_DIRECTORY]) { addClass(Styles.subTitle) }

      val region = region()

      button(graphic = getAddImageView()) {
        hboxConstraints { marginRight = 10.0 }
        action { directoryController.addDirectory() }
      }

      button(graphic = getDelImageView()).action { directoryController.deleteSelectedDirectory() }

      HBox.setHgrow(region, Priority.ALWAYS) // 撑满，从而让按钮在最右侧显示
    }

    listview(directoryController.directorys) {
      vgrow = Priority.ALWAYS
      cellFormat {
        graphic = cache {
          hbox {
            checkbox(property = it.selectedProperty()).action {
              updateStartButtonState()
            }
            text(it.textProperty())
          }
        }
      }
      // 更新选中的目录数据
      selectionModel.selectedItems.addListener(ListChangeListener<StringSelected> {
        directoryController.selectedDirectory.item = selectedItem
      })
    }

    hbox {
      hgrow = Priority.ALWAYS
      alignment = Pos.CENTER_LEFT
      vboxConstraints { marginTopBottom(10.0) }

      label(messages[Messages.FILE_TYPE_TITLE]) { addClass(Styles.subTitle) }

      val region = region()

      button(graphic = getAddImageView()) {
        hboxConstraints { marginRight = 10.0 }
        action { showAddExtensionDialog() }
      }

      button(graphic = getDelImageView()).action { extensionController.deleteSelectedExtension() }

      HBox.setHgrow(region, Priority.ALWAYS) // 撑满，从而让按钮在最右侧显示
    }

    listview(extensionController.extensions) {
      vgrow = Priority.ALWAYS
      cellFormat {
        graphic = cache {
          hbox {
            checkbox(property = it.selectedProperty()).action {
              updateStartButtonState()
            }
            text(it.textProperty())
          }
        }
      }
      // 更新选中的目录数据
      selectionModel.selectedItems.addListener(ListChangeListener<StringSelected> {
        extensionController.selectedExtension.item = selectedItem
      })
    }

    button(messages[Messages.START_STATISTICS]) {
      useMaxWidth = true
      vgrow = Priority.ALWAYS
      vboxConstraints { marginTop = 10.0 }
      disableWhen { startDisable or statisticsController.isRunnable }
      action { startStatistics() }
    }

  }

  init {
    directoryController.directorys.addListener(ListChangeListener<StringSelected> {
      updateStartButtonState()
    })
    extensionController.extensions.addListener(ListChangeListener<StringSelected> {
      updateStartButtonState()
    })
    updateStartButtonState()
  }

  /**
   * 显示添加文件扩展名弹框。
   */
  fun showAddExtensionDialog() {
    val extension = SimpleStringProperty()
    dialog(messages[Messages.ADD_FILE_TYPE]) {
      field(messages[Messages.EXTENSION]) {
        textfield(extension) { filterInput { it.text.isLetter() } }
      }

      buttonbar {
        button(messages[Messages.ADD]) {
          disableWhen { extension.isEmpty }
          action {
            extensionController.addExtension(StringSelected(extension.value))
            close()
          }
        }
      }
    }
  }

  private fun startStatistics() {
    val files = directoryController.getDirectoryFiles()
    val extensions = extensionController.getExtensionSet()
    statisticsController.startStatistics(files, extensions)
  }

  // 多次使用同一个示例会导致只有最后使用的生效，因此每次使用时都创建一个新的实例
  private fun getAddImageView() = ImageView(Image(resources.stream("/images/ic_add_dir.png"), 14.0, 14.0, true, false))

  private fun getDelImageView() = ImageView(Image(resources.stream("/images/ic_del_dir.png"), 14.0, 14.0, true, false))

  private fun updateStartButtonState() {
    startDisable.set(directoryController.directorys.all { !it.selected }
        || extensionController.extensions.all { !it.selected })
  }
}