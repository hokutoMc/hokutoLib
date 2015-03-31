package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagCompound
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValueCompound.HT_T_NBTValueCompound
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

/**
 * Created by user on 2015/02/26.
 */
class HT_RichInventory (val inventory: IInventory) extends AnyVal with HT_T_NBTValueCompound[HT_RichInventory]{
  def apply(index: Int): ItemStack = inventory getStackInSlot index

  def update(index: Int, itemStack: ItemStack) = inventory.setInventorySlotContents(index, itemStack)

  def checkEmpty(index: Int) = if (this(index) != null && this(index).stackSize == 0) this.update(index, null)

  def checkAllEmpty() = for (i <- 0 until inventory.getSizeInventory) checkEmpty(i)

  override def writeToNBT(compound: HT_RichNBTTagCompound): Unit = inventory.writeToNBT(compound)

  override def readFromNBT(compound: HT_RichNBTTagCompound): HT_RichInventory = inventory.readFromNBT(compound)

  def foreach(func: ItemStack => Unit) = this.map(func)
  
  def map[A](func: ItemStack => A) = for (i <- 0 until inventory.getSizeInventory) yield func(inventory.getStackInSlot(i))
}
