package com.github.hokutomc.lib.nbt;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by user on 2015/06/10.
 */
public abstract class HT_NBTEvidence<T> {
    public abstract void write (String key, NBTTagCompound tagCompound, T value);

    public abstract T read (String key, NBTTagCompound tagCompound);

    public abstract String getTypeString ();


    public static final HT_NBTEvidence<Integer> INT = new HT_NBTEvidence<Integer>() {
        @Override
        public void write (String key, NBTTagCompound tagCompound, Integer value) {
            tagCompound.setInteger(key, value);
        }

        @Override
        public Integer read (String key, NBTTagCompound tagCompound) {
            return tagCompound.getInteger(key);
        }

        @Override
        public String getTypeString () {
            return "int";
        }
    };

    public static final HT_NBTEvidence<Short> SHORT = new HT_NBTEvidence<Short>() {
        @Override
        public void write (String key, NBTTagCompound tagCompound, Short value) {
            tagCompound.setShort(key, value);
        }

        @Override
        public Short read (String key, NBTTagCompound tagCompound) {
            return tagCompound.getShort(key);
        }

        @Override
        public String getTypeString () {
            return "short";
        }
    };

    public static final HT_NBTEvidence<Byte> BYTE = new HT_NBTEvidence<Byte>() {
        @Override
        public void write (String key, NBTTagCompound tagCompound, Byte value) {
            tagCompound.setByte(key, value);
        }

        @Override
        public Byte read (String key, NBTTagCompound tagCompound) {
            return tagCompound.getByte(key);
        }

        @Override
        public String getTypeString () {
            return "byte";
        }
    };

    public static final HT_NBTEvidence<Long> LONG = new HT_NBTEvidence<Long>() {
        @Override
        public void write (String key, NBTTagCompound tagCompound, Long value) {
            tagCompound.setLong(key, value);
        }

        @Override
        public Long read (String key, NBTTagCompound tagCompound) {
            return tagCompound.getLong(key);
        }

        @Override
        public String getTypeString () {
            return "long";
        }
    };

    public static final HT_NBTEvidence<String> STRING = new HT_NBTEvidence<String>() {
        @Override
        public void write (String key, NBTTagCompound tagCompound, String value) {
            tagCompound.setString(key, value);
        }

        @Override
        public String read (String key, NBTTagCompound tagCompound) {
            return tagCompound.getString(key);
        }

        @Override
        public String getTypeString () {
            return "String";
        }
    };

    public static final HT_NBTEvidence<Boolean> BOOLEAN = new HT_NBTEvidence<Boolean>() {
        @Override
        public void write (String key, NBTTagCompound tagCompound, Boolean value) {
            tagCompound.setBoolean(key, value);
        }

        @Override
        public Boolean read (String key, NBTTagCompound tagCompound) {
            return tagCompound.getBoolean(key);
        }

        @Override
        public String getTypeString () {
            return "boolean";
        }
    };

}
