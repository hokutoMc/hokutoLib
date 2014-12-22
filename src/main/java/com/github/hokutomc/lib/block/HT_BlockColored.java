package com.github.hokutomc.lib.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemDye;

/**
 * Created by user on 2014/12/21.
 */
public class HT_BlockColored extends HT_MultiBlock {
    public HT_BlockColored (String modid, Material material, String innerName) {
        super(modid, material, innerName, ItemDye.field_150923_a);
    }

    @Override
    public MapColor HT_getMapColor (int p_149728_1_) {
        return MapColor.getMapColorForBlockColored(p_149728_1_);
    }
}
