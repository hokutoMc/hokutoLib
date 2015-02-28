package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.entity.HT_EntityUtil
import com.github.hokutomc.lib.item.tool.HT_ItemTool
import com.github.hokutomc.lib.scala.entity.HT_RichPlayer
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.player.AttackEntityEvent
import HT_ScalaConversion._

@Mod(modid = "hokutomc.lib.scala", modLanguage = "scala")
object Mod_HTLibScala {
  @SubscribeEvent
  def toolHitEntityHook (event: AttackEntityEvent): Unit = {
    val itemStack: HT_RichItemStack = ~event
    itemStack.getItemAs[HT_ItemTool] foreach { item => {
      event.target match {
        case entity: EntityLivingBase => item.HT_hitEntity(itemStack, entity, +event)
        case _ =>
      }

      val reaching: Float = HT_EntityUtil.getReachingSpeed(+event, event.target)
      val bonus: Float = item.getRangeBonus(itemStack, +event <--> event.target toFloat)
      val damage: Float = item.HT_getAttackDamage(itemStack) * bonus * Math.pow(2.0, reaching) * Math.pow(1.4, (+event).posY - event.target.posY) toFloat

      if (damage > 0.5f) {
        event.target.attackEntityFrom(+event toDamageSource, damage)
//        System.out.println("damage:" + damage)
      }
    }}
  }
}