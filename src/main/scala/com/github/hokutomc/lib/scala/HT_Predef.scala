package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.item.{HT_ItemBuilder, HT_ItemCondition}
import com.github.hokutomc.lib.nbt.HT_NBTEvidence
import com.github.hokutomc.lib.scala.entity.HT_DataWatchEvidence
import com.github.hokutomc.lib.scala.item.HT_ItemOrBlock.{OfBlock, OfItem}
import com.github.hokutomc.lib.scala.item.{HT_ItemOrBlock, HT_ItemStackPattern}
import com.github.hokutomc.lib.scala.nbt.HT_ScalaNBTEvidence.EvItemStackArray
import com.github.hokutomc.lib.scala.nbt.{HT_RichNBTTagCompound, HT_ScalaNBTEvidence}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{AxisAlignedBB, BlockPos}
import net.minecraftforge.common.config.Property
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

/**
 * Created by user on 2015/04/12.
 */
object HT_Predef {
  type JavaList[A] = java.util.List[A]

  type ForgeProp = Property

  type Item = net.minecraft.item.Item
  type Block = net.minecraft.block.Block
  type ItemStack = net.minecraft.item.ItemStack
  type Entity = net.minecraft.entity.Entity

  type Material = net.minecraft.block.material.Material

  type ItemOrBlock = HT_ItemOrBlock
  type EntityOrTE = Either[Entity, TileEntity]

  type Mod = net.minecraftforge.fml.common.Mod
  type EventHandler = net.minecraftforge.fml.common.Mod.EventHandler
  type ModInstance = net.minecraftforge.fml.common.Mod.Instance

  type PreInit = FMLPreInitializationEvent
  type Init = FMLInitializationEvent
  type PostInit = FMLPostInitializationEvent

  type TagComp = NBTTagCompound

  type RichTagComp = HT_RichNBTTagCompound

  type AABB = AxisAlignedBB

  val ItemPattern = HT_ItemStackPattern

  implicit val evBool: HT_NBTEvidence[Boolean] = HT_ScalaNBTEvidence.EvBoolean
  implicit val evByte: HT_NBTEvidence[Byte] = HT_ScalaNBTEvidence.EvByte
  implicit val evByteArr: HT_NBTEvidence[Array[Byte]] = HT_ScalaNBTEvidence.EvByteArray
  implicit val evInt: HT_NBTEvidence[Int] = HT_ScalaNBTEvidence.EvInt
  implicit val evIntArr: HT_NBTEvidence[Array[Int]] = HT_ScalaNBTEvidence.EvIntArray
  implicit val evStr: HT_NBTEvidence[String] = HT_ScalaNBTEvidence.EvString
  implicit val evFloat: HT_NBTEvidence[Float] = HT_ScalaNBTEvidence.EvFloat
  implicit val evDouble: HT_NBTEvidence[Double] = HT_ScalaNBTEvidence.EvDouble
  implicit val evItemStack: HT_NBTEvidence[ItemStack] = HT_ScalaNBTEvidence.EvItemStack
  implicit val evBlockPos: HT_NBTEvidence[BlockPos] = HT_ScalaNBTEvidence.EvBlockPos

  //TODO: should add evidences for blockpos and enums and rotations

  lazy val evItemStackArray = Seq.tabulate(40) { i => new EvItemStackArray(i) }

  implicit val evDwByte: HT_DataWatchEvidence[Byte] = HT_DataWatchEvidence.EvByte
  implicit val evDwShort: HT_DataWatchEvidence[Short] = HT_DataWatchEvidence.EvShort
  implicit val evDwInt: HT_DataWatchEvidence[Int] = HT_DataWatchEvidence.EvInt
  implicit val evDwFloat: HT_DataWatchEvidence[Float] = HT_DataWatchEvidence.EvFloat
  implicit val evDwString: HT_DataWatchEvidence[String] = HT_DataWatchEvidence.EvString
  implicit val evDwItemStack: HT_DataWatchEvidence[ItemStack] = HT_DataWatchEvidence.EvItemStack

  def builder(itemOrBlock: ItemOrBlock, damage: Int = 0): HT_ItemCondition = itemOrBlock match {
    case OfItem(i) => HT_ItemCondition.builder(i).checkDamage(damage).build()
    case OfBlock(i) => HT_ItemCondition.builder(i).checkDamage(damage).build()
  }

  def builder(f: => ItemStack): HT_ItemBuilder = new HT_ItemBuilder {
    override def createStack(): ItemStack = f
  }

  def ItemStack(itemOrBlock: ItemOrBlock, size: Int = 1, damage: Int = 0): ItemStack = itemOrBlock match {
    case OfItem(i) => new ItemStack(i, size, damage)
    case OfBlock(b) => new ItemStack(b, size, damage)
  }

  type LogManager = org.apache.logging.log4j.LogManager
  type Logger = org.apache.logging.log4j.Logger

  def doWileNone[A](functions: (() => Option[A])*): Option[A] = {
    var prev: Option[A] = None
    var index = 0
    while (index < functions.length && prev == None) {
      prev = functions(index).apply()
      index += 1
    }
    prev
  }

  def mutate[A](a: A)(f: A => Any): A = {
    f(a); a
  }

  implicit def eitherBlock(block: Block): ItemOrBlock = OfBlock(block)

  implicit def eitherItem(item: Item): ItemOrBlock = OfItem(item)
}
