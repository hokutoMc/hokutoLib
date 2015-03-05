package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.HT_FurnaceRecipeBuilder
import com.github.hokutomc.lib.item.recipe.HT_FurnaceRecipeBuilder.ISB4RF
import net.minecraft.item.Item

/**
 * Created by user on 2015/03/05.
 */
class HT_ScalaFurnaceRecipeBuilder extends HT_FurnaceRecipeBuilder with HT_T_ScalaRecipeBuilder[HT_ScalaFurnaceRecipeBuilder]{

  type SELF = HT_ScalaFurnaceRecipeBuilder

  override def getThis: HT_ScalaFurnaceRecipeBuilder = this



  def from(item: Item)(func: ISB4RF => AnyISB4R): SELF = {
    super.from(item)
    this
  }

  def to(item: Item)(func: ISB4RF => AnyISB4R): SELF = {
    super.to(item)
    this
  }
}
