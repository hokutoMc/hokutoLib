package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.scala.nbt.HT_T_NBTCompound
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.oredict.OreDictionary

import scala.reflect.ClassTag

object HT_RichItemStack {
  def apply(stack: ItemStack): HT_RichItemStack = {
    if (stack == null || stack.stackSize == 0) HT_RichItemStack.NullStack else new Impl(stack)
  }

  object NullStack extends HT_RichItemStack {
    override protected def stackOp: Option[ItemStack] = None

    override def unwrap: ItemStack = null

    override def isEmpty: Boolean = true

    override def item: Item = null

    override def damage: Int = throw new NoSuchElementException

    override def size: Int = 0

    override def tag: NBTTagCompound = null

    override def damage_=(d: Int): Unit = throw new UnsupportedOperationException

    override def size_=(s: Int): Unit = throw new UnsupportedOperationException

    override def tag_=(t: NBTTagCompound): Unit = throw new UnsupportedOperationException

    override def createTag(): Unit = throw new UnsupportedOperationException

    override def sameItem[A <: Item : ClassTag](a: A): Option[A] = None

    override def isItemInstance[A: ClassTag] = None

    override def unary_+ : HT_RichItemStack = throw new UnsupportedOperationException

    override def unary_- : HT_RichItemStack = throw new UnsupportedOperationException

    override def +(a: Int): HT_RichItemStack = throw new UnsupportedOperationException

    override def -(a: Int): HT_RichItemStack = throw new UnsupportedOperationException

    override def matches(other: HT_RichItemStack): Boolean = OreDictionary.itemMatches(null, other.unwrap, false)

    override def matchesStrict(other: HT_RichItemStack): Boolean = OreDictionary.itemMatches(null, other.unwrap, true)
  }

  class Impl(val wrapped: ItemStack) extends AnyVal with HT_RichItemStack {
    def stack: ItemStack = wrapped

    override protected def stackOp: Option[ItemStack] = if (wrapped eq null) None else Some(wrapped)
  }
}

/**
 * Created by user on 2015/02/26.
 */
trait HT_RichItemStack extends Any with HT_T_NBTCompound[HT_RichItemStack] {
  protected def stackOp: Option[ItemStack]

  def unwrap: ItemStack = stackOp.orNull

  private[this] def forStack(some: ItemStack => Any) = stackOp match {
    case Some(a) => some(a)
    case _ => throw new UnsupportedOperationException
  }

  private[this] def mapStack[A](f: ItemStack => A) = stackOp.map(f)

  def isEmpty: Boolean = stackOp.isEmpty

  def writeToNBT(nbtTagCompound: NBTTagCompound) = {
    forStack(_.writeToNBT(nbtTagCompound)); this
  }

  def item: Item = mapStack(_.getItem).orNull

  /**
   * @throws NoSuchElementException when stack is null
   * @return
   */
  def damage: Int = stackOp match {
    case Some(v) => v.getItemDamage
    case _ => throw new NoSuchElementException
  }

  def size: Int = mapStack(_.stackSize).getOrElse(0)

  def tag: NBTTagCompound = mapStack(_.getTagCompound).orNull

  def damage_=(d: Int): Unit = forStack(_.setItemDamage(if (d < 0) 0 else d))

  def size_=(s: Int): Unit = forStack(_.stackSize = if (s < 0) 0 else s)

  def tag_=(t: NBTTagCompound): Unit = forStack(_.setTagCompound(t))

  def createTag(): Unit = this.tag = new NBTTagCompound

  def sameItem[A <: Item : ClassTag](a: A): Option[A] = stackOp flatMap {
    case HT_ItemStackPattern(i: A, _) if i == a => Some(i)
    case _ => None
  }

  def isItemInstance[A: ClassTag]: Option[Item with A] = stackOp flatMap {
    case HT_ItemStackPattern(i: A, _) => Some(i)
    case _ => None
  }

  def unary_+ : HT_RichItemStack = this + 1

  def unary_- : HT_RichItemStack = this - 1

  def +(a: Int): HT_RichItemStack = {
    size += a; this
  }

  def -(a: Int): HT_RichItemStack = {
    size += a; this
  }

  def matches(other: HT_RichItemStack): Boolean = OreDictionary.itemMatches(this.unwrap, other.unwrap, false)

  def matchesStrict(other: HT_RichItemStack): Boolean = OreDictionary.itemMatches(this.unwrap, other.unwrap, true)
}
