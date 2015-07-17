package com.github.hokutomc.lib.scala
package item

import com.github.hokutomc.lib.nbt.HT_NBTEvidence
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

    override def damage_=(d: Int): Unit = throw new UnsupportedOperationException

    override def size_=(s: Int): Unit = throw new UnsupportedOperationException

    override def tag_=(t: TagComp): Unit = throw new UnsupportedOperationException

    override def createTag(): Unit = throw new UnsupportedOperationException

    @deprecated
    override def sameItem[A <: Item : ClassTag](a: A): Option[A] = None

    @deprecated
    override def isItemInstance[A: ClassTag] = None

    override def unary_+ : HT_RichItemStack = throw new UnsupportedOperationException

    override def unary_- : HT_RichItemStack = throw new UnsupportedOperationException

    override def +(a: Int): HT_RichItemStack = throw new UnsupportedOperationException

    override def -(a: Int): HT_RichItemStack = throw new UnsupportedOperationException

    override def matches(other: HT_RichItemStack): Boolean = OreDictionary.itemMatches(null, other.unwrap, false)

    override def matchesStrict(other: HT_RichItemStack): Boolean = OreDictionary.itemMatches(null, other.unwrap, true)

    override def apply[T: HT_NBTEvidence](key: String): Option[T] = None

    override def update[T: HT_NBTEvidence](key: String, t: T): Unit = {}
  }

  class Impl(val wrapped: ItemStack) extends AnyVal with HT_RichItemStack {
    def stack: ItemStack = wrapped

    override protected def stackOp: Option[ItemStack] = if (wrapped eq null) None else Some(wrapped)
  }
}

/**
 * Created by user on 2015/02/26.
 */
trait HT_RichItemStack extends Any {
  protected def stackOp: Option[ItemStack]

  def unwrap: ItemStack = stackOp.orNull

  private[this] def forStack(some: ItemStack => Any) = stackOp match {
    case Some(a) => some(a)
    case _ => throw new UnsupportedOperationException
  }

  private[this] def mapStack[A](f: ItemStack => A) = stackOp.map(f)

  def isEmpty: Boolean = stackOp.isEmpty

  def writeToNBT(nbtTagCompound: TagComp) = {
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


  def tag: TagComp = mapStack(_.getTagCompound).orNull

  def ensureTagCreated(): TagComp =
    mapStack { i =>
      if (!i.hasTagCompound) {
        createTag()
      }
      tag
    }.orNull
  

  def damage_=(d: Int): Unit = forStack(_.setItemDamage(if (d < 0) 0 else d))

  def size_=(s: Int): Unit = forStack(_.stackSize = if (s < 0) 0 else s)

  def tag_=(t: TagComp): Unit = forStack(_.setTagCompound(t))

  def createTag(): Unit = this.tag = new TagComp

  @deprecated
  def sameItem[A <: Item : ClassTag](a: A): Option[A] = stackOp flatMap {
    case HT_ItemStackPattern(i: A, _) if i == a => Some(i)
    case _ => None
  }

  @deprecated
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

  def apply(f: HT_RichItemStack => Any): HT_RichItemStack = {
    f(this)
    this
  }

  //delegation to nbt tag
  def apply[T: HT_NBTEvidence](key: String): Option[T] =
    if (isEmpty || !stackOp.exists(_.hasTagCompound) || !tag.hasKey(key)) None
    else HT_ScalaConversion.wrapNBTTagComp(tag).apply[T](key)

  def update[T: HT_NBTEvidence](key: String, t: T): Unit = {
    HT_ScalaConversion.wrapNBTTagComp(ensureTagCreated()).update(key, t)
  }
}
