package com.github.hokutomc.lib.scala

import net.minecraft.item.EnumDyeColor

object HT_RichEnumDyeColor {
  val map = EnumDyeColor.values.map { (a: EnumDyeColor) => (a, new HT_RichEnumDyeColor(a))}.toSeq.toMap

  def apply(color: EnumDyeColor) = map.get(color).get

  def forItemDamage(damage: Int) = EnumDyeColor.func_176766_a(damage)

  def forColoredBlockMeta(meta: Int) = EnumDyeColor.func_176764_b(meta)
}

/**
 * Created by user on 2015/03/19.
 */
class HT_RichEnumDyeColor(val dyeColor: EnumDyeColor) {
  def getBlockMeta = dyeColor.func_176765_a()

  def getMapColor = dyeColor.func_176768_e()

  def getDye = "dye" + (dyeColor.getName capitalize)

  def colorValue = dyeColor.func_176768_e().colorValue

  def colorFloat = {
    val v = colorValue
    (((v >> 4) & 0xff) / 255.0, ((v >> 2) & 0xff) / 255.0, (v & 0xff) / 255.0)
  }
}
