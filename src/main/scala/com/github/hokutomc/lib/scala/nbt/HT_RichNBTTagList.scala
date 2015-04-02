package com.github.hokutomc.lib.scala.nbt

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.nbt.{NBTBase, NBTTagCompound, NBTTagList}
import net.minecraftforge.common.util.Constants.NBT._

import scala.reflect.ClassTag


object HT_RichNBTTagList {

  def apply[A](nBTTagList: NBTTagList)(implicit classTag: ClassTag[A]): HT_RichNBTTagList[A] = {
    val clazz = classTag.runtimeClass
    nBTTagList.getTagType match {
      case TAG_STRING if clazz == classOf[String] => new ListString(nBTTagList).asInstanceOf[HT_RichNBTTagList[A]]
      case TAG_DOUBLE if clazz == classOf[Double] => new ListDouble(nBTTagList).asInstanceOf[HT_RichNBTTagList[A]]
      case TAG_FLOAT if clazz == classOf[Float] => new ListFloat(nBTTagList).asInstanceOf[HT_RichNBTTagList[A]]
      case TAG_COMPOUND if (clazz isAssignableFrom classOf[NBTTagCompound]) || (clazz isAssignableFrom classOf[HT_RichNBTTagCompound]) =>
        new ListCompound(nBTTagList).asInstanceOf[HT_RichNBTTagList[A]]
      case TAG_INT_ARRAY if clazz == classOf[Array[Int]] => new ListIntArray(nBTTagList).asInstanceOf[HT_RichNBTTagList[A]]
    }
  }


  class ListString(override val wrapped: NBTTagList) extends AnyVal with HT_RichNBTTagList[String] {
    override def apply(index: Int): String = wrapped.getStringTagAt(index)
  }

  class ListDouble(override val wrapped: NBTTagList) extends AnyVal with HT_RichNBTTagList[Double] {
    override def apply(index: Int): Double = wrapped.getDouble(index)
  }

  class ListFloat(override val wrapped: NBTTagList) extends AnyVal with HT_RichNBTTagList[Float] {
    override def apply(index: Int): Float = wrapped.getFloat(index)
  }

  class ListCompound(override val wrapped: NBTTagList) extends AnyVal with HT_RichNBTTagList[HT_RichNBTTagCompound] {
    override def apply(index: Int): HT_RichNBTTagCompound = wrapped.getCompoundTagAt(index)
  }

  class ListIntArray(override val wrapped: NBTTagList) extends AnyVal with HT_RichNBTTagList[Array[Int]] {
    override def apply(index: Int): Array[Int] = wrapped.getIntArray(index)
  }

}

/**
 * Created by user on 2015/02/26.
 */
trait HT_RichNBTTagList[+A] extends Any {
  def wrapped: NBTTagList

  def +=(nbtBase: NBTBase): HT_RichNBTTagList[A] = {
    wrapped.appendTag(nbtBase)
    this
  }

  def map[R](func: A => R) = {
    for (i <- 0 until wrapped.tagCount()) yield func(this(i))
  }

  def foreach(func: A => Unit) = this.map(func)

  def apply(index: Int): A
}
