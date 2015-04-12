package com.github.hokutomc.lib.oredict;

import com.github.hokutomc.lib.item.HT_ItemStackUtil;
import com.github.hokutomc.lib.item.matcher.HT_ItemMatcherItem;
import com.github.hokutomc.lib.util.HT_ArrayUtil;
import com.github.hokutomc.lib.util.HT_OreUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2015/04/10.
 */
public final class HT_OreDictPlus {
    private HT_OreDictPlus () {
    }

    public static HT_OreDictPlus EVENT_HOOK = new HT_OreDictPlus();

    private static Map<String, Predicate<ItemStack>> map = Maps.newHashMap();

    public static boolean hasName (ItemStack itemStack, String name) {
        return HT_OreUtil.hasName(itemStack, name) || hasExtraName(itemStack, name);
    }

    public static boolean hasExtraName (ItemStack itemStack, String name) {
        if (itemStack != null) {
            HT_ItemOre o = HT_ItemStackUtil.getItemAs(itemStack, HT_ItemOre.class);
            if (o != null) {
                for (String s : o.getOreNames(itemStack)) {
                    if (s.equalsIgnoreCase(name)) return true;
                }
            }
        }
        return false;
    }

    public static List<String> getNames (ItemStack itemStack) {
        return Lists.newArrayList(HT_ArrayUtil.append(HT_OreUtil.getNames(itemStack), getExtraNames(itemStack)));
    }

    private static String[] getExtraNames (ItemStack itemStack) {
        if (itemStack != null) {
            HT_ItemOre o = HT_ItemStackUtil.getItemAs(itemStack, HT_ItemOre.class);
            if (o != null) {
                return o.getOreNames(itemStack);
            }
        }
        return new String[0];
    }

    private static void registerOre (String name, Predicate<ItemStack> matcher) {
        map.put(name, matcher);
    }

    private static void registerOreByStack (String name, ItemStack itemStack) {
        registerOre(name, HT_ItemMatcherItem.ofStack(itemStack));
    }

    @SubscribeEvent
    public void oreRegisterationHook (OreDictionary.OreRegisterEvent event) {
        registerOreByStack(event.Name, event.Ore);
    }


}
