package com.airsaid.codestatistics.extension

/**
 * [String] 字符串的扩展方法。
 *
 * @author airsaid
 */

private val letterRegex by lazy { Regex("[a-zA-Z]+") }

/**
 * 返回字符串是否由纯字母组成。
 */
fun String.isLetter(): Boolean {
  return letterRegex.matches(this)
}