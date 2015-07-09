package com.github.hokutomc.lib.scala
package block


import scala.reflect.ClassTag

/**
 * Created by user on 2015/07/07.
 */
class HT_RichBlock(val block: Block) extends AnyVal {
  def item: Item = net.minecraft.item.Item.getItemFromBlock(block)

  def getItemAs[T <: Item](implicit classTag: ClassTag[T]) = item match {
    case i: T => Some(i)
    case _ => None
  }
}


