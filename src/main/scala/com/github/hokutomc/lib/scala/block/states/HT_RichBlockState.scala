package com.github.hokutomc.lib.scala.block.states

import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.IBlockState

/**
 * Created by user on 2015/03/17.
 */
class HT_RichBlockState(val blockState: IBlockState) {
  def apply (iProperty: IProperty): Comparable[_] = blockState.getValue(iProperty)

  def update (iProperty: IProperty, comparable: Comparable[_]) = blockState.withProperty(iProperty, comparable)
}
