package com.github.hokutomc.lib.reflect;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Array;
import java.util.EnumSet;

/**
 * Created by user on 2014/12/08.
 */
public final class HT_Reflections {

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> Class<T> getClass (@NotNull T... empty) {
        return (Class<T>) empty.getClass().getComponentType();
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <E extends Enum<E>> Class<EnumSet<E>> getEnumSetClass (@NotNull E... empty) {
        return (Class<EnumSet<E>>) EnumSet.noneOf(getClass(empty)).getClass();
    }


    @SuppressWarnings("unchecked")
    public static <P> P[] createDummyArray (Class<P> clazz) {
        return (P[]) Array.newInstance(clazz, 0);
    }
}
