package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.data.enumerate.{HT_I_IntOrdered, HT_I_StringOrdered}
import com.github.hokutomc.lib.nbt.HT_NBTUtil
import com.github.hokutomc.lib.scala.HT_ScalaConversion._

trait HT_T_NBTValue[T] extends Any {
  def setToNBT(nbt: HT_RichNBTTagCompound, key: String)

  def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): T
}

/**
 * Created by user on 2015/02/26.
 */
object HT_T_NBTValue {

  implicit class HT_IntNBT (val value: Int) extends AnyVal with HT_T_NBTValue[HT_IntNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setInteger(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_IntNBT = nbt.getInteger(key)
  }

  implicit def unwrapInt(i: HT_IntNBT): Int = i.value

  implicit class HT_ByteNBT (val value: Byte) extends AnyVal with HT_T_NBTValue[HT_ByteNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setByte(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_ByteNBT = nbt.getByte(key)
  }

  implicit def unwrapByte(b: HT_ByteNBT): Byte = b.value

  implicit class HT_ShortNBT (val value: Short) extends AnyVal with HT_T_NBTValue[HT_ShortNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setShort(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_ShortNBT = nbt.getShort(key)
  }

  implicit def unwrapShort(s: HT_ShortNBT): Short = s.value

  implicit class HT_LongNBT (val value: Long) extends AnyVal with HT_T_NBTValue[HT_LongNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setLong(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_LongNBT = nbt.getLong(key)
  }

  implicit def unwrapLong(l: HT_LongNBT): Long = l.value

  implicit class HT_BoolNBT (val value: Boolean) extends AnyVal with HT_T_NBTValue[HT_BoolNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setBoolean(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_BoolNBT = nbt.getBoolean(key)
  }

  implicit def unwrapBool(b: HT_BoolNBT): Boolean = b.value

  implicit class HT_FloatNBT (val value: Float) extends AnyVal with HT_T_NBTValue[HT_FloatNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setFloat(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_FloatNBT = nbt.getFloat(key)
  }

  implicit def unwrapFloat(f: HT_FloatNBT): Float = f.value

  implicit class HT_DoubleNBT (val value: Double) extends AnyVal with HT_T_NBTValue[HT_DoubleNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setDouble(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_DoubleNBT = nbt.getDouble(key)
  }

  implicit def unwrapDouble(d: HT_DoubleNBT): Double = d.value

  implicit class HT_StringNBT (val value: String) extends AnyVal with HT_T_NBTValue[HT_StringNBT] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = nbt.setString(key, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_StringNBT = nbt.getString(key)
  }

  implicit def unwrapString(s: HT_StringNBT): String = s.value

  implicit class HT_Enum[E <: Enum[E]] (val value: E) extends AnyVal with HT_T_NBTValue[HT_Enum[E]] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = HT_NBTUtil.writeEnum(key, nbt, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_Enum[E] = HT_NBTUtil.readEnum(key, nbt, value.getDeclaringClass)
  }

  implicit def unwrapEnum[E <: Enum[E]](e: HT_Enum[E]): E = e.value

  implicit class HT_IntOrdered[T <: HT_I_IntOrdered[T]] (val value: T) extends AnyVal with HT_T_NBTValue[HT_I_IntOrdered[T]] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = HT_NBTUtil.writeIntOrdered(key, nbt, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_I_IntOrdered[T] = HT_NBTUtil.readIntOrdered(key, nbt, value)
  }

  implicit def unwrapIntOrdered[T <: HT_I_IntOrdered[T]](t: HT_IntOrdered[T]): T = t.value

  implicit class HT_StringOrdered[T <: HT_I_StringOrdered[T]] (val value: T) extends AnyVal with HT_T_NBTValue[HT_I_StringOrdered[T]] {
    override def setToNBT(nbt: HT_RichNBTTagCompound, key: String): Unit = HT_NBTUtil.writeStringOrdered(key, nbt, value)

    override def getFromNBT(nbt: HT_RichNBTTagCompound, key: String): HT_I_StringOrdered[T] = HT_NBTUtil.readStringOrdered(key, nbt, value)
  }

  implicit def unwrapStringOrdered[T <: HT_I_StringOrdered[T]](t: HT_StringOrdered[T]): T = t.value
}

