package com.github.hokutomc.lib.item;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2014/10/08.
 */
public final class HT_ItemStackUtil {
    private HT_ItemStackUtil () {
    }

    public static int getStackSize (ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() != null ? itemStack.stackSize : 0;
    }

    public static <T extends Item> T getItemAs (ItemStack itemStack, Class<T> itemClass) {
        return itemStack != null && itemStack.getItem() != null && itemClass.isInstance(itemStack.getItem()) ? itemClass.cast(itemStack.getItem()) : null;
    }
}
