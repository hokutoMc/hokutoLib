package com.github.hokutomc.lib.scala.nbt

import net.minecraft.nbt.NBTTagCompound

/**
 * Created by user on 2015/03/18.
 */
trait HT_T_NBTCompound[A] extends Any {
  this: A =>
  def delegate: NBTTagCompound


}
