package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.item.HT_ItemStackBuilder;
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
        return this.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.func_176766_a(meta).func_176765_a());
    }

    @Override
    public int getMetaFromState (IBlockState state) {
        return ((EnumDyeColor) state.getValue(BlockColored.COLOR)).getDyeColorDamage();
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
    public HT_ItemStackBuilder getItemStackFromColor (EnumDyeColor color) {
        return new HT_ItemStackBuilder<>(this).size(1).damage(color.func_176765_a());
    }
}
