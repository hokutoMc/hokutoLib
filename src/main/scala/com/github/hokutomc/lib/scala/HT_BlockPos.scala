package com.github.hokutomc.lib.scala

import net.minecraft.util.{BlockPos, EnumFacing, Vec3i}

/**
 * Created by user on 2015/03/20.
 */
class HT_BlockPos(val blockPos: BlockPos) extends AnyVal {
  def +(vec3i: Vec3i) = blockPos.add(vec3i)

  def +(enumFacing: EnumFacing) = blockPos.offset(enumFacing)

  def +(offset: (EnumFacing, Int)) = blockPos.offset(offset._1, offset._2)
}
