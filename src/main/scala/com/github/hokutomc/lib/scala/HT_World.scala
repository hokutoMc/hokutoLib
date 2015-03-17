package com.github.hokutomc.lib.scala

import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockPos
import net.minecraft.world.World

/**
 * Created by user on 2015/03/15.
 */
class HT_World(val world: World) extends AnyVal {
  def apply(blockPos: BlockPos): IBlockState = world.getBlockState(blockPos)

  def update(blockPos: BlockPos, flags: Int = 3)(blockState: IBlockState): Unit = world.setBlockState(blockPos, blockState)
}
