package com.github.hokutomc.lib.data.enumerate;

/**
 * Created by user on 2015/02/13.
 */
public class HT_SimpleStringEnumerator<T> implements HT_I_StringOrderEnumerator<T> {

    private final T[] m_elements;

    @SafeVarargs
    public HT_SimpleStringEnumerator(T... elements) {
        this.m_elements = elements;
    }

    @Override
    public T get (String identifier) {
        for (T e : m_elements) {
            if (e.toString().equals(identifier)) return e;
        }
        return null;
    }
}
