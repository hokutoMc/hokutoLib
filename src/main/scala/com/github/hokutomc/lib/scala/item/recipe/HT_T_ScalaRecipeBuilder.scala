package com.github.hokutomc.lib.scala.item.recipe

import com.github.hokutomc.lib.item.recipe.{HT_ItemStackBuilder4Recipe, HT_RecipeBuilder}
import net.minecraft.item.EnumDyeColor

/**
 * Created by user on 2015/03/04.
 */
trait HT_T_ScalaRecipeBuilder[Repr <: HT_T_ScalaRecipeBuilder[Repr]]{

  def getThis: Repr

  type AnyISB4R = HT_ItemStackBuilder4Recipe[_]

  def THRU[R <: HT_RecipeBuilder[R]]: HT_ItemStackBuilder4Recipe[R] => AnyISB4R = a => a

  def eachColor(function: (Repr, EnumDyeColor) => Unit): Repr = {
    EnumDyeColor.values().foreach(function(getThis, _))
    getThis
  }

  def forInt (range: Range)(function: (Repr, Int) => Unit): Repr = {
    range foreach {function(getThis, _)}
    getThis
  }
}
