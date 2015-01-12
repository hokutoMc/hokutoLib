package com.github.hokutomc.lib.nbt;

import com.github.hokutomc.lib.reflect.HT_Reflections;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.EnumSet;

/**
 * Created by user on 2014/12/06.
 */
public final class HT_NBTUtil {

    public static ItemStack[] readItemStacks (NBTTagCompound nbtTagCompound, int size) {
        NBTTagList itemTagList = nbtTagCompound.getTagList("items", Constants.NBT.TAG_COMPOUND);
        ItemStack[] contents = new ItemStack[size];

        for (int i = 0; i < itemTagList.tagCount(); i++) {
            NBTTagCompound itemTagCompound = itemTagList.getCompoundTagAt(i);

            int slotIndex = itemTagCompound.getInteger("slot");

            if (slotIndex >= 0 && slotIndex < contents.length) {
                contents[slotIndex] = ItemStack.loadItemStackFromNBT(itemTagCompound);
            }
        }
        return contents;
    }

    public static void writeItemStacks (NBTTagCompound nbtTagCompound, ItemStack[] itemStacks) {
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null) {
                NBTTagCompound itemTagCompound = new NBTTagCompound();
                itemTagCompound.setInteger("slot", i);
                itemStacks[i].writeToNBT(itemTagCompound);
                tagList.appendTag(itemTagCompound);
            }
        }

        nbtTagCompound.setTag("items", tagList);
    }

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

    public static <E extends Enum<E>> EnumSet<E> readEnumSetFromNBT (String key, NBTTagCompound nbtTagCompound, E... enumClass) {
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
    public static <E extends Enum<E>> void writeEnumSetToNBT (String key, NBTTagCompound nbtTagCompound, EnumSet<E> enumSet, Class<E> enumClass) {
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
    public static <E extends Enum<E>> void writeEnumSetToNBT (String key, NBTTagCompound nbtTagCompound, EnumSet<E> enumSet, E... enumClass) {
        writeEnumSetToNBT(key, nbtTagCompound, enumSet, HT_Reflections.getClass(enumClass));
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> readClassFromNBT (String key, NBTTagCompound nbtTagCompound, T... empty) {
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
