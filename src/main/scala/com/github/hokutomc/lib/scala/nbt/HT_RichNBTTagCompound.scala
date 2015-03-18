package com.github.hokutomc.lib.scala.nbt

import net.minecraft.nbt.NBTTagCompound

/**
 * Created by user on 2015/02/26.
 */
class HT_RichNBTTagCompound(val wrapped: NBTTagCompound) extends AnyVal with HT_T_NBTCompound {
  override def tag: NBTTagCompound = wrapped
}
