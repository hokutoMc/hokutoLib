package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.item.HT_ItemCondition;
import com.github.hokutomc.lib.util.HT_I_Colored;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Created by user on 2014/12/21.
 */
public class HT_BlockColored<T extends HT_BlockColored<T>> extends HT_Block<T> implements HT_I_Colored {
    private boolean m_isDyable;

    public HT_BlockColored (String modid,  Material material, String innerName) {
        super(modid, material, innerName);
        this.multi(toStrings(EnumDyeColor.values()));
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE));
    }

    private String[] toStrings (EnumDyeColor[] colors) {
        String[] names = new String[colors.length];
        for (EnumDyeColor c : colors) {
            names[c.func_176765_a()] = c.func_176762_d();
        }
        return names;
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {
        return this.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.func_176764_b(meta).func_176765_a());
    }

    @Override
    public int getMetaFromState (IBlockState state) {
        return ((EnumDyeColor) state.getValue(BlockColored.COLOR)).func_176765_a();
    }

    @Override
    public MapColor getMapColor (IBlockState state) {
        return ((EnumDyeColor) state.getValue(BlockColored.COLOR)).func_176768_e();
    }


    public EnumDyeColor getClolor (IBlockAccess world, BlockPos pos) {
        return (EnumDyeColor) world.getBlockState(pos).getValue(BlockColored.COLOR);
    }

    @Override
    public EnumDyeColor getColor (ItemStack itemStack) {
        return EnumDyeColor.func_176766_a(itemStack.getItemDamage());
    }

    @Override
    public HT_ItemCondition getItemStackFromColor (EnumDyeColor color) {
        return HT_ItemCondition.builder(this).checkDamage(color.func_176765_a()).build();
    }
}
