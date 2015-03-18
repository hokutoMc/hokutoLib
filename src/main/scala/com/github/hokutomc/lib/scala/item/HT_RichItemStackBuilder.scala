package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.item.HT_ItemStackBuilder
import net.minecraft.item.ItemStack

/**
 * Created by user on 2015/03/18.
 */
class HT_RichItemStackBuilder(val itemStackBuilder: HT_ItemStackBuilder[_]) extends AnyVal with HT_ItemStackOp[HT_RichItemStackBuilder] {
  override def stack: ItemStack = itemStackBuilder.m_template

  override def stackSize_=(size: Int): HT_RichItemStackBuilder = {
    itemStackBuilder.size(size)
    this
  }

  override def damage_=(value: Int): Unit = itemStackBuilder.damage(value)
}
