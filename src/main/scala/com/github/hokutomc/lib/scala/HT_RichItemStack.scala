package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.item.{HT_ItemArmor, HT_ItemDurable}
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagCompound
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValue.HT_T_NBTValue
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValueCompound.HT_T_NBTValueCompound
import com.github.hokutomc.lib.util.{HT_GeneralUtil, HT_OreUtil}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.oredict.OreDictionary

import scala.reflect.ClassTag


/**
 * Created by user on 2015/02/26.
 */
case class HT_RichItemStack (stack : ItemStack) extends AnyVal with HT_T_NBTValueCompound[HT_RichItemStack]{

  def unwrap : ItemStack = if (isEmpty) null else stack
  
  def isEmpty = stackSize == 0

  def decreaseOne : HT_RichItemStack = decreaseSize(1)
  
  def decreaseSize (by : Int) : HT_RichItemStack = increaseSize(-by)

  def increaseOne  : HT_RichItemStack = increaseSize(1)

  def increaseSize (by : Int) : HT_RichItemStack = stackSize = stackSize + by
  
  def stackSize : Int = stack.stackSize

  def stackSize_= (size : Int) : HT_RichItemStack = {
    stack.stackSize = HT_GeneralUtil.negToZero(size)
    this
  }

  def damage : Option[Int] = getItem map {_ => stack.getItemDamage}
  
  def damage_= (value : Int) = getItem map {_ => stack.setItemDamage(value)}

  def durability : Option[Int] = getItemAs[HT_ItemDurable] map {e => e.getDurability(stack)}

  def durability_= (int: Int) : HT_RichItemStack = {
    this(HT_ItemDurable.KEY_DURABILITY) = int
    durability match {
      case Some(v) => // exactly move to this case close
        if (v > 0) this(HT_ItemDurable.KEY_BROKEN) = v <= 0
      case _ =>}
    this
  }

  def decreaseDurability (int: Int) : HT_RichItemStack = {
    durability foreach { i => durability = i - int }
    this
  }

  def armorPart : Option[HT_ItemArmor.Part] = getItemAs[HT_ItemArmor] map {_.getPart(this)}

  private def getItem : Option[Item] = if (stack.getItem == null) None  else Some(stack.getItem)

  def getItemAs [T <: Item](implicit classTag: ClassTag[T]) : Option[T] = getItem flatMap { case i: T => Some(i) case _ => None}

  def ifItemIsOf [T <: Item, U](func: T => U)(implicit classTag: ClassTag[T]): Option[U] = getItemAs[T] map func

  def ifItemSame [T <: Item, U](item: Item)(func: T => U)(implicit classTag: ClassTag[T]): Option[U] = getItemAs[T] match {
    case Some(v) if v eq item => Some(func(v))
    case _=> None
  }

  def getOrCreateTag : HT_RichNBTTagCompound = {
    if (!stack.hasTagCompound) {stack.setTagCompound(new NBTTagCompound)}
    stack.getTagCompound
  }

  def getOreNames : Array[String] = HT_OreUtil getNames stack
  
  def hasName (name : String) : Boolean = HT_OreUtil.hasName(stack, name)
  
  def writeToNBT (nbtTagCompound: NBTTagCompound) = stack.writeToNBT(nbtTagCompound)

  override def toString: String = if (isEmpty) "empty stack" else stack.toString

  // operator support
  def unary_- : HT_RichItemStack = decreaseOne
  
  def unary_+ : HT_RichItemStack = increaseOne

  def unary_! : HT_RichNBTTagCompound = getOrCreateTag

  def unary_~ : Option[Item] = getItem
  
  def + (by : Int) : HT_RichItemStack = increaseSize(by)
  
  def - (by : Int) : HT_RichItemStack = decreaseSize(by)

  def #- (by: Int) : HT_RichItemStack = decreaseDurability(by)

  def =~ (input: HT_RichItemStack) : Boolean = OreDictionary.itemMatches(this, input, false)

  def !=~ (input: HT_RichItemStack) : Boolean = !(this =~ input)

  def ==~ (input: HT_RichItemStack) : Boolean = OreDictionary.itemMatches(this, input, true)

  def !==~ (input: HT_RichItemStack) : Boolean = !(this ==~ input)

  def apply [T <: Item](implicit classTag: ClassTag[T]) : Option[T] = getItemAs[T]

  def apply [T <: HT_T_NBTValue[T]] (key: String, nbtValue: T): T = {
    nbtValue.getFromNBT(!this, key)
  }

  def update [T <: HT_T_NBTValue[T]](key: String, nbtValue: HT_T_NBTValue[T]): HT_RichItemStack = {
    nbtValue.setToNBT(!this, key)
    this
  }

  override def writeToNBT(compound: HT_RichNBTTagCompound): Unit = stack writeToNBT compound

  override def readFromNBT(compound: HT_RichNBTTagCompound): HT_RichItemStack = ItemStack loadItemStackFromNBT compound
}



