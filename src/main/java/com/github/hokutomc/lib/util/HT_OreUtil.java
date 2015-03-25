package com.github.hokutomc.lib.util;


import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * Created by user on 2014/10/08.
 */
public final class HT_OreUtil {
    private HT_OreUtil () {
    }

    public static ItemStack getOneItemStack (String oreName, int size) {
        ItemStack stack = OreDictionary.getOres(oreName).get(0);
        if (stack != null) {
            stack.stackSize = size;
        }
        return stack;
    }

    public static ItemStack getWithPriority (String oreName, int size, ItemStack priority, boolean strict) {
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
        }
        return stack;
    }

    public static String[] getNames (ItemStack itemStack) {
        if (itemStack == null) return new String[0];
        int[] intIds = OreDictionary.getOreIDs(itemStack);
        ArrayList<String> strings = new ArrayList<>();
        for (int e : intIds) {
            strings.add(OreDictionary.getOreName(e));
        }
        return HT_ArrayUtil.toArray(strings, String.class);
    }

    public static boolean matchAnyOreName (ItemStack ore1, ItemStack ore2) {
        String[] names1 = getNames(ore1);
        String[] names2 = getNames(ore2);
        for (String e1 : names1) {
            for (String e2 : names2) {
                if (e1 != null && e1.equals(e2)) return true;
            }
        }
        return false;
    }

    public static boolean hasName (ItemStack itemStack, String name) {
        for (String s : getNames(itemStack)) {
            if (s.equals(name)) return true;
        }
        return false;
    }
}
