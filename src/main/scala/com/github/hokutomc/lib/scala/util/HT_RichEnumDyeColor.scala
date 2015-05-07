package com.github.hokutomc.lib.scala.util

import net.minecraft.item.EnumDyeColor

object HT_RichEnumDyeColor extends scala.collection.Set[HT_RichEnumDyeColor] {
  val elems = EnumDyeColor.values().map {
    new HT_RichEnumDyeColor(_)
  } toSet

  def apply(color: EnumDyeColor) = elems.find { a => a.dyeColor == color }.get

  private def newIns(color: EnumDyeColor) = new HT_RichEnumDyeColor(color)

  def forItemDamage(damage: Int) = EnumDyeColor.func_176766_a(damage)

  def forColoredBlockMeta(meta: Int) = EnumDyeColor.func_176764_b(meta)

  //  def foreach(func: HT_RichEnumDyeColor => Unit) = {
  //    EnumDyeColor.values() foreach {func(_)}
  //  }

  override def contains(elem: HT_RichEnumDyeColor): Boolean = elems contains elem

  override def +(elem: HT_RichEnumDyeColor): collection.Set[HT_RichEnumDyeColor] = elems + elem

  override def -(elem: HT_RichEnumDyeColor): collection.Set[HT_RichEnumDyeColor] = elems - elem

  override def iterator: Iterator[HT_RichEnumDyeColor] = elems iterator
}

/**
 * Created by user on 2015/03/19.
 */
class HT_RichEnumDyeColor(val dyeColor: EnumDyeColor) {
  def getBlockMeta = dyeColor.func_176765_a()

  def getMapColor = dyeColor.func_176768_e()

  def getDye = "dye" + (dyeColor.getName capitalize)

  def colorValue = dyeColor.func_176768_e().colorValue

  def colorFloat: (Double, Double, Double) = {
    val v = colorValue
    (((v >> 4) & 0xff) / 255.0, ((v >> 2) & 0xff) / 255.0, (v & 0xff) / 255.0)
  }
}
