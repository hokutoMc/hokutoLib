package com.github.hokutomc.lib.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by user on 2015/04/25.
 */
public class HT_IntFlag {

    private static final List<HT_IntFlag> list = Lists.newArrayList();

    static {
        for (int i = 0; i < 32; i++) {
            list.add(new HT_IntFlag(i));
        }
    }

    public static HT_IntFlag of (int i) {
        if (i >= 32 || i < 0) throw new IllegalArgumentException("the parameter should be in range of 0-31");
        return list.get(i);
    }

    private final int value;

    public HT_IntFlag (int i) {
        this.value = 1 << i;
    }

    public boolean apply (int fields) {
        return (fields & this.value) != 0;
    }

    public int set (boolean bool, int fields) {
        return bool ? fields | this.value : fields & ~this.value;
    }
}
