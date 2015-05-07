package com.github.hokutomc.lib.scala.client

import net.minecraft.client.model.ModelBase

/**
 * Created by user on 2015/04/29.
 */
trait HT_T_Model {
  this: ModelBase =>
  def model(u: Int, v: Int, rotationPos: (Double, Double, Double), mirror: Boolean = false): HT_ModelRenderer = {
    val render = new HT_ModelRenderer(this, u, v)
    render.setTextureSize(this.textureWidth, this.textureWidth)
    render.setRotationPoint(rotationPos._1 toFloat, rotationPos._2 toFloat, rotationPos._3 toFloat)
    render.mirror = mirror
    render
  }
}
