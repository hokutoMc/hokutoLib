package com.github.hokutomc.lib.data.enumerate;

import com.github.hokutomc.lib.data.enumerate.HT_I_IntOrdered;

/**
 * Created by user on 2015/02/13.
 */
public interface HT_I_IntOrderEnumerator<T extends HT_I_IntOrdered> {
    T get(int ordinal);
}
