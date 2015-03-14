package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.{HT_FurnaceRecipeBuilder, HT_ItemStackBuilder4Recipe => ISB4R}
import net.minecraft.item.Item

/**
 * Created by user on 2015/03/05.
 */
class HT_ScalaFurnaceRecipeBuilder extends HT_FurnaceRecipeBuilder with HT_T_ScalaRecipeBuilder[HT_ScalaFurnaceRecipeBuilder]{

  type SELF = HT_ScalaFurnaceRecipeBuilder

  override def getThis: HT_ScalaFurnaceRecipeBuilder = this


  def source(item: Item)(func: ISB4R[HT_FurnaceRecipeBuilder] => AnyISB4R = THRU): SELF = {
    super.from(item)
    this
  }

  override def withXp(xp: Double): SELF = {
    super.withXp(xp)
    this
  }

  def to(item: Item)(func: ISB4R[HT_FurnaceRecipeBuilder] => AnyISB4R = THRU): SELF = {
    super.to(item)
    this
  }
}
