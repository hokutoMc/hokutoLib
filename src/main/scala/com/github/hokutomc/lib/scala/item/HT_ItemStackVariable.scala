package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.scala.nbt.HT_NBTEvidence

/**
 * Created by user on 2015/04/30.
 */
class HT_ItemStackVariable[A](val key: String, val initial: A) {
  def getOrInitial(itemStackOp: HT_ItemStackOp[_])(implicit ev: HT_NBTEvidence[A]): A = getOpt(itemStackOp) getOrElse initial

  def getOpt(itemStackOp: HT_ItemStackOp[_])(implicit ev: HT_NBTEvidence[A]): Option[A] = itemStackOp.apply[A](key)(ev)

  def set(itemStackOp: HT_ItemStackOp[_], value: A)(implicit ev: HT_NBTEvidence[A]) = itemStackOp.update[A](key, value)(ev)
}
