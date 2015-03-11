package com.github.hokutomc.lib.scala.world.gen

import java.util.Random

import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenMinable


object HT_ScalaOreGenGenerator {

  class Nether(override val block: Block, override val size: Int, override val meta: Int = -1, override val target: Block = Blocks.netherrack, override val chance: Int = 30, override val canceled: Float = 0, override val yMax: Int = 64, override val yMin: Int = 2)
    extends HT_ScalaOreGenGenerator(block, size, meta, target, chance, yMax, yMin)

  class End(override val block: Block, override val size: Int, override val meta: Int = -1, override val target: Block = Blocks.end_stone, override val chance: Int = 30, override val canceled: Float = 0, override val yMax: Int = 64, override val yMin: Int = 2)
    extends HT_ScalaOreGenGenerator(block, size, meta, target, chance, yMax, yMin)

  class Surface(override val block: Block, override val size: Int, override val meta: Int = -1, override val target: Block = Blocks.end_stone, override val chance: Int = 30, override val canceled: Float = 0, override val yMax: Int = 64, override val yMin: Int = 2)
    extends HT_ScalaOreGenGenerator(block, size, meta, target, chance, yMax, yMin)

}

/**
 * Created by user on 2015/03/09.
 */
class HT_ScalaOreGenGenerator(val block: Block, val size: Int, val meta: Int = -1, val target: Block = Blocks.stone, val chance: Int = 30, val canceled: Float = 0, val yMax: Int = 64, val yMin: Int = 2) {

  val generator = if (meta < 0) new WorldGenMinable(block, size, target)
  else new WorldGenMinable(block, meta, size, target)

  def generate(x: Int, z: Int, world: World, rand: Random): Unit = {
    for (i <- 0 until chance if rand.nextFloat() >= canceled) {
      val genX = x + rand.nextInt(16)
      val genY = yMin + rand.nextInt(yMax - yMin)
      val genZ = z + rand.nextInt(16)

      generator.generate(world, rand, genX, genY, genZ)
    }
  }
}
