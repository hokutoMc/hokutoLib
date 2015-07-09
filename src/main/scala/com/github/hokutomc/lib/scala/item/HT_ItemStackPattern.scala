package com.github.hokutomc.lib.scala
package item

import com.github.hokutomc.lib.item.matcher.{HT_ItemMatcher, HT_ItemMatcherItem, HT_ItemMatcherOre}
import com.github.hokutomc.lib.nbt.HT_NBTEvidence
import com.github.hokutomc.lib.scala.HT_ScalaConversion._

import scala.reflect.ClassTag

/**
 * Created by user on 2015/03/28.
 */
object HT_ItemStackPattern {

  /**
   * extract into item and damage
   * @param itemStack
   * @return
   */
  def unapply(itemStack: ItemStack): Option[(Item, Int)] =
    if (itemStack isEmpty) None else Some(itemStack.getItem, itemStack.damage)

  def unapply(itemStack: HT_RichItemStack): Option[(Item, Int)] = unapply(itemStack.unwrap)

  object PatternItemBlock {
    def unapply(item: Item): Option[Block] = Option(net.minecraft.block.Block.getBlockFromItem(item))
  }

  def ofType[I: ClassTag] = new HT_ItemStackPatternType[I]

  def apply[I <: Item : ClassTag](item: I, damage: Option[Int] = None, eqOrMore: Int = 1) = {
    new PatternWrapperItem(damage match {
      case Some(d) => new HT_ItemMatcherItem(item, d)
      case _ => new HT_ItemMatcherItem(item)
    }
    )
  }

  def ofOre(ore: String, eqOrMore: Int = 1) = new PatternWrapperOre(new HT_ItemMatcherOre(ore))
}

trait HT_RichItemMatcher {
  val self: HT_ItemMatcher

  /**
   * This method creates new instance that checks NBT tag.
   * @param key
   * @param a
   * @param ev
   * @tparam A
   * @return new instance
   */
  def andThenNBT[A: HT_NBTEvidence](key: String, a: Option[A] => Boolean): HT_ItemMatcher =
    self.andThen(new HT_ItemStackPatternNBT(key, a))

}

class HT_ItemStackPatternNBT[A: HT_NBTEvidence](val key: String, val p: Option[A] => Boolean)
  extends HT_ItemMatcher with HT_RichItemMatcher {
  override protected def check(itemStack: ItemStack): Boolean = p(itemStack.apply[A](key))

  override val self: HT_ItemMatcher = this
}

class HT_ItemStackPatternType[I](implicit val classTag: ClassTag[I]) extends HT_ItemMatcher with HT_RichItemMatcher {
  override def check(itemStack: ItemStack): Boolean = itemStack.getItem match {
    case _: I => true
    case _ => false
  }

  def unapply(itemStack: ItemStack): Option[I] = if (matches(itemStack)) itemStack.isItemInstance[Item with I] else None

  override val self: HT_ItemMatcher = this
}

class PatternWrapperItem[I <: Item : ClassTag](val matcher: HT_ItemMatcherItem) extends HT_RichItemMatcher {
  def unapply(itemStack: ItemStack): Option[(I, Int)] = if (matcher.matches(itemStack)) Some((matcher.item.asInstanceOf[I], matcher.damage)) else None

  override val self: HT_ItemMatcher = matcher
}

class PatternWrapperOre(val matcherOre: HT_ItemMatcherOre) extends HT_RichItemMatcher {
  def unapply(itemStack: ItemStack): Option[(ItemStack, Item)] = if (matcherOre.matches(itemStack)) Some((itemStack, itemStack.getItem)) else None

  override val self: HT_ItemMatcher = matcherOre
}