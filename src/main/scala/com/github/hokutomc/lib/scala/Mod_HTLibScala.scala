package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.item.recipe.HT_ScalaCraftingRecipeBuilder
import cpw.mods.fml.common.Mod
import net.minecraft.init.Items

@Mod(modid = "hokutomc.lib.scala", modLanguage = "scala")
object Mod_HTLibScala {
  HT_ScalaCraftingRecipeBuilder().param('a', Items.carrot).param('a', Items.carrot)
  //  .param('b', Items.diamond)
}