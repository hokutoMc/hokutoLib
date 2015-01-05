package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2014/09/23.
 */
public class HT_MultiItemBlock extends ItemBlock {
    private final int m_variety;
    private String[] m_subNames;

    public HT_MultiItemBlock (Block block) {
        super(block);
        if (block instanceof HT_MultiBlock) {
            m_subNames = ((HT_MultiBlock) block).getMultiNames();
            m_variety = m_subNames.length;
        } else {
            m_variety = 0;
        }
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata (int metaData) {
        return metaData;
    }

    @Override
    public String getUnlocalizedName (ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + m_subNames[itemStack.getItemDamage() % m_variety];
    }
}
