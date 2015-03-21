package com.github.hokutomc.lib.scala.nbt

/**
 * Created by user on 2015/03/18.
 */
class HT_NBTKey(val string: String) extends AnyVal {

  def :=[A <: HT_T_NBTCompound[_], T <: HT_T_NBTValue[T]](nbtValue: T)(a: A) = {
    a.apply[T](string, nbtValue)
  }
}
