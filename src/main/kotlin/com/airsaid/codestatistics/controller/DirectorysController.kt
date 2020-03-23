package com.airsaid.codestatistics.controller

import com.airsaid.codestatistics.data.CodeDirectory
import tornadofx.Controller
import tornadofx.chooseDirectory
import tornadofx.observable
import tornadofx.toJSON
import java.io.File
import java.util.*

/**
 * @author airsaid
 */
class DirectorysController : Controller() {

  val directorys = LinkedList<CodeDirectory>().observable()

  init {
    loadLocalData()
  }

  private fun loadLocalData() {
    runAsync {
      val jsonArray = config.jsonArray(KEY_ADD_DIRECTORYS)
      jsonArray?.map {
        val directory = CodeDirectory()
        directory.updateModel(it.asJsonObject())
        directory
      }
    } ui {
      it?.let { directorys.addAll(it) }
    }
  }

  fun addDirectory() {
    val directory = chooseDirectory() ?: return
    directorys.add(CodeDirectory(directory.path))
  }

  fun deleteDirectory(dir: CodeDirectory) {
    directorys.remove(dir)
  }

  fun save() {
    runAsync {
      val json = directorys.toJSON().toString()
      if (json.isNotEmpty()) {
        config[KEY_ADD_DIRECTORYS] = json
        config.save()
      }
    }
  }

  fun getDirectoryFiles(): List<File> {
    return directorys.filter { it.selected }.map { File(it.directory) }
  }

  fun getSelectedDirectorys(): String {
    return directorys.filter { it.selected }.joinToString { it.directory }
  }

  companion object {
    private const val KEY_ADD_DIRECTORYS = "addFileDir"
  }
}