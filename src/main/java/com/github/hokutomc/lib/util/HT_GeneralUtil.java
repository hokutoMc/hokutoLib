package com.github.hokutomc.lib.util;

import com.google.common.base.Objects;

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
    public static <T> T orElse (T value, T alternate) {
        return value != null ? value : alternate;
    }

    public static <T> boolean equalsAnyOf (T left, T... right) {
        for (T t : right) {
            if (Objects.equal(left, t)) return true;
        }
        return false;
    }


}
