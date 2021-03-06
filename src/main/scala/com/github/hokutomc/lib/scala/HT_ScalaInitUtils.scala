package com.github.hokutomc.lib.scala

import java.io.File

import com.github.hokutomc.lib.HT_Registries
import com.github.hokutomc.lib.client.gui.HT_GuiAction
import com.github.hokutomc.lib.client.render.HT_RenderUtil
import com.github.hokutomc.lib.common.config.HT_Config
import com.github.hokutomc.lib.scala.HT_Predef._
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.item.HT_ItemOrBlockOrStack
import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.fml.common.IFuelHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.{ShapedOreRecipe, ShapelessOreRecipe}

import scala.reflect.ClassTag

/**
 * Created by user on 2015/03/17.
 */
object HT_ScalaInitUtils {
  type Mod = net.minecraftforge.fml.common.Mod
  type EventHandler = net.minecraftforge.fml.common.Mod.EventHandler
  type ModInstance = net.minecraftforge.fml.common.Mod.Instance

  type PreInit = FMLPreInitializationEvent
  type Init = FMLInitializationEvent
  type PostInit = FMLPostInitializationEvent

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

  def registerFuelHandler(pf: PartialFunction[ItemStack, Int]): Unit = {
    GameRegistry.registerFuelHandler(new IFuelHandler {
      override def getBurnTime(fuel: ItemStack): Int = if (pf.isDefinedAt(fuel)) pf(fuel) else 0
    })
  }

  def registerTileEntity[A <: TileEntity : ClassTag](name: String) =
    HT_Registries.registerCommonTileEntity(HT_Predef.classFromTag[A], name)

  def shapedRecipe(result: ItemStack, grid: String*)(params: (Char, HT_ItemOrBlockOrStack)*) =
    GameRegistry.addRecipe(result, grid ++ flattenMap(params): _*)

  def shapelessRecipe(result: ItemStack)(resources: HT_ItemOrBlockOrStack*) =
    GameRegistry.addShapelessRecipe(result, resources.map { case r: HT_ItemOrBlockOrStack => r.any case r => r.asInstanceOf[AnyRef] }: _*)

  def shapedOreRecipe(result: ItemStack, grid: String*)(params: (Char, HT_ItemOrBlockOrStack)*)(ores: (Char, String)*) =
    GameRegistry.addRecipe(new ShapedOreRecipe(result, grid ++ flattenMap(params) ++ flattenMap(ores): _*))

  def shapelessOreRecipe(result: ItemStack)(resources: HT_ItemOrBlockOrStack*)(ores: String*) =
    GameRegistry.addRecipe(new ShapelessOreRecipe(result,
      resources.map { case r: HT_ItemOrBlockOrStack => r.any case r => r.asInstanceOf[AnyRef] } ++ ores: _*))

  implicit def itemAsStack(item: Item): ItemStack = new ItemStack(item)

  implicit def blockAsStack(block: Block): ItemStack = new ItemStack(block)

  implicit def blockAsItem(block: Block): Item = block.item

  def flattenMap[A, B](params: Seq[(A, B)]): Seq[AnyRef] = (for ((a, b) <- params) yield {
    Seq(a, b match { case b: HT_ItemOrBlockOrStack => b.any case x => x })
  }).flatten map {
    _.asInstanceOf[Object]
  }

  implicit def either3Item(item: Item): HT_ItemOrBlockOrStack = HT_ItemOrBlockOrStack.OfItem(item)

  implicit def either3Block(block: Block): HT_ItemOrBlockOrStack = HT_ItemOrBlockOrStack.OfBlock(block)

  implicit def either3ItemStack(stack: ItemStack): HT_ItemOrBlockOrStack = HT_ItemOrBlockOrStack.OfItemStack(stack)
}
