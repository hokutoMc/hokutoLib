package com.github.hokutomc.lib.scala


import com.github.hokutomc.lib.util.HT_I18nUtil

import scala.math.ScalaNumber

/**
 * Created by user on 2015/02/26.
 */
class HT_RichString(val string: String) extends AnyVal {

  def localize: String = HT_I18nUtil.localize(string)

  def localizeF(array: Any*): String = HT_I18nUtil.localize(string, array map unwrapArg: _*)


  private def unwrapArg(arg: Any): AnyRef = arg match {
    case x: ScalaNumber => x.underlying
    case x => x.asInstanceOf[AnyRef]
  }

  // operators
  def unary_-(): String = localize

  def apply(array: Any*): String = localizeF(array)

  def +:+(other: String): String = string + ":" + other
}
