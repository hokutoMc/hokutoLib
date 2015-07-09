package com.github.hokutomc.lib.scala
package event

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.item.HT_RichItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.event.entity.player.PlayerEvent

/**
 * Created by user on 2015/07/07.
 */
class HT_RichPlayerEvent(val event: PlayerEvent) extends AnyVal {
  def unary_+ : EntityPlayer = event entityPlayer

  def unary_~ : HT_RichItemStack = ~(+this)

  def unary_- : World = (+this) worldObj
}