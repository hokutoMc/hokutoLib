package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.nbt.HT_NBTEvidence
import com.github.hokutomc.lib.scala.HT_Predef.ItemStack
import com.github.hokutomc.lib.scala.HT_ScalaConversion._

/**
 * Created by user on 2015/04/30.
 */
class HT_ItemStackVariable[A](val key: String, val initial: A) {
  def getOrInitial(itemStackOp: ItemStack)(implicit ev: HT_NBTEvidence[A]): A = getOpt(itemStackOp) getOrElse initial

  def getOpt(itemStackOp: ItemStack)(implicit ev: HT_NBTEvidence[A]): Option[A] = itemStackOp.apply[A](key)(ev)

  def set(itemStackOp: ItemStack, value: A)(implicit ev: HT_NBTEvidence[A]) = itemStackOp.update[A](key, value)(ev)
}
