package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.item.HT_ItemStackBuilder.Raw
import com.github.hokutomc.lib.scala.entity.HT_DataWatchEvidence
import com.github.hokutomc.lib.scala.nbt.HT_NBTEvidence.EvItemStackArray
import com.github.hokutomc.lib.scala.nbt.{HT_NBTEvidence, HT_RichNBTTagCompound}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

/**
 * Created by user on 2015/04/12.
 */
object HT_Predef {
  type JList[A] = java.util.List[A]

  type Item = net.minecraft.item.Item
  type Block = net.minecraft.block.Block
  type ItemStack = net.minecraft.item.ItemStack
  type Entity = net.minecraft.entity.Entity

  type Mod = net.minecraftforge.fml.common.Mod
  type EventHandler = net.minecraftforge.fml.common.Mod.EventHandler
  type ModInstance = net.minecraftforge.fml.common.Mod.Instance

  type PreInit = FMLPreInitializationEvent
  type Init = FMLInitializationEvent
  type PostInit = FMLPostInitializationEvent

  type TagComp = NBTTagCompound

  type RichTagComp = HT_RichNBTTagCompound

  implicit val evBool: HT_NBTEvidence[Boolean] = HT_NBTEvidence.EvBoolean
  implicit val evByte: HT_NBTEvidence[Byte] = HT_NBTEvidence.EvByte
  implicit val evByteArr: HT_NBTEvidence[Array[Byte]] = HT_NBTEvidence.EvByteArray
  implicit val evInt: HT_NBTEvidence[Int] = HT_NBTEvidence.EvInt
  implicit val evIntArr: HT_NBTEvidence[Array[Int]] = HT_NBTEvidence.EvIntArray
  implicit val evStr: HT_NBTEvidence[String] = HT_NBTEvidence.EvString
  implicit val evFloat: HT_NBTEvidence[Float] = HT_NBTEvidence.EvFloat
  implicit val evDouble: HT_NBTEvidence[Double] = HT_NBTEvidence.EvDouble
  implicit val evItemStack: HT_NBTEvidence[ItemStack] = HT_NBTEvidence.EvItemStack

  //TODO: should add evidences for blockpos and enums and rotations

  lazy val evItemStackArray = Seq.tabulate(40) { i => new EvItemStackArray(i) }

  implicit val evDwByte: HT_DataWatchEvidence[Byte] = HT_DataWatchEvidence.EvByte
  implicit val evDwShort: HT_DataWatchEvidence[Short] = HT_DataWatchEvidence.EvShort
  implicit val evDwInt: HT_DataWatchEvidence[Int] = HT_DataWatchEvidence.EvInt
  implicit val evDwFloat: HT_DataWatchEvidence[Float] = HT_DataWatchEvidence.EvFloat
  implicit val evDwString: HT_DataWatchEvidence[String] = HT_DataWatchEvidence.EvString
  implicit val evDwItemStack: HT_DataWatchEvidence[ItemStack] = HT_DataWatchEvidence.EvItemStack

  def builder(item: Item, size: Int = 1, damage: Int = 0): Raw = new Raw(item).size(size).damage(damage)

  def ItemStack(item: Item, size: Int = 1, damage: Int = 0): ItemStack = new ItemStack(item, size, damage)

  type LogManager = org.apache.logging.log4j.LogManager
  type Logger = org.apache.logging.log4j.Logger

  def doWileNone[A](functions: (() => Option[A])*): Option[A] = {
    var prev: Option[A] = None
    for (f <- functions) {
      prev match {
        case Some(v) => return Some(v)
        case _ => prev = f()
      }
    }
    prev
  }
}
