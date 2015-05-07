package com.github.hokutomc.lib.scala

import java.io.File

import com.github.hokutomc.lib.client.gui.HT_GuiAction
import com.github.hokutomc.lib.client.render.HT_RenderUtil
import com.github.hokutomc.lib.common.config.HT_Config
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.block.Block
import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.world.World
import net.minecraftforge.fml.common.IFuelHandler
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.{ShapedOreRecipe, ShapelessOreRecipe}

/**
 * Created by user on 2015/03/17.
 */
object HT_ScalaInitUtils {
  def creativeTab(modid: String, name: String, item: => Item) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = item
  }

  def creativeTabFromStack(modid: String, name: String, itemStack: => ItemStack) = new CreativeTabs(modid + "." + name) {
    override def getTabIconItem: Item = itemStack.getItem

    override def getIconItemStack: ItemStack = itemStack
  }

  def registerItemMeshDefinition(item: Item)(func: ItemStack => ModelResourceLocation): Unit = {
    HT_RenderUtil.getItemModelMesher.register(item, new ItemMeshDefinition {
      override def getModelLocation(stack: ItemStack): ModelResourceLocation = func(stack)
    })
  }

  def guiAction[A](func: (EntityPlayer, World, Int, Int, Int) => A): HT_GuiAction[A] = {
    new HT_GuiAction[A] {
      override def get(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): A = func(player, world, x, y, z)
    }
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

  def shapedRecipe(result: ItemStack, grid: String*)(params: (Char, ItemStack)*) =
    GameRegistry.addRecipe(result, grid ++ flattenMap(params): _*)

  def shapelessRecipe(result: ItemStack)(resources: ItemStack*) =
    GameRegistry.addShapelessRecipe(result, resources: _*)

  def shapedOreRecipe(result: ItemStack, grid: String*)(params: (Char, ItemStack)*)(ores: (Char, String)*) =
    GameRegistry.addRecipe(new ShapedOreRecipe(result, grid ++ flattenMap(params) ++ flattenMap(ores): _*))

  def shapelessOreRecipe(result: ItemStack)(resources: ItemStack*)(ores: String*) =
    GameRegistry.addRecipe(new ShapelessOreRecipe(result, resources ++ ores: _*))

  implicit def itemAsStack(item: Item): ItemStack = new ItemStack(item)

  implicit def blockAsStack(block: Block): ItemStack = new ItemStack(block)

  implicit def blockAsItem(block: Block): Item = block.item

  def flattenMap[A, B](params: Seq[(A, B)]): Seq[AnyRef] = (for ((a, b) <- params) yield Seq(a, b)).flatten map {
    _.asInstanceOf[Object]
  }
}
