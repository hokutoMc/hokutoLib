package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.item.HT_RichItemStack
import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagCompound
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValueCompound.HT_T_NBTValueCompound
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

/**
 * Created by user on 2015/02/26.
 */
class HT_RichInventory (val inventory: IInventory) extends AnyVal with HT_T_NBTValueCompound[HT_RichInventory]{
  def apply (index: Int) : HT_RichItemStack = inventory getStackInSlot index

  def update(index: Int, itemStack: ItemStack) = inventory.setInventorySlotContents(index, itemStack)

  override def writeToNBT(compound: HT_RichNBTTagCompound): Unit = inventory.writeToNBT(compound)

  override def readFromNBT(compound: HT_RichNBTTagCompound): HT_RichInventory = inventory.readFromNBT(compound)

  def foreach(func: ItemStack => Unit) = for (i <- 0 until inventory.getSizeInventory) func(inventory.getStackInSlot(i))

}
