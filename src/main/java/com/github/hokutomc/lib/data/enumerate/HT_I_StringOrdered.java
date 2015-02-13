package com.github.hokutomc.lib.data.enumerate;

/**
 * Created by user on 2015/02/13.
 */
public interface HT_I_StringOrdered<T extends HT_I_StringOrdered<T>> {
    HT_I_StringOrderEnumerator<T> getEnumerator ();
}
