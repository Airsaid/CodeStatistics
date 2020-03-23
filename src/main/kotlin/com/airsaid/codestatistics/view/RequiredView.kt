package com.airsaid.codestatistics.view

import com.airsaid.codestatistics.app.Styles
import com.airsaid.codestatistics.constant.Messages
import com.airsaid.codestatistics.controller.CodeTypeController
import com.airsaid.codestatistics.controller.DirectorysController
import com.airsaid.codestatistics.controller.StatisticsController
import com.airsaid.codestatistics.controller.StatisticsHistoryController
import com.airsaid.codestatistics.data.CodeDirectory
import com.airsaid.codestatistics.data.CodeType
import com.airsaid.codestatistics.extension.isLetter
import com.airsaid.codestatistics.utils.NotificationUtil
import javafx.beans.property.SimpleBooleanProperty
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
  private val typeController: CodeTypeController by inject()
  private val statisticsController: StatisticsController by inject()
  private val historyController: StatisticsHistoryController by inject()

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
            text(it.directoryProperty())
          }
        }
      }

      selectionModel.selectedItems.addListener(ListChangeListener<CodeDirectory> {
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
        action { showCodeTypeDialog(CodeType()) }
      }

      button(graphic = getDelImageView()).action { typeController.deleteSelectedType() }

      HBox.setHgrow(region, Priority.ALWAYS) // 撑满，从而让按钮在最右侧显示
    }

    listview(typeController.types) {
      vgrow = Priority.ALWAYS
      cellFormat {
        graphic = cache {
          hbox {
            checkbox(property = it.selectedProperty()).action {
              updateStartButtonState()
            }
            text(it.extensionProperty())
          }
        }
      }

      selectionModel.selectedItems.addListener(ListChangeListener<CodeType> {
        typeController.selectedType.item = selectedItem
      })
    }

    button(messages[Messages.START_STATISTICS]) {
      useMaxWidth = true
      vgrow = Priority.ALWAYS
      vboxConstraints { marginTop = 10.0 }
      disableWhen { startDisable or statisticsController.isRunnable }
      action { startStatistics() }
    }


    button(messages[Messages.SAVE_RESULTS]) {
      useMaxWidth = true
      vgrow = Priority.ALWAYS
      vboxConstraints { marginTop = 10.0 }
      disableWhen { statisticsController.statisticsTotal.empty or statisticsController.isRunnable }
      action {
        val dirs = directoryController.getSelectedDirectorys()
        val types = typeController.getSelectedExtensions()
        historyController.addHistory(dirs, types, statisticsController.statisticsTotal.item)
        NotificationUtil.show(messages[Messages.SAVE_SUCCESS])
      }
    }
  }

  init {
    directoryController.directorys.addListener(ListChangeListener<CodeDirectory> {
      updateStartButtonState()
    })
    typeController.types.addListener(ListChangeListener<CodeType> {
      updateStartButtonState()
    })
    updateStartButtonState()
  }

  fun showCodeTypeDialog(codeType: CodeType) {
    dialog(messages[Messages.ADD_FILE_TYPE]) {
      field("* ${messages[Messages.EXTENSION]}") {
        textfield(codeType.extensionProperty()) { filterInput { it.text.isLetter() } }
      }
      field(messages[Messages.SINGLE_COMMENTS]) {
        textfield(codeType.singleCommentsProperty())
      }
      field(messages[Messages.MULTI_COMMENTS_START]) {
        textfield(codeType.multiCommentsStartProperty())
      }
      field(messages[Messages.MULTI_COMMENTS_END]) {
        textfield(codeType.multiCommentsEndProperty())
      }

      buttonbar {
        button(messages[Messages.ADD]) {
          disableWhen { codeType.extensionProperty().isBlank() }
          action {
            typeController.addCodeType(codeType)
            close()
          }
        }
      }
    }
  }

  private fun startStatistics() {
    val files = directoryController.getDirectoryFiles()
    val types = typeController.getTypeMap()
    statisticsController.startStatistics(files, types)
  }

  // 多次使用同一个示例会导致只有最后使用的生效，因此每次使用时都创建一个新的实例
  private fun getAddImageView() = ImageView(Image(resources.stream("/images/ic_add_dir.png"), 14.0, 14.0, true, false))

  private fun getDelImageView() = ImageView(Image(resources.stream("/images/ic_del_dir.png"), 14.0, 14.0, true, false))

  private fun updateStartButtonState() {
    startDisable.set(directoryController.directorys.all { !it.selected }
        || typeController.types.all { !it.selected })
  }
}