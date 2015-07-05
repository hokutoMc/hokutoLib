package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.util.HT_I_Colored;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/02/12.
 */
public class HT_ItemColored<T extends HT_ItemColored<T>> extends HT_Item<T> implements HT_I_Colored {
    public static class Raw extends HT_ItemColored<Raw> {
        public Raw (String modid, String innerName) {
            super(modid, innerName);
        }
    }


    public HT_ItemColored (String modid, String innerName) {
        super(modid, innerName);
        this.multi(toStringArray(EnumDyeColor.values()));
    }

    private String[] toStringArray (EnumDyeColor[] colors) {
        String[] names = new String[colors.length];
        for (EnumDyeColor color : colors) {
            names[color.getDyeColorDamage()] = color.func_176762_d();
        }
        return names;
    }

    @Override
    public EnumDyeColor getColor (ItemStack itemStack) {
        return EnumDyeColor.func_176766_a(itemStack.getItemDamage());
    }

    @Override
    public HT_ItemCondition getItemStackFromColor (EnumDyeColor color) {
        return HT_ItemCondition.builder(this).checkDamage(color.getDyeColorDamage()).build();
    }
}
