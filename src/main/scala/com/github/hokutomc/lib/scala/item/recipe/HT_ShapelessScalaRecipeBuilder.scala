package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.HT_ShapelessRecipeBuilder.ISB4RS
import com.github.hokutomc.lib.item.recipe.{HT_ItemStackBuilder4Recipe => ISB4r, HT_ShapelessRecipeBuilder}
import net.minecraft.item.Item

/**
 * Created by user on 2015/03/04.
 */
class HT_ShapelessScalaRecipeBuilder extends HT_ShapelessRecipeBuilder with HT_T_ScalaRecipeBuilder[HT_ShapelessScalaRecipeBuilder]{

  type SELF = HT_ShapelessScalaRecipeBuilder

  def from (item: Item) (function: ISB4RS => ISB4r[_]): SELF = {
    function(from(item)).endItem()
    this
  }

  def and (item: Item) (function: ISB4RS => ISB4r[_]): SELF = {
    function(and(item)).endItem()
    this
  }

  def to (item: Item) (function: ISB4RS => ISB4r[_]): SELF = {
    function(to(item)).endItem()
    this
  }

  override def getThis: SELF = this
}
