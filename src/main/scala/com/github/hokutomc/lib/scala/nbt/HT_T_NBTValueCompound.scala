package com.github.hokutomc.lib.scala.nbt


/**
 * Created by user on 2015/02/26.
 */
object HT_T_NBTValueCompound {
  trait HT_T_NBTValueCompound[T] extends Any with HT_T_NBTValue[T]{

    def writeToNBT(compound: HT_RichNBTTagCompound)

    def setToNBT (nbt: HT_RichNBTTagCompound, key: String) = {
      writeToNBT(nbt)
    }

    def readFromNBT(compound: HT_RichNBTTagCompound): T

    def getFromNBT (nbt: HT_RichNBTTagCompound, key: String) : T = {
      readFromNBT(nbt)
    }
  }
}

