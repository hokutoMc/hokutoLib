package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.util.HT_ArrayUtil;
import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by user on 2014/09/23.
 */
public class HT_MultiBlock extends HT_Block<HT_MultiBlock> {
    private final ImmutableSet<String> m_multiNames;

    @SideOnly(Side.CLIENT)
    private IIcon[] m_multiIcons;

    public HT_MultiBlock (String modid, Material material, String innerName, String... subNameList) {
        super(modid, material, innerName);
        this.m_multiNames = ImmutableSet.copyOf(subNameList);
        this.m_multiIcons = new IIcon[subNameList.length];
    }

    @Override
    public String[] getMultiNames () {
        return HT_ArrayUtil.toArray(ImmutableSet.copyOf(m_multiNames), String.class);
    }

    @Override
    public int HT_damageDropped (int metadata) {
        return metadata;
    }


    @Override
    public IIcon HT_getIcon (int side, int meta) {
        return this.m_multiIcons[meta % m_multiNames.size()];
    }

    @Override
    public void HT_registerBlockIcons (IIconRegister iconRegister) {
        super.HT_registerBlockIcons(iconRegister);
        for (int i = 0; i < m_multiNames.size(); i++) {
            this.m_multiIcons[i] = iconRegister.registerIcon(this.HT_getTextureName() + "_" + m_multiNames.toArray()[i]);
        }
    }

    @Override
    public void HT_registerMulti (Item item, CreativeTabs creativeTab, final List<ItemStack> list) {
        for (int i = 0; i < m_multiNames.size(); i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

}
