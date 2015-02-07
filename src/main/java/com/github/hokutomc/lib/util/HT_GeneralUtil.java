package com.github.hokutomc.lib.util;

/**
 * This class provides General methods.
 */
public final class HT_GeneralUtil {
    private HT_GeneralUtil(){}

    /**
     * if value is null returns alternate object.
     * @param value
     * @param alternate
     * @param <T>
     * @return
     */
    public static <T> T nullChecked (T value, T alternate) {
        return value != null ? value : alternate;
    }


}
