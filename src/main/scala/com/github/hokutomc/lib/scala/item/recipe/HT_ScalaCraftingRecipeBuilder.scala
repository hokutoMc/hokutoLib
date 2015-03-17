package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.{HT_CraftingRecipeBuilder, HT_ItemStackBuilder4Recipe => ISB4R}
import net.minecraft.block.Block
import net.minecraft.item.Item

/**
 * Created by user on 2015/02/28.
 */
class HT_ScalaCraftingRecipeBuilder extends HT_CraftingRecipeBuilder[HT_ScalaCraftingRecipeBuilder]
with HT_T_ScalaRecipeBuilder[HT_ScalaCraftingRecipeBuilder] {

  type SELF = HT_ScalaCraftingRecipeBuilder


  override def getThis: SELF = this

  override def grid(grid: String*): SELF = {
    super.grid(grid: _*)
    getThis
  }


  override def paramOre(symbol: Char, oreName: String): SELF = {
    super.paramOre(symbol, oreName)
    this
  }


  override def param(symbol: Char, item: Item): ISB4R[SELF] = super.param(symbol, item)


  override def param(symbol: Char, block: Block): ISB4R[SELF] = super.param(symbol, block)

  //  override def param(synbol: Char, modid: String, name: String): ISB4R[SELF] = super.param(synbol, modid, name)

  override def to(item: Item): ISB4R[SELF] = super.to(item)

  override def to(block: Block): ISB4R[SELF] = super.to(block)


  def value(char: Char, item: Item)(function: ISB4R[HT_ScalaCraftingRecipeBuilder] => ISB4R[_] = THRU[HT_ScalaCraftingRecipeBuilder]): SELF = {
    function(super.param(char, item)).endItem()
    this
  }

  //  def value (char: Char, item: Block) (function: ISB4R[HT_CraftingRecipeBuilder] => ISB4R[_] = THRU[HT_CraftingRecipeBuilder]): SELF = {
  //    function(super.param(char, item)).endItem()
  //    this
  //  }

  def result(item: Item)(function: ISB4R[HT_ScalaCraftingRecipeBuilder] => ISB4R[_] = THRU[HT_ScalaCraftingRecipeBuilder]): SELF = {
    function(super.to(item)).endItem()
    this
  }

  //  def result (item: Block) (function: ISB4R[HT_CraftingRecipeBuilder] => ISB4R[_] = THRU[HT_CraftingRecipeBuilder]): SELF = {
  //    function(super.to(item)).endItem()
  //    this
  //  }

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
