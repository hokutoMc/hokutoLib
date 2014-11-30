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
public class HT_MultiBlock extends HT_AbstractBlock<HT_MultiBlock> {
    private final ImmutableSet<String> multiNames;

    @SideOnly(Side.CLIENT)
    private IIcon[] multiIcons;

    public HT_MultiBlock (String modid, Material material, String innerName, String[] subNameList) {
        super(modid, material, innerName);
        this.multiNames = ImmutableSet.copyOf(subNameList);
        this.multiIcons = new IIcon[subNameList.length];
    }

    @Override
    public String[] HT_getMultiNames () {
        return HT_ArrayUtil.toArray(ImmutableSet.copyOf(multiNames), String.class);
    }

    @Override
    public int HT_damageDropped (int metadata) {
        return metadata;
    }


    @Override
    public IIcon HT_getIcon (int side, int meta) {
        return this.multiIcons[meta % multiNames.size()];
    }

    @Override
    public void HT_registerBlockIcons (IIconRegister iconRegister) {
        super.HT_registerBlockIcons(iconRegister);
        for (int i = 0; i < multiNames.size(); i++) {
            this.multiIcons[i] = iconRegister.registerIcon(this.HT_getTextureName() + "_" + multiNames.toArray()[i]);
        }
    }

    @Override
    public void HT_registerMulti (Item item, CreativeTabs creativeTab, final List<ItemStack> list) {
        for (int i = 0; i < multiNames.size(); i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

}
