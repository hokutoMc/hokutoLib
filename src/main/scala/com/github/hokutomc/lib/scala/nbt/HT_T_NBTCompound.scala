package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.nbt.HT_NBTEvidence
import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagList.{ListCompound, ListDouble, ListFloat, ListString}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants.NBT._

/**
 * Created by user on 2015/03/18.
 */
trait HT_T_NBTCompound[A] extends Any {
  this: A =>
  def delegate: NBTTagCompound

  def isNull = delegate eq null

  def update[T: HT_NBTEvidence](key: String, t: T): Unit = {
    if (!isNull) implicitly[HT_NBTEvidence[T]].write(key, delegate, t)
  }

  def apply[T: HT_NBTEvidence](key: String) = {
    if (isNull || !delegate.hasKey(key)) None
    else Some(implicitly[HT_NBTEvidence[T]].read(key, delegate))
  }

  def doubleList(key: String) = if (isNull) None else Some(new ListDouble(delegate.getTagList(key, TAG_DOUBLE)))

  def floatList(key: String) = if (isNull) None else Some(new ListFloat(delegate.getTagList(key, TAG_FLOAT)))

  def tagCompList(key: String) = if (isNull) None else Some(new ListCompound(delegate.getTagList(key, TAG_COMPOUND)))

  def stringList(key: String) = if (isNull) None else Some(new ListString(delegate.getTagList(key, TAG_STRING)))
}
