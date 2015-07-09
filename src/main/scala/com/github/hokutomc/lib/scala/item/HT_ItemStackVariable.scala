package com.github.hokutomc.lib.scala
package item

import com.github.hokutomc.lib.nbt.HT_NBTEvidence
import com.github.hokutomc.lib.scala.HT_ScalaConversion._

/**
 * Created by user on 2015/04/30.
 */
class HT_ItemStackVariable[A](val key: String, val initial: A) {
  def getOrInitial(stack: ItemStack)(implicit ev: HT_NBTEvidence[A]): A = getOpt(stack) getOrElse initial

  def getOpt(stack: ItemStack)(implicit ev: HT_NBTEvidence[A]): Option[A] = stack.apply[A](key)(ev)

  def set(stack: ItemStack, value: A)(implicit ev: HT_NBTEvidence[A]) = stack.update[A](key, value)(ev)
}
