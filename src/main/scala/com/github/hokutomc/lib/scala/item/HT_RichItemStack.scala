package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object HT_RichItemStack {
  val NullStack = new HT_RichItemStack(null)
}

/**
 * Created by user on 2015/02/26.
 */
class HT_RichItemStack(val wrapped: ItemStack) extends AnyVal with HT_ItemStackOp[HT_RichItemStack] {

  def unwrap : ItemStack = if (isEmpty) null else stack

  def isEmpty = stackSize == 0

  def writeToNBT(nbtTagCompound: NBTTagCompound) = wrapped.writeToNBT(nbtTagCompound)

  override def toString: String = if (isEmpty) "empty stack" else stack.toString

  override def tag: NBTTagCompound = this.getOrCreateTag

  override def stack: ItemStack = wrapped
}
