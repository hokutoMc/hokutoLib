package com.github.hokutomc.lib.scala
package item


/**
 * Created by user on 2015/07/01.
 */
object HT_ItemOrBlockOrStack {

  case class OfItem(any: Item) extends HT_ItemOrBlockOrStack

  case class OfBlock(any: Block) extends HT_ItemOrBlockOrStack

  case class OfItemStack(any: ItemStack) extends HT_ItemOrBlockOrStack

}

trait HT_ItemOrBlockOrStack {
  def any: AnyRef
}
