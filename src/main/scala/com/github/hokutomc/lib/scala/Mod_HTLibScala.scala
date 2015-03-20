package com.github.hokutomc.lib.scala

import com.github.hokutomc.lib.item.{HT_Item, HT_ItemStackBuilder}
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import net.minecraft.init.Items
import net.minecraftforge.fml.common.Mod

import scala.collection.JavaConversions

@Mod(modid = "hokutomc.lib.scala", modLanguage = "scala")
object Mod_HTLibScala {

  val a: HT_ItemStackBuilder[_] = Items.diamond

  val b = new MyItem

  class MyItem extends HT_Item("a", "b") {
    this.m_subItems = JavaConversions.seqAsJavaList(Seq.tabulate(9) { i =>
      val c: HT_ItemStackBuilder.Raw = this
      c
    })
  }
}