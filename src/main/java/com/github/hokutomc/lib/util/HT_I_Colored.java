package com.github.hokutomc.lib.util;

import com.github.hokutomc.lib.item.HT_ItemCondition;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/02/12.
 */
public interface HT_I_Colored {
    EnumDyeColor getColor (ItemStack itemStack);

    HT_ItemCondition getItemStackFromColor (EnumDyeColor color);
}
