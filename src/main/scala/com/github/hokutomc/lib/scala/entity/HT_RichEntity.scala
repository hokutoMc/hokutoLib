package com.github.hokutomc.lib.scala.entity

import net.minecraft.entity.Entity
import net.minecraft.util.Vec3


object HT_RichEntity {

  class Impl(val entity: Entity) extends AnyVal with HT_RichEntity

}

/**
 * Created by user on 2015/02/28.
 */
trait HT_RichEntity extends Any {
  def entity: Entity

  def >>(ridden: Entity) = entity.mountEntity(ridden)

  def <<(riding: Entity) = riding.mountEntity(riding)

  def unary_- : Boolean = entity isSneaking

  def unary_+ : Boolean = entity isSprinting

  def <-->(vec3: Vec3): Double = entity.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord)

  def <-->(target: Entity): Double = entity getDistanceToEntity target

}




