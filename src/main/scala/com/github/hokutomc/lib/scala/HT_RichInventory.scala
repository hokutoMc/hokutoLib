package com.github.hokutomc.lib.scala

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

import scala.collection.mutable

/**
 * Created by user on 2015/02/26.
 */
class HT_RichInventory(val inventory: IInventory) extends AnyVal with mutable.ArrayLike[ItemStack, HT_RichInventory] {
  def apply(index: Int): ItemStack = inventory getStackInSlot index

  def update(index: Int, itemStack: ItemStack) = inventory.setInventorySlotContents(index, itemStack)

  def checkEmpty(index: Int) = if (this(index) != null && this(index).stackSize <= 0) this.update(index, null)

  def checkAllEmpty() = for (i <- 0 until inventory.getSizeInventory) checkEmpty(i)

  def foreach(func: ItemStack => Unit) = this.map[Unit](func)

  def map[A](func: ItemStack => A): Seq[A] = for (i <- 0 until inventory.getSizeInventory) yield func(inventory.getStackInSlot(i))

  override def length: Int = inventory.getSizeInventory

  override protected[this] def newBuilder: mutable.Builder[ItemStack, HT_RichInventory] = throw new UnsupportedOperationException

  override def seq: mutable.IndexedSeq[ItemStack] = mutable.IndexedSeq(map[ItemStack] { a => a }: _*)

  override def iterator: Iterator[ItemStack] = seq iterator
}
