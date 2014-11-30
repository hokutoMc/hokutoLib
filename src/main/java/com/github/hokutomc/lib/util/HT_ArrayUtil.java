package com.github.hokutomc.lib.util;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Created by user on 2014/10/27.
 */
public final class HT_ArrayUtil {
    private HT_ArrayUtil () {
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] castAll (Object[] objArray, Class<T> clazz) {
        Object[] object;
        for (Object entry : objArray) {
            if (!(clazz.isInstance(entry))) {
                throw new ClassCastException();
            }
        }
        return ((T[]) objArray);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray (Collection<T> collection, Class<T> clazz) {
        return collection.toArray((T[]) Array.newInstance(clazz, collection.size()));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] HT_split (T[] stacks, int start, int size) {
        Object[] objects = new Object[size];
        System.arraycopy(stacks, start, objects, 0, size);
        return (T[]) objects;
    }
}
