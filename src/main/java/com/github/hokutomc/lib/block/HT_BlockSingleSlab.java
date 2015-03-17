package com.github.hokutomc.lib.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by user on 2014/12/21.
 */
public class HT_BlockSingleSlab extends HT_Block<HT_BlockSingleSlab> {
    public final boolean m_isUpper;
    private final HT_BlockDoubleSlab m_parent;

    protected HT_BlockSingleSlab (String modid, Material material, String innerName, boolean isUpper, HT_BlockDoubleSlab parent, String... subNameList) {
        super(modid, material, innerName);
        this.multi(subNameList);
        this.m_isUpper = isUpper;
        this.m_parent = parent;
        if (this.m_isUpper) {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public String getShortName () {
        return super.getShortName() + (m_isUpper ? "_upper" : "_lower");
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {
        return this.m_parent.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState (IBlockState state) {
        return this.m_parent.getMetaFromState(state);
    }

    @Override
    public Item getItem (World world, BlockPos pos) {
        return Item.getItemFromBlock(this.m_parent);
    }

    @Override
    public Item getItemDropped (IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this.m_parent);
    }

    @Override
    public IBlockState onBlockPlaced (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (this.m_isUpper && hitY < 0.5f) {
            worldIn.setBlockState(pos, this.m_parent.m_lower.getStateFromMeta(meta), 9);
        } else if (!this.m_isUpper) {
            worldIn.setBlockState(pos, this.m_parent.m_upper.getStateFromMeta(meta), 9);
        }
        return worldIn.getBlockState(pos);
    }

    @Override
    public void addCollisionBoxesToList (World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }

    @Override
    public void setBlockBoundsBasedOnState (IBlockAccess access, BlockPos pos) {
        if (this.m_isUpper) {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered (IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(world, pos, side)) {
            return false;
        } else {
            BlockPos nextPos = pos.offset(side);
            boolean flag = ((HT_BlockSingleSlab) world.getBlockState(nextPos).getBlock()).m_isUpper;
            return flag ? side == EnumFacing.UP || side == EnumFacing.DOWN && super.shouldSideBeRendered(world, pos, side) || !isHalfSlab(world, nextPos) : (side == EnumFacing.UP || (side == EnumFacing.DOWN && super.shouldSideBeRendered(world, pos, side) || !isHalfSlab(world, nextPos)));
        }
    }

    private boolean isHalfSlab (IBlockAccess world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block instanceof HT_BlockSingleSlab || block == Blocks.stone_slab || block == Blocks.wooden_slab;
    }
}
