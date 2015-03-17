package com.github.hokutomc.lib.scala.entity

import com.github.hokutomc.lib.scala.HT_RichItemStack
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.util.HT_PlayerUtil
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource

/**
 * Created by user on 2015/02/26.
 */
class HT_RichPlayer(val player: EntityPlayer) extends AnyVal with HT_RichEntity {

  override def entity = player

  implicit def unwrap: EntityPlayer = player

  def toDamageSource = DamageSource.causePlayerDamage(player)

  // operation support

  def unary_~ : HT_RichItemStack = player getCurrentEquippedItem

  def unary_! : Boolean = player isBlocking

  def apply(int: Int): HT_RichItemStack = player.inventory(int)

  def update(int: Int, itemStack: HT_RichItemStack) = player.inventory(int) = itemStack

  def <--(message: String): HT_RichPlayer = HT_PlayerUtil.displayMessage(this, message)

  def <==(itemStacks: HT_RichItemStack*) = itemStacks foreach {
    player.inventory.addItemStackToInventory(_)
  }

}
