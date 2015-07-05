package com.github.hokutomc.lib.scala.world

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.util.{AxisAlignedBB, BlockPos}
import net.minecraft.world.World

import scala.collection.JavaConversions._
import scala.reflect.ClassTag

/**
 * Created by user on 2015/03/15.
 */
class HT_World(val world: World) extends AnyVal {
  def apply(blockPos: BlockPos): IBlockState = world.getBlockState(blockPos)

  def update(blockPos: BlockPos, flags: Int = 3)(blockState: IBlockState): Unit = world.setBlockState(blockPos, blockState)

  def server[A](function: () => A): A = function()

  def entitiesWithin[A <: Entity : ClassTag](aabb: AxisAlignedBB): Seq[A] =
    world.getEntitiesWithinAABB(implicitly[ClassTag[A]].runtimeClass, aabb).collect { case a: A => a }

  def entitiesWithinExcluding[A <: Entity : ClassTag](entity: Entity, aabb: AxisAlignedBB): Seq[A] =
    world.getEntitiesWithinAABBExcludingEntity(entity, aabb).collect { case a: A => a }
}
