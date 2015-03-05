package com.github.hokutomc.lib.util;

import com.github.hokutomc.lib.item.HT_ItemStackBuilder;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/02/12.
 */
public interface HT_I_Colored {
    HT_Color getColor (ItemStack itemStack);

    HT_ItemStackBuilder getItemStackFromColor (HT_Color color);
}