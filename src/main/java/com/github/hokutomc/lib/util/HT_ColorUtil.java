package com.github.hokutomc.lib.util;

import net.minecraft.item.EnumDyeColor;

/**
 * Created by user on 2015/03/22.
 */
public final class HT_ColorUtil {
    private HT_ColorUtil () {
    }

    public static String getUnlocalizedColorName (EnumDyeColor color) {
        return "color." + color.getName() + ".name";
    }

    public static String localizeColorName (EnumDyeColor color) {
        return HT_I18nUtil.localize(getUnlocalizedColorName(color));
    }
}
