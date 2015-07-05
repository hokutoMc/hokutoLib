package com.github.hokutomc.lib.scala.block.states

import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.IBlockState

import scala.reflect.ClassTag

/**
 * Created by user on 2015/03/17.
 */
class HT_RichBlockState(val blockState: IBlockState) extends AnyVal {

  def apply[A: ClassTag](iProperty: IProperty): A = blockState.getValue(iProperty) match {
    case a: A => a
    case _ => throw new ClassCastException("Cannot read BlockState property " + iProperty.getName + "'s value of type " + implicitly[ClassTag[A]].toString())
  }

  def update (iProperty: IProperty, comparable: Comparable[_]) = blockState.withProperty(iProperty, comparable)
}
