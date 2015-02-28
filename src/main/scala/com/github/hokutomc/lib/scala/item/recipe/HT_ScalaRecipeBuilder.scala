package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.{HT_ItemStackBuilder4Recipe, HT_CraftingRecipeBuilder, HT_ShapelessRecipeBuilder}
import com.github.hokutomc.lib.util.HT_Color
import net.minecraft.init.Items
import net.minecraft.item.Item

/**
 * Created by user on 2015/02/28.
 */
class HT_ScalaRecipeBuilder extends HT_CraftingRecipeBuilder{

  type SELF = HT_ScalaRecipeBuilder

  type ISB4RG = HT_ItemStackBuilder4Recipe[SELF]

  {
    param('a', Items.apple) {_.size(10)}
    to(Items.diamond) {_.setBoolean("a", false)}
  }

  def param (char: Char, item: Item) (function: ISB4RG => ISB4RG): SELF = {
    function(super.param(char, item).asInstanceOf[ISB4RG]).endItem()
    this
  }

  def to (item: Item) (function: ISB4RG => ISB4RG): SELF = {
    function(super.to(item).asInstanceOf[ISB4RG]).endItem()
    this
  }

  def eachColor (function: HT_Color => Unit): SELF = {
    HT_Color.values().foreach(function)
    this
  }

  def forInt (range: Range)(function: Int => Unit): SELF = {
    range foreach function
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
