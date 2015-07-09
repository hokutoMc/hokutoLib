package com.github.hokutomc.lib.scala
package item


/**
 * Created by user on 2015/07/01.
 */
object HT_ItemOrBlock {

  case class OfItem(item: Item) extends HT_ItemOrBlock

  case class OfBlock(block: Block) extends HT_ItemOrBlock

}

trait HT_ItemOrBlock
