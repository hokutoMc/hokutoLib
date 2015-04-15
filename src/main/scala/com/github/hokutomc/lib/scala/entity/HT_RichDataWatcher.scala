package com.github.hokutomc.lib.scala.entity

import net.minecraft.entity.DataWatcher

/**
 * Created by user on 2015/04/13.
 */
class HT_RichDataWatcher(val wrapped: DataWatcher) {
  def init[A](id: Int, default: A)(implicit ev: HT_DataWatchEvidence[A]) = ev.init(wrapped, id, default)

  def apply[A](id: Int)(implicit ev: HT_DataWatchEvidence[A]): A = ev.get(wrapped, id)

  def update[A](id: Int, a: A)(implicit ev: HT_DataWatchEvidence[A]): Unit = ev.set(wrapped, id, a)
}
