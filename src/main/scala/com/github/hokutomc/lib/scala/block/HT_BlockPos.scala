package com.github.hokutomc.lib.scala.block

import net.minecraft.util.{BlockPos, EnumFacing, Vec3i}

/**
 * Created by user on 2015/03/20.
 */
object HT_BlockPos {
  def unapply(p: BlockPos): Option[(Int, Int, Int)] =
    if (p eq null) None else Some(p.getX, p.getY, p.getZ)

  def apply(x: Int, y: Int, z: Int) = new HT_BlockPos(new BlockPos(x, y, z))
}

class HT_BlockPos(val blockPos: BlockPos) extends AnyVal {
  def +(vec3i: Vec3i) = blockPos.add(vec3i)

  def +(enumFacing: EnumFacing) = blockPos.offset(enumFacing)

  def +(offset: (EnumFacing, Int)) = blockPos.offset(offset._1, offset._2)

  def +(v: (Int, Int, Int)) = blockPos.add(v._1, v._2, v._3)
}
