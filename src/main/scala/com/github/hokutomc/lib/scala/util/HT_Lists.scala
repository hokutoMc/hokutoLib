package com.github.hokutomc.lib.scala.util

import java.util

import com.google.common.collect.Lists


/**
 * Created by user on 2015/05/06.
 */
object HT_Lists {

  object JavaArrayList {
    def apply[A](a: A*): util.ArrayList[A] = Lists.newArrayList(a: _*)

    def tabulate[A](size: Int)(func: Int => A): util.ArrayList[A] = {
      require(size >= 0, "size should be non-negative")
      val list = new util.ArrayList[A](size)
      for (i <- 0 until size) list.add(i, func(i))
      list
    }
  }

}
