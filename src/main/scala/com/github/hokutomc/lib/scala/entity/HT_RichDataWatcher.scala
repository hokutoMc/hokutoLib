package com.github.hokutomc.lib.scala.entity

import net.minecraft.entity.DataWatcher

/**
 * Created by user on 2015/04/13.
 */
class HT_RichDataWatcher(val wrapped: DataWatcher) {
  def init[A: HT_DataWatchEvidence](id: Int, default: A) = implicitly[HT_DataWatchEvidence[A]].init(wrapped, id, default)

  def apply[A: HT_DataWatchEvidence](id: Int): A = implicitly[HT_DataWatchEvidence[A]].get(wrapped, id)

  def update[A: HT_DataWatchEvidence](id: Int, a: A): Unit = implicitly[HT_DataWatchEvidence[A]].set(wrapped, id, a)
}
