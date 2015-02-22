package com.github.hokutomc.lib.nbt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by user on 2015/02/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NBTByteArray {
    String value();
    byte[] defaultValue() default {};
}
