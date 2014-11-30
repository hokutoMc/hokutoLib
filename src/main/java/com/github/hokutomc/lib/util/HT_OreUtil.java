package com.github.hokutomc.lib.util;


import com.github.hokutomc.lib.nbt.HT_NBTSetter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by user on 2014/10/08.
 */
public final class HT_OreUtil {
    private HT_OreUtil () {
    }

    public static ItemStack getOneItemStack (String oreName, int size, HT_NBTSetter nbtSetter) {
        ItemStack stack = OreDictionary.getOres(oreName).get(0);
        if (stack != null) {
            stack.stackSize = size;
            nbtSetter.accept(stack, stack.getTagCompound());
        }
        return stack;
    }

    public static ItemStack getWithPriority (String oreName, int size, HT_NBTSetter nbtSetter, ItemStack priority, boolean strict) {
        ItemStack stack = null;
        for (ItemStack entry : OreDictionary.getOres(oreName)) {
            if (OreDictionary.itemMatches(entry, priority, strict)) {
                stack = entry;
            }
        }
        if (stack == null) {
            stack = OreDictionary.getOres(oreName).get(0);
        }
        if (stack != null) {
            stack.stackSize = size;
            nbtSetter.accept(stack, stack.getTagCompound());
        }
        return stack;
    }
}
