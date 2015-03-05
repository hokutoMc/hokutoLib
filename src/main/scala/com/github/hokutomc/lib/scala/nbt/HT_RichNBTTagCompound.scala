package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValue.HT_T_NBTValue
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by user on 2015/02/26.
 */
class HT_RichNBTTagCompound (val wrapped: NBTTagCompound) extends AnyVal{

  implicit def unwrap: NBTTagCompound = wrapped

  def ifHasKey (key: String, func: => Unit) = if (wrapped.hasKey(key)) func

  def update [T <: HT_T_NBTValue[T]](key: String, nbtValue: HT_T_NBTValue[T]): HT_RichNBTTagCompound = {
    nbtValue.setToNBT(this, key)
    this
  }

  def apply [T <: HT_T_NBTValue[T]](key: String, nbtValue: T): T = nbtValue.getFromNBT(this, key)

  def apply (key: String, func: => Unit) = ifHasKey(key, func)
}
