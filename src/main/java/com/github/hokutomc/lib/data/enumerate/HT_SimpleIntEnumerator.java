package com.github.hokutomc.lib.data.enumerate;

/**
 * Created by user on 2015/02/13.
 */
public class HT_SimpleIntEnumerator<T extends HT_I_IntOrdered<T>> implements HT_I_IntOrderEnumerator<T> {

    private final T[] m_elements;

    @SafeVarargs
    public HT_SimpleIntEnumerator (T... elements){
        this.m_elements = elements;
    }

    @Override
    public T get (int ordinal) {
        for (T e : m_elements) {
            if (e.getIntId() == ordinal) return e;
        }
        return null;
    }
}
