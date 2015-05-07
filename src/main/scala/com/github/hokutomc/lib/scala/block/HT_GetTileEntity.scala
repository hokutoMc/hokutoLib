package com.github.hokutomc.lib.scala.block

import net.minecraft.util.BlockPos
import net.minecraft.world.IBlockAccess

import scala.reflect.ClassTag

/**
 * Created by user on 2015/05/06.
 */
trait HT_GetTileEntity[A] {
  def tileEntity(world: IBlockAccess, pos: BlockPos)(implicit classTag: ClassTag[A]): Option[A] = {
    world.getTileEntity(pos) match {
      case e: A => Some(e)
      case _ => None
    }
  }
}
