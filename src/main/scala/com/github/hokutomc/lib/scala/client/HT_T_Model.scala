package com.github.hokutomc.lib.scala.client

import net.minecraft.client.model.{ModelBase, ModelRenderer}

/**
 * Created by user on 2015/04/29.
 */
trait HT_T_Model {
  this: ModelBase =>
  def model(u: Int, v: Int, rotationPos: (Double, Double, Double), mirror: Boolean = false)
           (origin: (Double, Double, Double), size: (Int, Int, Int), scale: Double, boxMirror: Boolean = mirror): ModelRenderer = {
    val render = new ModelRenderer(this, u, v).setTextureSize(this.textureWidth, this.textureWidth)
    render.addBox(origin._1 toFloat, origin._2 toFloat, origin._3 toFloat, size._1, size._2, size._3, scale toFloat)
    render.setRotationPoint(rotationPos._1 toFloat, rotationPos._2 toFloat, rotationPos._3 toFloat)
    render.mirror = mirror
    render
  }
}
