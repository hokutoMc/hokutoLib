package com.github.hokutomc.lib.scala.util

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.util.HT_ColorUtil
import net.minecraft.item.EnumDyeColor

object HT_RichEnumDyeColor {
  lazy val elems = EnumDyeColor.values().map {
    new HT_RichEnumDyeColor(_)
  } toSet

  def apply(color: EnumDyeColor) = elems.find { a => a.dyeColor == color }.get

  def forItemDamage(damage: Int) = EnumDyeColor.func_176766_a(damage)

  def forColoredBlockMeta(meta: Int) = EnumDyeColor.func_176764_b(meta)
}

/**
 * Created by user on 2015/03/19.
 */
class HT_RichEnumDyeColor(val dyeColor: EnumDyeColor) {
  val blockMeta = dyeColor.func_176765_a()

  val mapColor = dyeColor.func_176768_e()

  val dyeName = "dye" + (dyeColor.getName capitalize)

  val dyeDamage = dyeColor.getDyeColorDamage

  val colorValue = dyeColor.func_176768_e().colorValue

  val colorFloat: (Double, Double, Double) = {
    val v = colorValue
    (((v >> 4) & 0xff) / 255.0, ((v >> 2) & 0xff) / 255.0, (v & 0xff) / 255.0)
  }

  val unlocalizedName = HT_ColorUtil.getUnlocalizedColorName(dyeColor)

  def localize = -unlocalizedName
}
