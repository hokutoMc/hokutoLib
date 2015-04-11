package com.github.hokutomc.lib.item.matcher;

import com.github.hokutomc.lib.oredict.HT_OreDictPlus;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/04/10.
 */
public class HT_ItemMatcherOre extends HT_ItemMatcher {
    private final String oreName;

    public HT_ItemMatcherOre (String oreName) {
        this.oreName = oreName;
    }

    @Override
    protected boolean check (ItemStack itemStack) {
        return HT_OreDictPlus.hasName(itemStack, oreName);
    }
}
