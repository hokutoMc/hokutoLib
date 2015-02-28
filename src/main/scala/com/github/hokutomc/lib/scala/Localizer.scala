package com.github.hokutomc.lib.scala


import com.github.hokutomc.lib.util.HT_I18nUtil

/**
 * Created by user on 2015/02/26.
 */
case class Localizer (string: String) extends AnyVal{
  def localize : String = HT_I18nUtil.localize(string)
  def localize (array: Any*) : String = HT_I18nUtil.localize(string, array)

  // operators
  def unary_- () : String = localize

  def apply (array: Any*) : String = localize(array)
}
