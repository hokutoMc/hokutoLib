package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by user on 2014/12/21.
 */
public class HT_BlockSingleSlab extends HT_MultiBlock {
    public final boolean m_isUpper;
    private final HT_BlockDoubleSlab m_parent;

    protected HT_BlockSingleSlab (String modid, Material material, String innerName, boolean isUpper, HT_BlockDoubleSlab parent, String... subNameList) {
        super(modid, material, innerName, subNameList);
        this.m_isUpper = isUpper;
        this.m_parent = parent;
        if (this.m_isUpper) {
            this.HT_setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.HT_setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public String HT_getShortName () {
        return super.HT_getShortName() + (m_isUpper ? "_upper" : "_lower");
    }

    @Override
    public void HT_registerMulti (Item item, CreativeTabs creativeTab, List<ItemStack> list) {}

    @Override
    public boolean HT_renderAsNormalBlock () {
        return false;
    }

    @Override
    public boolean HT_isOpaque () {
        return false;
    }

    @Override
    public boolean HT_isOpaqueCube () {
        return false;
    }

    @Override
    public Item HT_getItem (World world, int x, int y, int z) {
        return Item.getItemFromBlock(this.m_parent);
    }

    @Override
    public Item HT_getItemDropped (int p_149650_1_, Random random, int p_149650_3_) {
        return Item.getItemFromBlock(this.m_parent);
    }

    @Override
    public int HT_onBlockPlaced (World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        if (this.m_isUpper && hitY < 0.5f) {
            world.setBlock(x, y, z, this.m_parent.m_lower, meta, 9);
        } else if (!this.m_isUpper) {
            world.setBlock(x, y, z, this.m_parent.m_upper, meta, 9);
        }
        return meta;
    }

    //    @Override
//    public void HT_onBlockClicked (World world, int x, int y, int z, EntityPlayer player) {
//        if (this.HT_canAddSlab(player.getCurrentEquippedItem(), world, x, y, z)) {
//            world.setBlock(x, y, z, this.m_parent, player.getCurrentEquippedItem().getItemDamage(), 9);
//            return;
//        }
//        super.HT_onBlockClicked(world, x, y, z, player);
//    }


    @Override
    public void HT_addCollisionBoxesToList (World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        this.HT_setBlockBoundsBasedOnState(world, x, y, z);
        super.HT_addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
    }

    @Override
    public void HT_setBlockBoundsBasedOnState (IBlockAccess world, int x, int y, int z) {
        if (this.m_isUpper) {
            this.HT_setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.HT_setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean HT_shouldSideBeRendered (IBlockAccess world, int x, int y, int z, int side) {
        if (side != 1 && side != 0 && !super.HT_shouldSideBeRendered(world, x, y, z, side)) {
            return false;
        } else {
            int i1 = x + Facing.offsetsXForSide[Facing.oppositeSide[side]];
            int j1 = y + Facing.offsetsYForSide[Facing.oppositeSide[side]];
            int k1 = z + Facing.offsetsZForSide[Facing.oppositeSide[side]];
            boolean flag = ((HT_BlockSingleSlab) world.getBlock(i1, j1, k1)).m_isUpper;
            return flag ? (side == 0 || (side == 1 && super.HT_shouldSideBeRendered(world, x, y, z, side) || !isHalfSlab(world.getBlock(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) == 0)) : (side == 1 || (side == 0 && super.HT_shouldSideBeRendered(world, x, y, z, side) || !isHalfSlab(world.getBlock(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) != 0));
        }
    }

    private boolean isHalfSlab (Block block) {
        return block instanceof HT_BlockSingleSlab || block == Blocks.stone_slab || block == Blocks.wooden_slab;
    }
}
