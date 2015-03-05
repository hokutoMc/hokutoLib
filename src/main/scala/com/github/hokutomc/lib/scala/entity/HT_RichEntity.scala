package com.github.hokutomc.lib.scala.entity

import com.github.hokutomc.lib.scala.nbt.HT_RichNBTTagCompound
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValueCompound.HT_T_NBTValueCompound
import net.minecraft.entity.Entity
import net.minecraft.util.Vec3
import com.github.hokutomc.lib.scala.HT_ScalaConversion._

/**
 * Created by user on 2015/02/28.
 */
object HT_RichEntity {
  trait HT_RichEntity extends Any with HT_T_NBTValueCompound[HT_RichEntity]{
    def entity : Entity

    def >> (ridden: Entity) = entity.mountEntity(ridden)

    def << (riding: Entity) = riding.mountEntity(riding)

    def unary_- : Boolean = entity isSneaking

    def unary_+ : Boolean = entity isSprinting

    def <--> (vec3: Vec3) : Double = entity.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord)

    def <--> (target: Entity) : Double = entity getDistanceToEntity target

    override def writeToNBT(compound: HT_RichNBTTagCompound): Unit = entity.writeToNBT(compound)

    override def readFromNBT(compound: HT_RichNBTTagCompound): HT_RichEntity = {
      entity.readFromNBT(compound)
      this
    }
  }

  class HT_RichEntityImpl (val entity: Entity) extends AnyVal with HT_RichEntity
}

