package com.github.hokutomc.lib.item.matcher;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/04/10.
 */
public abstract class HT_ItemMatcher implements Predicate<ItemStack> {
    protected abstract boolean check (ItemStack itemStack);

    @Override
    public boolean apply (ItemStack input) {
        return check(input);
    }

    public boolean matches (ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() != null && check(itemStack);
    }
}
