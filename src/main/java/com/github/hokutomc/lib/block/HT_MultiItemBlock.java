package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by user on 2014/09/23.
 */
public class HT_MultiItemBlock extends ItemBlock {
    private final int m_variety;
    private List<String> m_subNames;

    @SuppressWarnings("unchecked")
    public HT_MultiItemBlock (Block block) {
        super(block);
        if (block instanceof HT_Block && ((HT_Block) block).getHasSubTypes()) {
            m_subNames = ((HT_Block) block).getMultiNames();
            m_variety = m_subNames.size();
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
        return super.getUnlocalizedName() + "." + m_subNames.get(itemStack.getItemDamage() % m_variety);
    }
}
