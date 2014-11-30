package com.github.hokutomc.lib.item;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by user on 2014/10/08.
 */
public final class HT_ItemStackUtil {
    private HT_ItemStackUtil () {
    }

    public static ItemStack createItemStack (Item item, int size, int damage) {
        ItemStack stack = new ItemStack(item, size, damage);
        NBTTagCompound tagCompound = new NBTTagCompound();
        stack.setTagCompound(tagCompound);
        return stack;
    }

    public static ItemStack createItemStack (Block block, int size, int damage) {
        return createItemStack(Item.getItemFromBlock(block), size, damage);
    }
}
