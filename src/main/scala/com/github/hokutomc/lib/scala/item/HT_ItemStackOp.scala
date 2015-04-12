package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.item.{HT_ItemArmor, HT_ItemDurable}
import com.github.hokutomc.lib.oredict.HT_OreDictPlus
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.{HT_RichNBTTagCompound, HT_T_NBTCompound}
import com.github.hokutomc.lib.util.HT_GeneralUtil
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.oredict.OreDictionary

import scala.collection.JavaConversions._
import scala.reflect.ClassTag

/**
 * Created by user on 2015/03/18.
 */
trait HT_ItemStackOp[Repr <: HT_ItemStackOp[Repr]] extends Any with HT_T_NBTCompound[Repr] {
  this: Repr =>

  def stack: ItemStack

  def isStackNull = stack ne null

  def decreaseOne: Repr = decreaseSize(1)

  def decreaseSize(by: Int): Repr = increaseSize(-by)

  def increaseOne: Repr = increaseSize(1)

  def increaseSize(by: Int): Repr = stackSize = stackSize + by

  def stackSize: Int = if (stack == null) 0 else stack.stackSize

  def stackSize_=(size: Int): Repr = {
    stack.stackSize = HT_GeneralUtil.negToZero(size)
    this
  }

  def damage: Option[Int] = getItem map { _ => stack.getItemDamage}

  def damage_=(value: Int) = getItem safe { _ => stack.setItemDamage(value)}

  def durability: Option[Int] = getItemAs[HT_ItemDurable[_]] map { e => e.getDurability(stack)}

  def durability_=(int: Int): Repr = {
    this(HT_ItemDurable.KEY_DURABILITY) = int
    durability match {
      case Some(v) =>
        if (v > 0) this(HT_ItemDurable.KEY_BROKEN) = v <= 0
      case _ =>
    }
    this
  }

  def decreaseDurability(int: Int): Repr = {
    durability foreach { i => durability = i - int}
    this
  }

  def armorPart: Option[HT_ItemArmor.Part] = getItemAs[HT_ItemArmor[_]] map {
    _.getPart(stack)
  }

  private def getItem: Option[Item] = if (stack == null || stack.getItem == null) None else Some(stack.getItem)

  def getItemAs[T](implicit classTag: ClassTag[T]): Option[T] = getItem flatMap { case i: T => Some(i) case _ => None }

  def ifItemIsOf[T, U](func: T => U)(implicit classTag: ClassTag[T]): Option[U] = getItemAs[T] map func

  def ifItemSame[T <: Item, U](item: Item)(func: T => U)(implicit classTag: ClassTag[T]): Option[U] = getItemAs[T] match {
    case Some(v) if v eq item => Some(func(v))
    case _ => None
  }

  def getOrCreateTag: Option[HT_RichNBTTagCompound] = {
    if (isStackNull) return None
    if (!stack.hasTagCompound) {
      stack.setTagCompound(new NBTTagCompound)
    }
    Some(stack.getTagCompound)
  }

  def getOreNames: Seq[String] = HT_OreDictPlus getNames stack

  def hasName(name: String): Boolean = HT_OreDictPlus.hasName(stack, name)


  // operator support
  def unary_- : Repr = decreaseOne

  def unary_+ : Repr = increaseOne

  def unary_! : Option[HT_RichNBTTagCompound] = getOrCreateTag

  def unary_~ : Option[Item] = getItem

  def +(by: Int): Repr = increaseSize(by)

  def -(by: Int): Repr = decreaseSize(by)

  def #-(by: Int): Repr = decreaseDurability(by)

  def =~(input: HT_RichItemStack): Boolean = OreDictionary.itemMatches(stack, input, false)

  def !=~(input: HT_RichItemStack): Boolean = !(this =~ input)

  def ==~(input: HT_RichItemStack): Boolean = OreDictionary.itemMatches(stack, input, true)

  def !==~(input: HT_RichItemStack): Boolean = !(this ==~ input)

  def apply[T <: Item](implicit classTag: ClassTag[T]): Option[T] = getItemAs[T]


  def apply(function: HT_RichItemStack => Unit) = function(stack)

  override def tag: NBTTagCompound = this.getOrCreateTag match {
    case Some(v) => v
    case _ => null
  }

  override def a: Repr = this
}
