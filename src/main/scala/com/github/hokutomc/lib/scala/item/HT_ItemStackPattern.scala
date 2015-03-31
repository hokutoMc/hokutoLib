package com.github.hokutomc.lib.scala.item

import com.github.hokutomc.lib.scala.HT_ScalaConversion._
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

  def ofType[I](eqOrMore: Int = 1)(implicit classTag: ClassTag[I]) = new HT_ItemStackPatternType[I](eqOrMore)

  def apply[I <: Item](item: I, damage: Option[Int] = None, eqOrMore: Int = 1)(implicit classTag: ClassTag[I]) = new HT_ItemStackPatternItem[I](item, damage, eqOrMore)

  def ofOre(ore: String, eqOrMore: Int = 1) = new HT_ItemStackPatternOre(ore, eqOrMore)
}


abstract class HT_ItemStackPattern(val eqOrMore: Int = 1) {

  protected def satisfy(itemStack: ItemStack): Boolean

  def matches(itemStack: ItemStack): Boolean = !itemStack.isEmpty && itemStack.stackSize >= eqOrMore && satisfy(itemStack)
}

class HT_ItemStackPatternItem[I <: Item](val item: I, val damage: Option[Int] = None, override val eqOrMore: Int) extends HT_ItemStackPattern(eqOrMore) {
  override def satisfy(itemStack: ItemStack): Boolean = damage match {
    case Some(d) => itemStack.getItem == item && itemStack.getItemDamage == d
    case _ => itemStack.getItem == item
  }

  def unapply(itemStack: ItemStack): Option[I] = if (matches(itemStack)) Some(item) else None
}

class HT_ItemStackPatternType[I](override val eqOrMore: Int)(implicit val classTag: ClassTag[I]) extends HT_ItemStackPattern(eqOrMore) {
  override def satisfy(itemStack: ItemStack): Boolean = itemStack.getItem match {
    case _: I => true
    case _ => false
  }

  def unapply(itemStack: ItemStack): Option[I] = if (matches(itemStack)) itemStack.getItemAs[I] else None
}

class HT_ItemStackPatternOre(val name: String, override val eqOrMore: Int) extends HT_ItemStackPattern(eqOrMore) {
  override protected def satisfy(itemStack: ItemStack): Boolean = itemStack hasName name

  def unapply(itemStack: ItemStack): Option[Item] = if (matches(itemStack)) Some(itemStack.getItem) else None
}