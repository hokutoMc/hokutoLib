package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.scala.item.recipe.HT_ScalaCraftingRecipeBuilder
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}

/**
 * Created by user on 2015/03/17.
 */
object HT_ScalaUtils {
  def creativeTab(modid: String, name: String, item: => Item) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = item
  }

  def creativeTabFromStack(modid: String, name: String, itemStack: => ItemStack) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = itemStack.getItem

    override def getIconItemStack: ItemStack = itemStack
  }

  def recipe = new HT_ScalaCraftingRecipeBuilder

}
