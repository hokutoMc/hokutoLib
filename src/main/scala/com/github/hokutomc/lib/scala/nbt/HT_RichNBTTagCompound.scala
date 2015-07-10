package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.nbt.HT_NBTEvidence
import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagList.{ListCompound, ListDouble, ListFloat, ListString}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants.NBT._

/**
 * Created by user on 2015/02/26.
 */
class HT_RichNBTTagCompound(val wrapped: NBTTagCompound) extends AnyVal {
  def isNull = wrapped eq null

  def update[T: HT_NBTEvidence](key: String, t: T): Unit = {
    if (!isNull) implicitly[HT_NBTEvidence[T]].write(key, wrapped, t)
  }

  def apply[T: HT_NBTEvidence](key: String) = {
    if (isNull || !wrapped.hasKey(key)) None
    else Some(implicitly[HT_NBTEvidence[T]].read(key, wrapped))
  }

  def doubleList(key: String) = if (isNull) None else Some(new ListDouble(wrapped.getTagList(key, TAG_DOUBLE)))

  def floatList(key: String) = if (isNull) None else Some(new ListFloat(wrapped.getTagList(key, TAG_FLOAT)))

  def tagCompList(key: String) = if (isNull) None else Some(new ListCompound(wrapped.getTagList(key, TAG_COMPOUND)))

  def stringList(key: String) = if (isNull) None else Some(new ListString(wrapped.getTagList(key, TAG_STRING)))
}
