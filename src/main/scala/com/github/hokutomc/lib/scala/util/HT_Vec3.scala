package com.github.hokutomc.lib.scala.util

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.util.Vec3

/**
 * Created by user on 2015/02/26.
 */
object HT_Vec3 {
  def unapply(vec3: Vec3): Option[(Double, Double, Double)] =
    if (vec3 eq null) None else Some(vec3.xCoord, vec3.yCoord, vec3.zCoord)

  def apply(x: Double, y: Double, z: Double): HT_Vec3 = new HT_Vec3(new Vec3(x, y, z))
}

class HT_Vec3(val wrapped: Vec3) extends AnyVal{

  implicit def unwrap : Vec3 = wrapped

  def x : Double = wrapped.xCoord

  def y : Double = wrapped.yCoord

  def z : Double = wrapped.zCoord

  def + (vec3: HT_Vec3) : HT_Vec3 = wrapped.addVector(vec3.x, vec3.y, vec3.z)

  def - (vec3: HT_Vec3) : HT_Vec3 = vec3.unwrap.subtract(wrapped)

  def * (vec3: HT_Vec3) : Double = wrapped.dotProduct(vec3.unwrap)

  def *:(double: Double): HT_Vec3 = new Vec3(double * x, double * y, double * z)

  def × (vec3: Vec3) : HT_Vec3 = wrapped.crossProduct(vec3.unwrap)

  def unary_~ : HT_Vec3 = wrapped.normalize()

  def unary_! : Double = wrapped.lengthVector()

  def <--> (vec3: HT_Vec3) : Double = wrapped.distanceTo(vec3.unwrap)

  def <<-->> (vec3: HT_Vec3) : Double = wrapped.squareDistanceTo(vec3.unwrap)
}
