package com.github.hokutomc.lib.oredict;

import com.github.hokutomc.lib.item.HT_ItemCondition;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2015/06/15.
 */
public class HT_SensitiveOreDict {
    private HT_SensitiveOreDict () {
    }

    public static final HT_SensitiveOreDict HOOK_OBJ = new HT_SensitiveOreDict();

    private static final Map<String, List<HT_ItemCondition>> nameToConditions = Maps.newHashMap();
    private static final Map<HT_ItemCondition, List<String>> conditionToNames = Maps.newHashMap();

    public static void registerSensitiveOre (String name, HT_ItemCondition condition) {
        if ("Unknown".equals(name)) {
            return;
        }

        putNewOrAdd(nameToConditions, name, condition);
        putNewOrAdd(conditionToNames, condition, name);
        MinecraftForge.EVENT_BUS.post(new SensitiveOreRegisterEvent(name, condition));
    }

    @SuppressWarnings("unchecked")
    private static <K, V> void putNewOrAdd (Map<K, List<V>> map, K key, V value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<V> l = new LinkedList<>();
            l.add(value);
            map.put(key, l);
        }
    }

    /**
     * Returns if the item stack has the name according to this class and {@linkplain net.minecraftforge.oredict.OreDictionary}.
     *
     * @param itemStack
     * @param oreName
     * @return if the item stack has the name
     */
    public static boolean hasName (ItemStack itemStack, String oreName) {
        for (HT_ItemCondition cond : getOres(oreName)) {
            if (cond.check(itemStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all the ore names according this class and {@linkplain net.minecraftforge.oredict.OreDictionary}.
     * If nothing is found, returns empty list.
     *
     * @param itemStack
     * @return All the names. Empty list if nothing found.
     */
    public static List<String> getNames (ItemStack itemStack) {
        if (itemStack == null) {
            return new LinkedList<>();
        }

        List<String> names = new LinkedList<>();
        for (HT_ItemCondition cond : conditionToNames.keySet()) {
            if (cond.check(itemStack)) {
                names.addAll(conditionToNames.get(cond));
            }
        }
        return names;
    }

    public static ImmutableList<HT_ItemCondition> getOres (String name) {
        return !nameToConditions.containsKey(name) ? ImmutableList.<HT_ItemCondition>of() : ImmutableList.copyOf(nameToConditions.get(name));
    }

    public static class SensitiveOreRegisterEvent extends Event {
        public final String name;
        public final HT_ItemCondition condition;

        public SensitiveOreRegisterEvent (String name, HT_ItemCondition condition) {
            this.name = name;
            this.condition = condition;
        }
    }

    @SubscribeEvent
    public void oreRegisterHook (OreDictionary.OreRegisterEvent event) {
        registerSensitiveOre(event.Name, HT_ItemCondition.ofItemStack(event.Ore));
    }
}
