package com.github.hokutomc.lib.entity;

import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import net.minecraft.entity.Entity;

import java.util.EnumSet;

/**
 * Created by user on 2014/12/07.
 */
public final class HT_DataWatcherUtil {

    public static <E> boolean isWatchableDataType (Class<E> clazz) {
        return clazz == int.class || clazz == Integer.class
                || clazz == byte.class || clazz == Byte.class
                || clazz == short.class || clazz == Short.class
                || clazz == float.class || clazz == Float.class
                || clazz == String.class || clazz.isEnum()
                || clazz.isAssignableFrom(EnumSet.class);
    }

    public static <E extends Enum<E>> E getWatchableEnum (Entity entity, int dwId, Class<E> enumClass) {
        return enumClass.getEnumConstants()[entity.getDataWatcher().getWatchableObjectInt(dwId)];
    }

    public static <E extends Enum<E>> void updateEnum (Entity entity, int dwId, E constant) {
        entity.getDataWatcher().updateObject(dwId, constant.ordinal());
    }

    /**
     * The enumeration class should have 32 fields or less.
     * @param entity
     * @param dwId
     * @param enumClass
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> EnumSet<E> getWatchableEnumSet (Entity entity, int dwId, Class<E> enumClass) {
        EnumSet<E> enumSet = EnumSet.noneOf(enumClass);
        int bits = entity.getDataWatcher().getWatchableObjectInt(dwId);
        for (int i = 0; i < 32 && i < enumClass.getEnumConstants().length; i++) {
            if (1 == ((bits >> i) & 0x00000001)) {
                enumSet.add(enumClass.getEnumConstants()[i]);
            }
        }
        return enumSet;
    }

    /**
     * The enumeration class should have 32 fields or less.
     * @param entity
     * @param dwId
     * @param enumSet
     * @param enumClass
     * @param <E>
     */
    public static <E extends Enum<E>> void updateEnumSet (Entity entity, int dwId, EnumSet<E> enumSet, Class<E> enumClass) {
        int bits = 0;
        for (int i = 0; i < 32 && i < enumClass.getEnumConstants().length; i++) {
            if (enumSet.contains(enumClass.getEnumConstants()[i])) {
                bits += HT_NBTUtil.pow2(i);
            }
        }
        entity.getDataWatcher().updateObject(dwId, bits);
    }
}
