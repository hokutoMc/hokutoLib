package com.github.hokutomc.lib.scala.client

import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation

/**
 * Created by user on 2015/07/20.
 */
trait HT_T_Gui {
  this: GuiScreen =>
  final def drawString(text: String, x: Int, y: Int, color: Int): Unit = {
    this.fontRendererObj.drawString(text, x, y, color)
  }

  final def bindTexture(res: ResourceLocation): Unit = {
    this.mc.renderEngine.bindTexture(res)
  }
}
