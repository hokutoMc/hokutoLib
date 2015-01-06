package com.github.hokutomc.lib.reflect;

import java.lang.reflect.Array;
import java.util.EnumSet;

/**
 * This class provides methods to deal with classes.
 *
 * 2014/12/08.
 */
public final class HT_Reflections {

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> Class<T> getClass (T... empty) {
        return (Class<T>) empty.getClass().getComponentType();
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <E extends Enum<E>> Class<EnumSet<E>> getEnumSetClass (E... empty) {
        return (Class<EnumSet<E>>) EnumSet.noneOf(getClass(empty)).getClass();
    }


    @SuppressWarnings("unchecked")
    public static <P> P[] createDummyArray (Class<P> clazz) {
        return (P[]) Array.newInstance(clazz, 0);
    }
}
