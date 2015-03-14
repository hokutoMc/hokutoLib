package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.{HT_ItemStackBuilder4Recipe => ISB4r, HT_ShapelessRecipeBuilder}
import net.minecraft.item.Item

/**
 * Created by user on 2015/03/04.
 */
class HT_ScalaShapelessRecipeBuilder extends HT_ShapelessRecipeBuilder with HT_T_ScalaRecipeBuilder[HT_ScalaShapelessRecipeBuilder]{

  type SELF = HT_ScalaShapelessRecipeBuilder

  override def getThis: SELF = this


  override def fromOre(oreName: String): SELF = {
    super.fromOre(oreName)
    this
  }

  def source(item: Item)(function: ISB4r[HT_ShapelessRecipeBuilder] => AnyISB4R = THRU): SELF = {
    function(from(item)).endItem()
    this
  }

  def andSource(item: Item)(function: ISB4r[HT_ShapelessRecipeBuilder] => AnyISB4R = THRU): SELF = {
    function(and(item)).endItem()
    this
  }

  def result(item: Item)(function: ISB4r[HT_ShapelessRecipeBuilder] => AnyISB4R = THRU): SELF = {
    function(to(item)).endItem()
    this
  }
}
