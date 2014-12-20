package com.github.hokutomc.lib.nbt;

import com.github.hokutomc.lib.reflect.HT_Reflections;
import com.sun.istack.internal.NotNull;
import net.minecraft.nbt.NBTTagCompound;

import java.util.EnumSet;

/**
 * Created by user on 2014/12/06.
 */
public final class HT_NBTUtil {

    /**
     * Read enum constant by its ordinal.
     * @param key
     * @param nbtTagCompound
     * @param enumClass
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> E readEnumFromNBT (String key, NBTTagCompound nbtTagCompound, Class<E> enumClass) {
        return enumClass.getEnumConstants()[nbtTagCompound.getInteger(key)];
    }

    /**
     * Write enum constant by its ordinal.
     * @param key
     * @param nbtTagCompound
     * @param enumConst
     * @param <E>
     */
    public static <E extends Enum<E>> void writeEnumToNBT (String key, NBTTagCompound nbtTagCompound, E enumConst) {
        nbtTagCompound.setInteger(key, enumConst.ordinal());
    }

    /**
     * Read EnumSet using their ordinals.
     * @param key
     * @param nbtTagCompound
     * @param enumClass
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> EnumSet<E> readEnumSetFromNBT (String key, NBTTagCompound nbtTagCompound, Class<E> enumClass) {
        EnumSet<E> enumSet = EnumSet.noneOf(enumClass);
        int[] intArray = nbtTagCompound.getIntArray(key);
        for (int index = 0; index * 32 < enumClass.getEnumConstants().length && index < intArray.length; index++) {
            for (int i = 0; index * 32 + i < enumClass.getEnumConstants().length; i++) {
                if (1 == ((intArray[index] >> i) & 0x00000001)) {
                    enumSet.add(enumClass.getEnumConstants()[index * 32 + i]);
                }
            }
        }
        return enumSet;
    }

    public static <E extends Enum<E>> EnumSet<E> readEnumSetFromNBT (String key, NBTTagCompound nbtTagCompound, @NotNull E... enumClass) {
        return readEnumSetFromNBT(key, nbtTagCompound, HT_Reflections.getClass(enumClass));
    }

    /**
     * Write EnumSet using their ordinals.
     * @param key
     * @param nbtTagCompound
     * @param enumSet
     * @param enumClass
     * @param <E>
     */
    public static <E extends Enum<E>> void writeEnumSetToNBT (String key, NBTTagCompound nbtTagCompound, @NotNull EnumSet<E> enumSet, @NotNull Class<E> enumClass) {
        int length = enumClass.getEnumConstants().length / 32 + 1;
        int[] intArray = new int[length];
        for (int index = 0; index < length; index++) {
            int current = 0;
            for (int i = 0; i < 32; i++) {
                if (enumSet.contains(enumClass.getEnumConstants()[i])){
                    current += pow2(i);
                }
                if (enumSet.size() < index * 32 + i) break;
            }
            intArray[index] = current;
        }
        nbtTagCompound.setIntArray(key, intArray);
    }

    public static int pow2(int exponent) {
        int num = 1;
        for (int i = 0; i < exponent; i++) {
            num *= 2;
        }
        return num;
    }

    @SafeVarargs
    public static <E extends Enum<E>> void writeEnumSetToNBT (String key, NBTTagCompound nbtTagCompound, EnumSet<E> enumSet, @NotNull E... enumClass) {
        writeEnumSetToNBT(key, nbtTagCompound, enumSet, HT_Reflections.getClass(enumClass));
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> readClassFromNBT (String key, NBTTagCompound nbtTagCompound, @NotNull T... empty) {
        try {
            Class clazz = Class.forName(nbtTagCompound.getString(key));
            if (HT_Reflections.getClass(empty).isAssignableFrom(clazz)){
                return (Class<T>)clazz;
            }
            throw new ClassCastException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeClassToNBT (String key, NBTTagCompound nbtTagCompound, Class clazz) {
        nbtTagCompound.setString(key, clazz.getName());
    }


}
