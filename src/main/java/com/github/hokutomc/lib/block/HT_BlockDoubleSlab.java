package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.HT_Registries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * This class is easy way to create slab blocks.
 * This class is the base to create half slab and slab block.
 * This class contains instances of upper slab and lower slab.
 *
 * 2014/12/21.
 */
public class HT_BlockDoubleSlab extends HT_Block<HT_BlockDoubleSlab> {
    public final HT_BlockSingleSlab m_upper;
    public final HT_BlockSingleSlab m_lower;


    public HT_BlockDoubleSlab (String modid, Material material, String innerName, String... subNameList) {
        super(modid, material, innerName);
        this.multi(subNameList);
        this.m_upper = this.getPartialSlab(true);
        this.m_lower = this.getPartialSlab(false);
    }

    @Override
    public HT_BlockDoubleSlab register () {
        HT_Registries.registerBlock(this, HT_ItemSlab.class);
        HT_Registries.registerBlock(this.m_upper);
        HT_Registries.registerBlock(this.m_lower);
        return this;
    }

    private HT_BlockSingleSlab getPartialSlab (boolean isUpper) {
        return new HT_BlockSingleSlab(this.m_modid, this.getMaterial(), super.getShortName(), isUpper, this, this.getMultiNames());
    }

    @Override
    public int quantityDropped (Random random) {
        return 2;
    }

    @Override
    public String getShortName () {
        return super.getShortName() + "_double";
    }

    @Override
    public void setBlockBoundsForItemRender () {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public void addCollisionBoxesToList (World world, BlockPos pos, IBlockState state, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        this.setBlockBoundsBasedOnState(world, pos);
        super.addCollisionBoxesToList(world, pos, state, axisAlignedBB, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState (IBlockAccess world, BlockPos pos) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }


    public static class HT_ItemSlab extends HT_MultiItemBlock {

        private final HT_BlockDoubleSlab blockDouble;

        public HT_ItemSlab (Block block) {
            super(block);
            this.blockDouble = (HT_BlockDoubleSlab) block;
        }


        public boolean onItemUse (ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float playerX, float playerY, float playerZ) {
            if (itemStack.stackSize == 0) {
                return false;
            } else if (!world.canBlockBePlaced(this.block, pos, false, side, player, itemStack)) {
                return false;
            } else {
                IBlockState state = world.getBlockState(pos);

                if ((side == EnumFacing.UP && block == this.blockDouble.m_lower || side == EnumFacing.DOWN && block == this.blockDouble.m_upper) && state.getBlock().getMetaFromState(state) == itemStack.getItemDamage()) {
                    if (world.checkNoEntityCollision(this.blockDouble.getCollisionBoundingBox(world, pos, state)) && placeDoubleBlock(world, pos, state.getBlock().getMetaFromState(state))) {
                        playBlockSound(world, pos);
                        --itemStack.stackSize;
                    }

                    return true;
                } else {
                    return this.onAddedToOtherBlock(itemStack, player, world, pos, side, playerX, playerY, playerZ) || super.onItemUse(itemStack, player, world, pos, side, playerX, playerY, playerZ);
                }
            }
        }

//        @Override
//        @SideOnly(Side.CLIENT)
//        public boolean func_150936_a(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack itemStack) {
//            int x0 = x;
//            int y0 = y;
//            int z0 = z;
//            Block block = world.getBlock(x, y, z);
//            int blockMeta = world.getBlockMetadata(x, y, z);
//
//            if ((side == 1 && block == this.blockDouble.m_lower || side == 0 && block == this.blockDouble.m_upper) && blockMeta == itemStack.getItemDamage()) {
//                return true;
//            } else {
//                switch (side) {
//                    case 0 : --y; break;
//                    case 1 : ++y; break;
//                    case 2 : --z; break;
//                    case 3 : ++z; break;
//                    case 4 : --x; break;
//                    case 5 : ++x; break;
//                    default:
//                }
//
//                IBlockState state = world.getBlockState(pos);
//                int blockMeta1 = state.getBlock().getMetaFromState(state);
//                return (state.getBlock() == this.blockDouble.m_lower || state.getBlock() == this.blockDouble.m_upper) && blockMeta1 == itemStack.getItemDamage() || super.func_150936_a(world, x0, y0, z0, side, player, itemStack);
//            }
//        }

        private boolean onAddedToOtherBlock (ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float playerX, float playerY, float playerZ) {
            IBlockState blockAnother = world.getBlockState(pos.offset(side));

            IBlockState state = world.getBlockState(pos);

            int blockMeta = state.getBlock().getMetaFromState(state);

            if (state.getBlock().isAir(world, pos) && state.getBlock().getMetaFromState(state) == itemStack.getItemDamage()) {
                if (world.checkNoEntityCollision(this.blockDouble.getCollisionBoundingBox(world, pos, state))) {

                    if (side == EnumFacing.UP && placeUpperBlock(world, pos, blockMeta)
                            || side == EnumFacing.DOWN && placeLowerBlock(world, pos, blockMeta)
                            || playerY >= 0.5 && placeUpperBlock(world, pos, blockMeta)
                            || playerY < 0.5 && placeLowerBlock(world, pos, blockMeta)) {
                        block.onBlockPlacedBy(world, pos, state, player, itemStack);

                        playBlockSound(world, pos);
                        --itemStack.stackSize;
                    }

                }

                return true;
            } else if (block == this.blockDouble.m_lower && blockMeta == itemStack.getItemDamage() && placeDoubleBlock(world, pos, blockMeta)) {
                playBlockSound(world, pos);
                --itemStack.stackSize;
                return true;
            } else if (block == this.blockDouble.m_upper && blockMeta == itemStack.getItemDamage() && placeDoubleBlock(world, pos, blockMeta)) {
                playBlockSound(world, pos);
                --itemStack.stackSize;
                return true;
            } else {
                return false;
            }
        }

        private void playBlockSound (World world, BlockPos pos) {
            world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, this.blockDouble.stepSound.getPlaceSound(), (this.blockDouble.stepSound.getVolume() + 1.0F) / 2.0F, this.blockDouble.stepSound.getFrequency() * 0.8F);
        }

        private boolean placeDoubleBlock (World world, BlockPos pos, int meta) {
            return world.setBlockState(pos, this.blockDouble.getStateFromMeta(meta), 3);
        }

        private boolean placeUpperBlock (World world, BlockPos pos, int meta) {
            return world.setBlockState(pos, this.blockDouble.m_upper.getStateFromMeta(meta), 3);
        }

        private boolean placeLowerBlock (World world, BlockPos pos, int meta) {
            return world.setBlockState(pos, this.blockDouble.m_lower.getStateFromMeta(meta), 3);
        }
    }
}
