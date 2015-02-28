package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.HT_CraftingRecipeBuilder.ISB4RG
import com.github.hokutomc.lib.item.recipe.{HT_CraftingRecipeBuilder, HT_ItemStackBuilder4Recipe => ISB4R, HT_ShapelessRecipeBuilder}
import com.github.hokutomc.lib.util.HT_Color
import net.minecraft.item.Item

/**
 * Created by user on 2015/02/28.
 */
class HT_ScalaRecipeBuilder extends HT_CraftingRecipeBuilder{

  type SELF = HT_ScalaRecipeBuilder

  def param (char: Char, item: Item) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.param(char, item)).endItem()
    this
  }

  def to (item: Item) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.to(item)).endItem()
    this
  }

  def eachColor (function: (SELF, HT_Color) => Unit): SELF = {
    HT_Color.values().foreach(function(this, _))
    this
  }

  def forInt (range: Range)(function: (SELF, Int) => Unit): SELF = {
    range foreach {function(this, _)}
    this
  }

  def shapeless (function: HT_ShapelessRecipeBuilder => Unit): SELF = {
    function(shapeless())
    this
  }
}

object HT_ScalaRecipeBuilder {
  def apply(): HT_ScalaRecipeBuilder = new HT_ScalaRecipeBuilder
}
