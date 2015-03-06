package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.block.HT_ContainerBlock
import com.github.hokutomc.lib.client.gui.HT_GuiAction
import com.github.hokutomc.lib.item.HT_ItemStackBuilder
import com.github.hokutomc.lib.item.recipe.HT_CraftingRecipeBuilder
import com.github.hokutomc.lib.scala.entity.HT_RichEntity.HT_RichEntity
import com.github.hokutomc.lib.scala.entity.{HT_RichEntity, HT_RichPlayer}
import com.github.hokutomc.lib.scala.item.recipe.{HT_ScalaCraftingRecipeBuilder, HT_ScalaCraftingRecipeBuilder$}
import com.github.hokutomc.lib.scala.nbt.HT_T_NBTValue.HT_T_NBTValue
import com.github.hokutomc.lib.scala.nbt.{HT_RichNBTTagCompound, HT_RichNBTTagList}
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{EnumChatFormatting, Vec3}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.event.entity.player.PlayerEvent

import scala.reflect.ClassTag

/**
 * Created by user on 2015/02/26.
 */
object HT_ScalaConversion {
  implicit def wrapItemStack (stack : ItemStack) : HT_RichItemStack = HT_RichItemStack(stack)

  /**
   *
   * @return null if this is empty stack
   */
  implicit def unwrapItemStack (stack: HT_RichItemStack) : ItemStack = stack.stack

  implicit def wrapNBTTagComp (nbtTagCompound: NBTTagCompound) : HT_RichNBTTagCompound = new HT_RichNBTTagCompound(nbtTagCompound)

  implicit def unwrapNBTTagComp (hT_RichNBTTagCompound: HT_RichNBTTagCompound): NBTTagCompound = hT_RichNBTTagCompound.wrapped

  implicit def wrapNBTTagList (nbtTagList: NBTTagList) : HT_RichNBTTagList = new HT_RichNBTTagList(nbtTagList)
  
  implicit def ChatFormatToString (chatFormat: EnumChatFormatting) : String = chatFormat.toString

  implicit def StringToLocalizer (string: String) : HT_Localizer = HT_Localizer(string)

  implicit def wrapVec3 (vec3: Vec3) : HT_Vec3 = new HT_Vec3(vec3)

  implicit def tupleToVec3 (tuple3: (Double, Double, Double)) : HT_Vec3 = Vec3.createVectorHelper(tuple3._1, tuple3._2, tuple3._3)

  implicit def getEntityPosVec (entity: Entity) : HT_Vec3 = (entity.posX, entity.posY, entity.posZ)

  implicit def getTilePosVec (tileEntity: TileEntity) : HT_Vec3 = tupleToVec3(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord)

  implicit def wrapPlayer (entityPlayer: EntityPlayer) : HT_RichPlayer = new HT_RichPlayer(entityPlayer)

  implicit def unwrapPlayer (player: HT_RichPlayer) : EntityPlayer = player.player

  implicit def wrapInventory (inventory: IInventory) : HT_RichInventory = new HT_RichInventory(inventory)

  implicit def unwrapInventory (inventory: HT_RichInventory) : IInventory = inventory.inventory

  implicit def wrapEntity (entity: Entity) : HT_RichEntity = new HT_RichEntity.HT_RichEntityImpl(entity)

  implicit def unwrapEntity (entity: HT_RichEntity) : Entity = entity.entity

  implicit def itemToBuilder (item: Item) : HT_ItemStackBuilder[_] = HT_ItemStackBuilder.start(item)

  implicit def blockToBuilder (block: Block) : HT_ItemStackBuilder[_] = HT_ItemStackBuilder.apply(block)

  implicit class BlockToItem (val block: Block) extends AnyVal {
    def getItem : Option[Item] = Option(Item.getItemFromBlock(block))

    def getItemAs [T <: Item] (implicit classTag: ClassTag[T]) = getItem map { case i: T => Some(i) case _ => None}
  }

  implicit class WrapItemStackBuilder (val itemStackBuilder: HT_ItemStackBuilder[_]) extends AnyVal {
    def unary_! : HT_RichNBTTagCompound = itemStackBuilder.getNBTTag

    def apply [T <: HT_T_NBTValue[T]] (key: String, nbtValue: T): T = {
      nbtValue.getFromNBT(!this, key)
    }

    def update [T <: HT_T_NBTValue[T]](key: String, nbtValue: HT_T_NBTValue[T]): WrapItemStackBuilder = {
      nbtValue.setToNBT(!this, key)
      this
    }
  }

  implicit class WrapPlayerEvent (val event: PlayerEvent) extends AnyVal {
    def unary_+ : HT_RichPlayer = event entityPlayer

    def unary_~ : HT_RichItemStack = ~(+this)

    def unary_- : World = (+this) worldObj
  }

  implicit def funcToGuiAction [T](func : (EntityPlayer, World, Int, Int, Int) =>  T): HT_GuiAction[T] = {
    new HT_GuiAction[T] {
      override def get(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): T = func(player, world, x, y, z)
    }
  }

  implicit class WrapOption[T] (val option: Option[T]) extends AnyVal {
    def safe (function: T => Unit) = option match {case Some(v) => function(v) case _ =>}
  }

  implicit class WrapBlockContainer[T <: TileEntity] (val containerBlock: HT_ContainerBlock[T]) extends AnyVal {
    def tileEntity(world: IBlockAccess, x: Int, y: Int, z:Int)(func: T => Unit)(implicit classTag: ClassTag[T]) = {
      world.getTileEntity(x, y, z) match {
        case t: T => func(t)
        case _=>
      }
    }
  }
}
