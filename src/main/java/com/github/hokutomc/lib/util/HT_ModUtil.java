package com.github.hokutomc.lib.util;


import net.minecraftforge.fml.common.Mod;

import java.lang.annotation.Annotation;

/**
 * Created by user on 2014/09/27.
 */
public final class HT_ModUtil {
    private HT_ModUtil () {
    }

    public static Mod getMod (Object obj) {
        for (Annotation annotation : obj.getClass().getAnnotations()) {
            if (annotation instanceof Mod) return (Mod) annotation;
        }
        return null;
    }

    public static String getModId (Object obj) {
        return getMod(obj).modid();
    }
}
