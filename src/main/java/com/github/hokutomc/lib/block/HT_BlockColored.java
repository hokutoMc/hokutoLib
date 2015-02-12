package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.item.HT_ItemStackBuilder;
import com.github.hokutomc.lib.util.HT_Color;
import com.github.hokutomc.lib.util.HT_I_Colored;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

/**
 * Created by user on 2014/12/21.
 */
public class HT_BlockColored extends HT_Block<HT_BlockColored> implements HT_I_Colored {
    private boolean m_isDyable;

    public HT_BlockColored (String modid,  Material material, String innerName) {
        super(modid, material, innerName);
        this.multi(HT_Color.colorNamesForBlock());
    }

    @Override
    public MapColor HT_getMapColor (int p_149728_1_) {
        return MapColor.getMapColorForBlockColored(p_149728_1_);
    }

    public HT_Color getColor (int meta) {
        return HT_Color.getByColoredBlockMeta(meta);
    }

    public HT_Color getClolor (IBlockAccess world, int x, int y, int z) {
        return HT_Color.getByColoredBlockMeta(world.getBlockMetadata(x, y, z));
    }

    @Override
    public HT_Color getColor (ItemStack itemStack) {
        return getColor(itemStack.getItemDamage());
    }

    @Override
    public HT_ItemStackBuilder getItemStackFromColor (HT_Color color) {
        return new HT_ItemStackBuilder<>(this).size(1).damage(color.toColoredBlockMeta());
    }
}
