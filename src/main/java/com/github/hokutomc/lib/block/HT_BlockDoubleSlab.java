package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.HT_Registries;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by user on 2014/12/21.
 */
public class HT_BlockDoubleSlab extends HT_MultiBlock {
    public final HT_BlockSingleSlab m_upper;
    public final HT_BlockSingleSlab m_lower;

    public HT_BlockDoubleSlab (String modid, Material material, String innerName, String... subNameList) {
        super(modid, material, innerName, subNameList);
        this.m_upper = this.getPartialSlab(true);
        this.m_lower = this.getPartialSlab(false);
    }

    @Override
    public HT_MultiBlock register () {
        HT_Registries.registerBlock(this, HT_ItemSlab.class);
        HT_Registries.registerBlock(this.m_upper);
        HT_Registries.registerBlock(this.m_lower);
        return this;
    }

    private HT_BlockSingleSlab getPartialSlab (boolean isUpper) {
        return new HT_BlockSingleSlab(this.m_modid, this.HT_getMaterial(), super.HT_getShortName(), isUpper, this, this.getMultiNames());
    }

    @Override
    public int HT_quantityDropped (Random random) {
        return 2;
    }

    @Override
    public String HT_getShortName () {
        return super.HT_getShortName() + "_double";
    }

    @Override
    public void HT_setBlockBoundsForItemRender () {
        this.HT_setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public void HT_addCollisionBoxesToList (World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        this.HT_setBlockBoundsBasedOnState(world, x, y, z);
        super.HT_addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
    }

    @Override
    public void HT_setBlockBoundsBasedOnState (IBlockAccess world, int x, int y, int z) {
        this.HT_setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    //    @Override
//    public int HT_onBlockPlaced (World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
//        if (side == 1 || (double)(hitY - y) <= 0.5D) {
//            world.setBlock(x, y, z, this.m_lower, meta, 7);
//            return meta;
//        }
//        world.setBlock(x, y, z, this.m_upper, meta, 7);
//
//        return meta;
//    }





    public static class HT_ItemSlab extends HT_MultiItemBlock {

        private final HT_BlockDoubleSlab blockDouble;

        public HT_ItemSlab (Block block) {
            super(block);
            this.blockDouble = (HT_BlockDoubleSlab) block;
        }

        public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float playerX, float playerY, float playerZ) {
            if (itemStack.stackSize == 0) {
                return false;
            } else if (!player.canPlayerEdit(x, y, z, side, itemStack)) {
                return false;
            } else {
                Block block = world.getBlock(x, y, z);
                int i1 = world.getBlockMetadata(x, y, z);

                if ((side == 1 && block == this.blockDouble.m_lower || side == 0 && block == this.blockDouble.m_upper) && i1 == itemStack.getItemDamage()) {
                    if (world.checkNoEntityCollision(this.blockDouble.getCollisionBoundingBoxFromPool(world, x, y, z)) && placeDoubleBlock(world, x, y, z, i1)) {
                        playBlockSound(world, x, y, z);
                        --itemStack.stackSize;
                    }

                    return true;
                } else {
                    return this.onAddedToOtherBlock(itemStack, player, world, x, y, z, side, playerX, playerY, playerZ) || super.onItemUse(itemStack, player, world, x, y, z, side, playerX, playerY, playerZ);
                }
            }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack itemStack) {
            int x0 = x;
            int y0 = y;
            int z0 = z;
            Block block = world.getBlock(x, y, z);
            int blockMeta = world.getBlockMetadata(x, y, z);

            if ((side == 1 && block == this.blockDouble.m_lower || side == 0 && block == this.blockDouble.m_upper) && blockMeta == itemStack.getItemDamage()) {
                return true;
            } else {
                switch (side) {
                    case 0 : --y; break;
                    case 1 : ++y; break;
                    case 2 : --z; break;
                    case 3 : ++z; break;
                    case 4 : --x; break;
                    case 5 : ++x; break;
                    default:
                }

                Block block1 = world.getBlock(x, y, z);
                int blockMeta1 = world.getBlockMetadata(x, y, z);
                return (block1 == this.blockDouble.m_lower || block1 == this.blockDouble.m_upper) && blockMeta1 == itemStack.getItemDamage() || super.func_150936_a(world, x0, y0, z0, side, player, itemStack);
            }
        }

        private boolean onAddedToOtherBlock (ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float playerX, float playerY, float playerZ) {
            Block blockAnother = world.getBlock(x, y, z);
            Block metaAnother = world.getBlock(x, y, z);

            switch (side) {
                case 0 : --y; break;
                case 1 : ++y; break;
                case 2 : --z; break;
                case 3 : ++z; break;
                case 4 : --x; break;
                case 5 : ++x; break;
                default:
            }

            Block block = world.getBlock(x, y, z);
            int blockMeta = world.getBlockMetadata(x, y, z);

            if (block.isAir(world, x, y, z) && blockMeta == itemStack.getItemDamage()) {
                if (world.checkNoEntityCollision(this.blockDouble.getCollisionBoundingBoxFromPool(world, x, y, z)) ) {

                    if (side == 0 && placeUpperBlock(world, x, y, z, blockMeta)
                            || side == 1 && placeLowerBlock(world, x, y, z, blockMeta)
                            || playerY >= 0.5 && placeUpperBlock(world, x, y, z, blockMeta)
                            || playerY < 0.5  && placeLowerBlock(world, x, y, z, blockMeta)) {
                        block.onBlockPlacedBy(world, x, y, z, player, itemStack);

                        playBlockSound(world, x, y, z);
                        --itemStack.stackSize;
                    }

                }

                return true;
            } else if (block == this.blockDouble.m_lower && blockMeta == itemStack.getItemDamage() && placeDoubleBlock(world, x, y, z, blockMeta)) {
                playBlockSound(world, x, y, z);
                --itemStack.stackSize;
                return true;
            } else if (block == this.blockDouble.m_upper && blockMeta == itemStack.getItemDamage() && placeDoubleBlock(world, x, y, z, blockMeta)) {
                playBlockSound(world, x, y, z);
                --itemStack.stackSize;
                return true;
            } else {
                return false;
            }
        }

        private void playBlockSound (World world, double x, double y, double z) {
            world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, this.blockDouble.stepSound.func_150496_b(), (this.blockDouble.stepSound.getVolume() + 1.0F) / 2.0F, this.blockDouble.stepSound.getPitch() * 0.8F);
        }

        private boolean placeDoubleBlock (World world, int x, int y, int z, int meta) {
            return world.setBlock(x, y, z, this.blockDouble, meta, 3);
        }

        private boolean placeUpperBlock (World world, int x, int y, int z, int meta) {
            return world.setBlock(x, y, z, this.blockDouble.m_upper, meta, 3);
        }

        private boolean placeLowerBlock (World world, int x, int y, int z, int meta) {
            return world.setBlock(x, y, z, this.blockDouble.m_lower, meta, 3);
        }

//        @Override
//        public boolean placeBlockAt (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
//            if (this.canAddSlab(stack, world, x, y - 1, z)) {
//                world.setBlock(x, y - 1, z, this.blockDouble, stack.getItemDamage(), 7);
//            } else if (side == 1 || (double)(hitY - y) <= 0.5D) {
//                world.setBlock(x, y, z, this.blockDouble.m_lower, stack.getItemDamage(), 7);
//            } else {
//                world.setBlock(x, y, z, this.blockDouble.m_upper, stack.getItemDamage(), 7);
//            }
//            Block block1 = world.getBlock(x, y, z);
//            block1.onBlockPlacedBy(world, x, y, z, player, stack);
//            block1.onPostBlockPlaced(world, x, y, z, world.getBlockMetadata(x, y, z));
//            return true;
//        }


    }
}
