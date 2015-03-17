package com.github.hokutomc.lib.scala

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}

/**
 * Created by user on 2015/03/17.
 */
object HT_ScalaUtils {
  def creativeTab(modid: String, name: String, item: => Item) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = item
  }

  def creativeTab(modid: String, name: String, itemStack: => ItemStack) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = itemStack.getItem

    override def getIconItemStack: ItemStack = itemStack
  }
}
