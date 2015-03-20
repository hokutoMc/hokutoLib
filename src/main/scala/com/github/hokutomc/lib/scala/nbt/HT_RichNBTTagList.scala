package com.github.hokutomc.lib.scala.nbt

import net.minecraft.nbt.{NBTBase, NBTTagList}

/**
 * Created by user on 2015/02/26.
 */
class HT_RichNBTTagList(val wrapped: NBTTagList) extends AnyVal{
  def +=(nbtBase: NBTBase): HT_RichNBTTagList = {
    wrapped.appendTag(nbtBase)
    this
  }
}
