package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.HT_CraftingRecipeBuilder.ISB4RG
import com.github.hokutomc.lib.item.recipe.{HT_CraftingRecipeBuilder, HT_ItemStackBuilder4Recipe => ISB4R}
import net.minecraft.block.Block
import net.minecraft.item.Item

/**
 * Created by user on 2015/02/28.
 */
class HT_ScalaCraftingRecipeBuilder extends HT_CraftingRecipeBuilder with HT_T_ScalaRecipeBuilder[HT_ScalaCraftingRecipeBuilder]{

  type SELF = HT_ScalaCraftingRecipeBuilder


  override def getThis: SELF = this

  override def grid(grid: String*): SELF = {
    super.grid(grid: _*)
    this
  }


  override def paramOre(symbol: Char, oreName: String): SELF = {
    super.paramOre(symbol, oreName)
    this
  }

  

  def param (char: Char, item: Item) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.param(char, item)).endItem()
    this
  }

  def param (char: Char, item: Block) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.param(char, item)).endItem()
    this
  }

  def to (item: Item) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.to(item)).endItem()
    this
  }

  def to (item: Block) (function: ISB4RG => ISB4R[_]): SELF = {
    function(super.to(item)).endItem()
    this
  }

  def shapeless (function: HT_ScalaShapelessRecipeBuilder => Unit): SELF = {
    function(shapeless())
    this
  }

  override def shapeless(): HT_ScalaShapelessRecipeBuilder = new HT_ScalaShapelessRecipeBuilder

  def furnace (function: HT_ScalaFurnaceRecipeBuilder => Unit): SELF = {
    function(new HT_ScalaFurnaceRecipeBuilder())
    this
  }
}

object HT_ScalaCraftingRecipeBuilder {
  def apply(): HT_ScalaCraftingRecipeBuilder = new HT_ScalaCraftingRecipeBuilder
}
