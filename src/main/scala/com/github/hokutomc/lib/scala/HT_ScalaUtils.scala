package com.github.hokutomc.lib.scala

import java.io.File

import com.github.hokutomc.lib.client.render.HT_RenderUtil
import com.github.hokutomc.lib.common.config.HT_Config
import com.github.hokutomc.lib.scala.item.recipe.HT_ScalaCraftingRecipeBuilder
import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.fml.common.IFuelHandler
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry


/**
 * Created by user on 2015/03/17.
 */
object HT_ScalaUtils {
  def creativeTab(modid: String, name: String, item: => Item) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = item
  }

  def creativeTabFromStack(modid: String, name: String, itemStack: => ItemStack) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = itemStack.getItem

    override def getIconItemStack: ItemStack = itemStack
  }

  def recipe = new HT_ScalaCraftingRecipeBuilder

  def registerItemMeshDefinition(item: Item)(func: ItemStack => ModelResourceLocation): Unit = {
    HT_RenderUtil.getItemModelMesher.register(item, new ItemMeshDefinition {
      override def getModelLocation(stack: ItemStack): ModelResourceLocation = func(stack)
    })
  }

  def config(event: FMLPreInitializationEvent)(file: File = event.getSuggestedConfigurationFile)(func: HT_Config => Unit): HT_Config = {
    new HT_Config(file) {
      override protected def configure(): Unit = func(this)
    }
  }

  def registerFuelHandler(func: ItemStack => Int): Unit = {
    GameRegistry.registerFuelHandler(new IFuelHandler {
      override def getBurnTime(fuel: ItemStack): Int = func(fuel)
    })
  }


}
