package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.item.matcher.{HT_ItemMatcher, HT_ItemMatcherItem, HT_ItemMatcherOre}
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.HT_NBTEvidence
import net.minecraft.init.Items
import net.minecraft.item.{Item, ItemStack}

import scala.reflect.ClassTag

/**
 * Created by user on 2015/03/28.
 */
object HT_ItemStackPattern {

  val pattern = HT_ItemStackPattern(Items.apple)
  val pat2 = HT_ItemStackPattern.ofOre("ore")

  def unapply[A <: Item, Op <: HT_ItemStackOp[Op]](itemStack: Op)(implicit classTag: ClassTag[A]): Option[(A, Int)] = itemStack match {
    case i: HT_RichItemStack if i isEmpty => None
    case _ => itemStack.damage flatMap { d => itemStack.getItemAs[A] map { i => (i, d) } }
  }

  def ofType[I](implicit classTag: ClassTag[I]) = new HT_ItemStackPatternType[I]

  def apply[I <: Item](item: I, damage: Option[Int] = None, eqOrMore: Int = 1)(implicit classTag: ClassTag[I]) = {
    new PatternWrapperItem(damage match { case Some(d) => new HT_ItemMatcherItem(item, d) case _ => new HT_ItemMatcherItem(item) })
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
  def andThenNBT[A](key: String, a: Option[A] => Boolean)(implicit ev: HT_NBTEvidence[A]): HT_ItemMatcher =
    self.andThen(new HT_ItemStackPatternNBT(key, a)(ev))

}

class HT_ItemStackPatternNBT[A](val key: String, val p: Option[A] => Boolean)(implicit val ev: HT_NBTEvidence[A])
  extends HT_ItemMatcher with HT_RichItemMatcher {
  override protected def check(itemStack: ItemStack): Boolean = p(itemStack.apply(key)(ev))

  override val self: HT_ItemMatcher = this
}

class HT_ItemStackPatternType[I](implicit val classTag: ClassTag[I]) extends HT_ItemMatcher with HT_RichItemMatcher {
  override def check(itemStack: ItemStack): Boolean = itemStack.getItem match {
    case _: I => true
    case _ => false
  }

  def unapply(itemStack: ItemStack): Option[I] = if (matches(itemStack)) itemStack.getItemAs[I] else None

  override val self: HT_ItemMatcher = this
}

class PatternWrapperItem[I <: Item](val matcher: HT_ItemMatcherItem)(implicit val classTag: ClassTag[I]) extends HT_RichItemMatcher {
  def unapply(itemStack: ItemStack): Option[(I, Int)] = if (matcher.matches(itemStack)) Some((matcher.item.asInstanceOf[I], matcher.damage)) else None

  override val self: HT_ItemMatcher = matcher
}

class PatternWrapperOre(val matcherOre: HT_ItemMatcherOre) extends HT_RichItemMatcher {
  def unapply(itemStack: ItemStack): Option[(ItemStack, Item)] = if (matcherOre.matches(itemStack)) Some((itemStack, itemStack.getItem)) else None

  override val self: HT_ItemMatcher = matcherOre
}