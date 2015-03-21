package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.nbt.NBTTagCompound

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
}
