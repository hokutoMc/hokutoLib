package com.github.hokutomc.lib.util;

import com.github.hokutomc.lib.common.HT_I_International;
import net.minecraft.util.StatCollector;

/**
 * Created by user on 2014/11/03.
 */
public final class HT_I18nUtil {
    private HT_I18nUtil () {
    }

    public static String localize (String unlocalizedName, Object... datas) {
        return StatCollector.translateToLocalFormatted(unlocalizedName, datas).trim();
    }

    public static String localize (String unlocalizedName) {
        return StatCollector.translateToLocal(unlocalizedName).trim();
    }

    public static String localize (HT_I_International international, Object... datas) {
        return localize(international.getUnlocalizedName(), datas);
    }

    public static String localize (HT_I_International international) {
        return localize(international.getUnlocalizedName());
    }
}
