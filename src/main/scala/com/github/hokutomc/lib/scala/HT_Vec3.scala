package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.util.Vec3

/**
 * Created by user on 2015/02/26.
 */
class HT_Vec3(a: Double, b: Double, c: Double) extends Vec3(a, b, c){



  def x : Double = xCoord

  def y : Double = yCoord

  def z : Double = zCoord

  def + (vec3: HT_Vec3) : HT_Vec3 = addVector(vec3.x, vec3.y, vec3.z)

  def - (vec3: HT_Vec3) : HT_Vec3 = vec3.subtract(this)

  def * (vec3: HT_Vec3) : Double = dotProduct(vec3)

  def × (vec3: HT_Vec3) : HT_Vec3 = crossProduct(vec3)

  def unary_~ : HT_Vec3 = normalize()

  def unary_! : Double = lengthVector()

  def <--> (vec3: HT_Vec3) : Double = distanceTo(vec3)

  def <<-->> (vec3: HT_Vec3) : Double = squareDistanceTo(vec3)
}
