package com.github.hokutomc.lib.data.enumerate;

/**
 * Created by user on 2015/02/13.
 */
public interface HT_I_IntOrdered<T extends HT_I_IntOrdered<T>> {
    int getIntId ();

    HT_I_IntOrderEnumerator<T> getEnumerator ();
}
