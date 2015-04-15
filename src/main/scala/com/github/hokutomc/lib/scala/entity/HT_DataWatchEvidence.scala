package com.github.hokutomc.lib.scala.entity

import net.minecraft.entity.DataWatcher
import net.minecraft.item.ItemStack

object HT_DataWatchEvidence {

  object EvByte extends HT_DataWatchEvidence[Byte] {
    override def get(dataWatcher: DataWatcher, id: Int): Byte = dataWatcher.getWatchableObjectByte(id)
  }

  object EvShort extends HT_DataWatchEvidence[Short] {
    override def get(dataWatcher: DataWatcher, id: Int): Short = dataWatcher.getWatchableObjectShort(id)
  }

  object EvInt extends HT_DataWatchEvidence[Int] {
    override def get(dataWatcher: DataWatcher, id: Int): Int = dataWatcher.getWatchableObjectInt(id)
  }

  object EvFloat extends HT_DataWatchEvidence[Float] {
    override def get(dataWatcher: DataWatcher, id: Int): Float = dataWatcher.getWatchableObjectFloat(id)
  }

  object EvString extends HT_DataWatchEvidence[String] {
    override def get(dataWatcher: DataWatcher, id: Int): String = dataWatcher.getWatchableObjectString(id)
  }

  object EvItemStack extends HT_DataWatchEvidence[ItemStack] {
    override def get(dataWatcher: DataWatcher, id: Int): ItemStack = dataWatcher.getWatchableObjectItemStack(id)
  }

}

/**
 * Created by user on 2015/04/13.
 */
trait HT_DataWatchEvidence[E] {
  def init(dataWatcher: DataWatcher, id: Int, default: E): Unit = dataWatcher.addObject(id, default)

  def get(dataWatcher: DataWatcher, id: Int): E

  def set(dataWatcher: DataWatcher, id: Int, e: E): Unit = dataWatcher.updateObject(id, e)
}
