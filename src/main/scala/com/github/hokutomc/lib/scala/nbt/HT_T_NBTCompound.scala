package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagList.{ListCompound, ListDouble, ListFloat, ListString}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants.NBT._

/**
 * Created by user on 2015/03/18.
 */
trait HT_T_NBTCompound[A] extends Any {
  def tag: NBTTagCompound

  def a: A

  def update[T <: HT_T_NBTValue[T]](key: String, nbtValue: HT_T_NBTValue[T]): Unit = {
    nbtValue.setToNBT(tag, key)
  }

  def apply[T <: HT_T_NBTValue[T]](key: String, defValue: T) = {
    if (!tag.hasKey(key)) None
    else Some(defValue.getFromNBT(tag, key))
  }

  def apply(func: NBTTagCompound => Unit): A = {
    func(tag)
    a
  }


  def doubleList(key: String) = new ListDouble(tag.getTagList(key, TAG_DOUBLE))

  def floatList(key: String) = new ListFloat(tag.getTagList(key, TAG_FLOAT))

  def tagCompList(key: String) = new ListCompound(tag.getTagList(key, TAG_COMPOUND))

  def stringList(key: String) = new ListString(tag.getTagList(key, TAG_STRING))
}
