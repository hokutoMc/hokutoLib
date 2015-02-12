package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.util.HT_Color;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * Created by user on 2014/12/21.
 */
public class HT_BlockColored extends HT_MultiBlock {
    public HT_BlockColored (String modid, Material material, String innerName) {
        super(modid, material, innerName, HT_Color.colorNamesForBlock());
    }

    @Override
    public MapColor HT_getMapColor (int p_149728_1_) {
        return MapColor.getMapColorForBlockColored(p_149728_1_);
    }
}
