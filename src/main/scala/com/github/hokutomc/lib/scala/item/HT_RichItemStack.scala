package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagCompound
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValueCompound.HT_T_NBTValueCompound
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object HT_RichItemStack {
  val NullStack = new HT_RichItemStack(null)
}

/**
 * Created by user on 2015/02/26.
 */
class HT_RichItemStack(val wrapped: ItemStack) extends AnyVal with HT_T_NBTValueCompound[HT_RichItemStack] with HT_ItemStackOp[HT_RichItemStack] {

  def unwrap : ItemStack = if (isEmpty) null else stack

  def isEmpty = stackSize == 0


  def writeToNBT(nbtTagCompound: NBTTagCompound) = wrapped.writeToNBT(nbtTagCompound)

  override def toString: String = if (isEmpty) "empty stack" else stack.toString



  override def writeToNBT(compound: HT_RichNBTTagCompound): Unit = stack writeToNBT compound

  override def readFromNBT(compound: HT_RichNBTTagCompound): HT_RichItemStack = ItemStack loadItemStackFromNBT compound

  override def tag: NBTTagCompound = this.getOrCreateTag match {
    case Some(v) => v
    case _ => null
  }

  override def stack: ItemStack = wrapped
}
