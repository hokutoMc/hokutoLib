package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.scala.block.states.HT_RichBlockState
import com.github.hokutomc.lib.scala.block.{HT_BlockPos, HT_RichBlock}
import com.github.hokutomc.lib.scala.entity.{HT_RichDataWatcher, HT_RichEntity, HT_RichPlayer}
import com.github.hokutomc.lib.scala.event.HT_RichPlayerEvent
import com.github.hokutomc.lib.scala.item.HT_RichItemStack
import com.github.hokutomc.lib.scala.nbt.{HT_RichNBTTagCompound, HT_RichNBTTagList}
import com.github.hokutomc.lib.scala.util.{HT_RichEnumDyeColor, HT_Vec3}
import com.github.hokutomc.lib.scala.world.HT_World
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.DataWatcher
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.EnumDyeColor
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util._
import net.minecraft.world.World
import net.minecraftforge.event.entity.player.PlayerEvent

/**
 * Created by user on 2015/02/26.
 */
object HT_ScalaConversion extends HT_ScalaConversion

trait HT_ScalaConversion {
  implicit def wrapItemStack(stack: ItemStack): HT_RichItemStack = HT_RichItemStack(stack)

  /**
   *
   * @return null if this is empty stack
   */
  implicit def unwrapItemStack(stack: HT_RichItemStack): ItemStack = stack.unwrap

  implicit def wrapNBTTagComp (nbtTagCompound: NBTTagCompound) : HT_RichNBTTagCompound = new HT_RichNBTTagCompound(nbtTagCompound)

  implicit def unwrapNBTTagComp (hT_RichNBTTagCompound: HT_RichNBTTagCompound): NBTTagCompound = hT_RichNBTTagCompound.wrapped

  implicit def unwrapNBTTagList(ht_RichNBTTAgList: HT_RichNBTTagList[_]): NBTTagList = ht_RichNBTTAgList.wrapped

  implicit def ChatFormatToString (chatFormat: EnumChatFormatting) : String = chatFormat.toString

  implicit def wrapString(string: String): HT_RichString = new HT_RichString(string)

  implicit def unwrapString(string: HT_RichString): String = string.string

  implicit def wrapVec3 (vec3: Vec3) : HT_Vec3 = new HT_Vec3(vec3)

  implicit def tupleToVec3(tuple3: (Double, Double, Double)): HT_Vec3 = new Vec3(tuple3._1, tuple3._2, tuple3._3)

  implicit def Vec3i2Vec3(vec3i: Vec3i): Vec3 = new Vec3(vec3i.getX, vec3i.getY, vec3i.getZ)

  implicit def Vec32Vec3i(vec3: Vec3): Vec3i = new Vec3i(vec3.x, vec3.y, vec3.z)

  implicit def wrapVec3i(blockPos: BlockPos): HT_BlockPos = new HT_BlockPos(blockPos)

  implicit def getEntityPosVec (entity: Entity) : HT_Vec3 = (entity.posX, entity.posY, entity.posZ)

  implicit def getTilePosVec(tileEntity: TileEntity): HT_Vec3 = Vec3i2Vec3(tileEntity.getPos)

  implicit def wrapPlayer (entityPlayer: EntityPlayer) : HT_RichPlayer = new HT_RichPlayer(entityPlayer)

  implicit def unwrapPlayer (player: HT_RichPlayer) : EntityPlayer = player.player

  implicit def wrapDataWatcher(dataWatcher: DataWatcher): HT_RichDataWatcher = new HT_RichDataWatcher(dataWatcher)

  implicit def unwrapDataWatcher(dataWatcher: HT_RichDataWatcher): DataWatcher = dataWatcher.wrapped

  implicit def wrapInventory (inventory: IInventory) : HT_RichInventory = new HT_RichInventory(inventory)

  implicit def unwrapInventory (inventory: HT_RichInventory) : IInventory = inventory.inventory

  implicit def wrapEntity(entity: Entity): HT_RichEntity = new HT_RichEntity.Impl(entity)

  implicit def unwrapEntity (entity: HT_RichEntity) : Entity = entity.entity

  implicit def wrapWorld(world: World): HT_World = new HT_World(world)

  implicit def wrapBlock(block: Block): HT_RichBlock = new HT_RichBlock(block)

  implicit def wrapBlockEvent(event: PlayerEvent): HT_RichPlayerEvent = new HT_RichPlayerEvent(event)

  implicit def wrapBlockState(blockStates: IBlockState): HT_RichBlockState = new HT_RichBlockState(blockStates)

  implicit def unwrapBlockState(richBlockState: HT_RichBlockState): IBlockState = richBlockState.blockState

  implicit def wrapColor(dyeColor: EnumDyeColor): HT_RichEnumDyeColor = HT_RichEnumDyeColor(dyeColor)

  implicit def unwrapColor(dyeColor: HT_RichEnumDyeColor): EnumDyeColor = dyeColor.dyeColor

  implicit def asMessage(string: String): ChatComponentText = new ChatComponentText(string)
}
