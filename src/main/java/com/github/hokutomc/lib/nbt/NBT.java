package com.github.hokutomc.lib.nbt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by user on 2015/02/17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NBT {
    String value();
}
