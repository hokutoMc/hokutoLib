package com.github.hokutomc.lib.util;

import com.github.hokutomc.lib.reflect.HT_Reflections;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Created by user on 2014/10/27.
 */
public final class HT_ArrayUtil {
    private HT_ArrayUtil () {
    }

    public static <T> T getWithNoEx (T[] array, int index) {
        return array[index % array.length];
    }

    @SafeVarargs
    public static <T> T getWithNoEx (Collection<T> collection, int index, T... empty) {
        return getWithNoEx(toArray(collection, empty), index);
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

    @SafeVarargs
    public static <T> T[] toArray (Collection<T> collection, T... empty) {
        return toArray(collection, HT_Reflections.getClass(empty));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] split (T[] stacks, int start, int size) {
        Object[] objects = new Object[size];
        System.arraycopy(stacks, start, objects, 0, size);
        return (T[]) objects;
    }

    @SuppressWarnings("unchecked")
    public static <R> R[] append (R[] arrayA, R[] arrayB, R... dummy) {
        Class<R> rClass = HT_Reflections.getClass(dummy);
        R[] newArr = (R[]) Array.newInstance(rClass, arrayA.length + arrayB.length);
        System.arraycopy(arrayA, 0, newArr, 0, arrayA.length);
        System.arraycopy(arrayB, 0, newArr, arrayA.length, arrayB.length);
        return newArr;
    }
}
