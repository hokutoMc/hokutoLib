package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.nbt.HT_NBTUtil
import com.github.hokutomc.lib.scala.HT_Predef._
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.BlockPos

/**
 * Created by user on 2015/04/12.
 */
object HT_NBTEvidence {

  trait Compound[E] extends HT_NBTEvidence[E] {

    def writeComp(tag: NBTTagCompound, value: E): Unit

    def readComp(tag: NBTTagCompound): E

    final def write(key: String, tag: NBTTagCompound, a: E): Unit = {
      writeComp(tag.getCompoundTag(key), a)
    }

    final def read(key: String, tag: NBTTagCompound): E = {
      readComp(tag.getCompoundTag(key))
    }
  }


  class EvItemStackArray(val size: Int) extends HT_NBTEvidence.Compound[Array[ItemStack]] {

    override def writeComp(tag: NBTTagCompound, value: Array[ItemStack]): Unit = HT_NBTUtil.writeItemStacks(tag, value)

    override def readComp(tag: NBTTagCompound): Array[ItemStack] = HT_NBTUtil.readItemStacks(tag, size)
  }

  object EvBoolean extends HT_NBTEvidence[Boolean] {
    override def write(key: String, tag: NBTTagCompound, a: Boolean): Unit = tag.setBoolean(key, a)

    override def read(key: String, tag: NBTTagCompound): Boolean = tag.getBoolean(key)
  }

  object EvByte extends HT_NBTEvidence[Byte] {
    override def write(key: String, tag: NBTTagCompound, a: Byte): Unit = tag.setByte(key, a)

    override def read(key: String, tag: NBTTagCompound): Byte = tag.getByte(key)
  }

  object EvByteArray extends HT_NBTEvidence[Array[Byte]] {
    override def write(key: String, tag: NBTTagCompound, a: Array[Byte]): Unit = tag.setByteArray(key, a)

    override def read(key: String, tag: NBTTagCompound): Array[Byte] = tag.getByteArray(key)
  }

  object EvShort extends HT_NBTEvidence[Short] {
    override def write(key: String, tag: NBTTagCompound, a: Short): Unit = tag.setShort(key, a)

    override def read(key: String, tag: NBTTagCompound): Short = tag.getShort(key)
  }

  object EvInt extends HT_NBTEvidence[Int] {
    override def write(key: String, tag: NBTTagCompound, a: Int): Unit = tag.setInteger(key, a)

    override def read(key: String, tag: NBTTagCompound): Int = tag.getInteger(key)
  }

  object EvIntArray extends HT_NBTEvidence[Array[Int]] {
    override def write(key: String, tag: NBTTagCompound, a: Array[Int]): Unit = tag.setIntArray(key, a)

    override def read(key: String, tag: NBTTagCompound): Array[Int] = tag.getIntArray(key)
  }

  object EvLong extends HT_NBTEvidence[Long] {
    override def write(key: String, tag: NBTTagCompound, a: Long): Unit = tag.setLong(key, a)

    override def read(key: String, tag: NBTTagCompound): Long = tag.getLong(key)
  }

  object EvFloat extends HT_NBTEvidence[Float] {
    override def write(key: String, tag: NBTTagCompound, a: Float): Unit = tag.setFloat(key, a)

    override def read(key: String, tag: NBTTagCompound): Float = tag.getFloat(key)
  }

  object EvDouble extends HT_NBTEvidence[Double] {
    override def write(key: String, tag: NBTTagCompound, a: Double): Unit = tag.setDouble(key, a)

    override def read(key: String, tag: NBTTagCompound): Double = tag.getDouble(key)
  }

  object EvString extends HT_NBTEvidence[String] {
    override def write(key: String, tag: NBTTagCompound, a: String): Unit = tag.setString(key, a)

    override def read(key: String, tag: NBTTagCompound): String = tag.getString(key)
  }

  object EvItemStack extends HT_NBTEvidence.Compound[ItemStack] {
    override def writeComp(tag: NBTTagCompound, value: ItemStack): Unit = value.wrapped.writeToNBT(tag)

    override def readComp(tag: NBTTagCompound): ItemStack = ItemStack.loadItemStackFromNBT(tag)
  }

  object EvBlockPos extends HT_NBTEvidence.Compound[BlockPos] {
    override def writeComp(tag: NBTTagCompound, value: BlockPos): Unit = {
      tag.update[Int]("x", value.getX)
      tag.update[Int]("y", value.getY)
      tag.update[Int]("z", value.getZ)
    }

    override def readComp(tag: NBTTagCompound): BlockPos = (for {
      x <- tag[Int]("x")
      y <- tag[Int]("y")
      z <- tag[Int]("z")
    } yield new BlockPos(x, y, z)) orNull
  }

}

trait HT_NBTEvidence[E] {
  def write(key: String, tag: NBTTagCompound, a: E): Unit

  def read(key: String, tag: NBTTagCompound): E
}