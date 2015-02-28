package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.data.enumerate.{HT_I_IntOrdered, HT_I_StringOrdered}
import com.github.hokutomc.lib.nbt.HT_NBTUtil
import com.github.hokutomc.lib.scala.HT_RichItemStack
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValue.HT_T_NBTValue
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

import scala.reflect.ClassTag

/**
 * Created by user on 2015/02/26.
 */
class HT_RichNBTTagCompound extends NBTTagCompound{

  def ifHasKey (key: String, func: => Unit) = if (hasKey(key)) func

  def update [T <: HT_T_NBTValue[T]](key: String, nbtValue: HT_T_NBTValue[T]): HT_RichNBTTagCompound = {
    nbtValue.setToNBT(this, key)
    this
  }

  def apply [T <: HT_T_NBTValue[T]](key: String, nbtValue: T): T = nbtValue.getFromNBT(this, key)

  def apply (key: String, func: => Unit) = ifHasKey(key, func)
}
