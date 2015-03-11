package com.github.hokutomc.lib.scala.world.gen

import java.util.Random

import com.github.hokutomc.lib.scala.world.gen.HT_ScalaOreGenGenerator.{End, Nether, Surface}
import com.github.hokutomc.lib.world.gen.HT_OreGenerator
import net.minecraft.world.World


/**
 * Created by user on 2015/03/09.
 */
class HT_ScalaSimpleOreGen(val gen: HT_ScalaOreGenGenerator*) extends HT_OreGenerator {
  override protected def generateSurface(world: World, random: Random, x: Int, z: Int): Unit =
    gen foreach { case a: Surface => a.generate(x, z, world, random)}

  override protected def generateEnd(world: World, random: Random, x: Int, z: Int): Unit =
    gen foreach { case a: End => a.generate(x, z, world, random)}

  override protected def generateHell(world: World, random: Random, x: Int, z: Int): Unit =
    gen foreach { case a: Nether => a.generate(x, z, world, random)}
}
