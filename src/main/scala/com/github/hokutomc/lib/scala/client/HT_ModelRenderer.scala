package com.github.hokutomc.lib.scala.client

import net.minecraft.client.model.{ModelBase, ModelRenderer}

/**
 * Created by user on 2015/04/30.
 */
class HT_ModelRenderer(model: ModelBase, u: Int, v: Int) extends ModelRenderer(model, u, v) {
  def box(origin: (Double, Double, Double), size: (Int, Int, Int), scale: Double, mirror: Boolean = this.mirror) = {
    this.addBox(origin._1 toFloat, origin._2 toFloat, origin._3 toFloat, size._1, size._2, size._3, scale toFloat)
  }

  def apply(children: (ModelRenderer)*): this.type = {
    children.foreach(this.addChild)
    this
  }
}
