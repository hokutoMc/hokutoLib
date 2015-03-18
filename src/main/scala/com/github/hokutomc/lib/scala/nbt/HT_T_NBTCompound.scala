package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by user on 2015/03/18.
 */
trait HT_T_NBTCompound extends Any {
  def tag: NBTTagCompound

  def ifHasKey(key: String, func: => Unit) = if (tag.hasKey(key)) func

  def update[T <: HT_T_NBTValue[T]](key: String, nbtValue: HT_T_NBTValue[T]): Unit = {
    nbtValue.setToNBT(tag, key)
  }

  def apply[T <: HT_T_NBTValue[T]](key: String, nbtValue: T): T = nbtValue.getFromNBT(tag, key)

  def apply(key: String, func: => Unit) = ifHasKey(key, func)
}
