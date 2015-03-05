package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.HT_CraftingRecipeBuilder.ISB4RG
import com.github.hokutomc.lib.item.recipe.{HT_CraftingRecipeBuilder, HT_ItemStackBuilder4Recipe => ISB4R}
import net.minecraft.item.Item

/**
 * Created by user on 2015/02/28.
 */
class HT_ScalaCraftingRecipeBuilder extends HT_CraftingRecipeBuilder with HT_T_ScalaRecipeBuilder[HT_ScalaCraftingRecipeBuilder]{

  type SELF = HT_ScalaCraftingRecipeBuilder

  def param (char: Char, item: Item) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.param(char, item)).endItem()
    this
  }

  def to (item: Item) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.to(item)).endItem()
    this
  }

  def shapeless (function: HT_ShapelessScalaRecipeBuilder => Unit): SELF = {
    function(shapeless())
    this
  }

  override def shapeless(): HT_ShapelessScalaRecipeBuilder = new HT_ShapelessScalaRecipeBuilder

  override def getThis: SELF = this
}

object HT_ScalaCraftingRecipeBuilder {
  def apply(): HT_ScalaCraftingRecipeBuilder = new HT_ScalaCraftingRecipeBuilder
}
