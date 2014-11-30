package com.github.hokutomc.lib.entity;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by user on 2014/10/25.
 */
@Deprecated
public class HT_EntityData {

    private WatchableData[] datas;

    public HT_EntityData (WatchableData... datas) {
        this.datas = datas;
    }

    public static <T> WatchableData<T> createWatchedData (int dataWatchId, String nbtId, Class<T> type) {
        return new WatchableData<T>(true, dataWatchId, nbtId, type);
    }

    public static <T> WatchableData<T> createServerOnlyData (String nbtId, Class<T> type) {
        return new WatchableData<T>(false, -1, nbtId, type);
    }

    private static class WatchableData<T> {
        private final boolean doSync;
        private final int dwId;
        private final String nbtKey;
        private Class type;
        private T data;

        private WatchableData (boolean doSync, int dwId, String nbtKey, Class<T> type) {
            this.doSync = doSync;
            this.dwId = dwId;
            this.nbtKey = nbtKey;
            this.type = type;
        }

        private void update (Entity entity, T object) {
            if (this.doSync) {
                entity.getDataWatcher().updateObject(dwId, object);
            } else {
                this.data = object;
            }
        }

        @SuppressWarnings("unchecked")
        private T get (Entity entity) {
            if (this.doSync) {
                if (type == Integer.class) {
                    return (T) (Integer) entity.getDataWatcher().getWatchableObjectInt(dwId);
                }
                if (type == Byte.class) {
                    return (T) (Byte) entity.getDataWatcher().getWatchableObjectByte(dwId);
                }
                if (type == Float.class) {
                    return (T) (Float) entity.getDataWatcher().getWatchableObjectFloat(dwId);
                }
                if (type == Short.class) {
                    return (T) (Short) entity.getDataWatcher().getWatchableObjectShort(dwId);
                }
                if (type == String.class) {
                    return (T) entity.getDataWatcher().getWatchableObjectString(dwId);
                }
                return (T) entity.getDataWatcher().getWatchableObjectItemStack(dwId);
            }
            return data;
        }

        private void write (Entity entity, NBTTagCompound nbtTagCompound) {
            if (type == Integer.class) {
                nbtTagCompound.setInteger(nbtKey, (Integer) get(entity));
            }
            if (type == Byte.class) {
                nbtTagCompound.setByte(nbtKey, (Byte) get(entity));
            }
            if (type == Float.class) {
                nbtTagCompound.setFloat(nbtKey, (Float) get(entity));
            }
            if (type == Short.class) {
                nbtTagCompound.setShort(nbtKey, (Short) get(entity));
            }
            if (type == String.class) {
                nbtTagCompound.setString(nbtKey, (String) get(entity));
            }
            if (type == int[].class) {
                nbtTagCompound.setIntArray(nbtKey, (int[]) get(entity));
            }
            if (type == byte[].class) {
                nbtTagCompound.setByteArray(nbtKey, (byte[]) get(entity));
            }
            if (type == Boolean.class) {
                nbtTagCompound.setBoolean(nbtKey, (Boolean) get(entity));
            }
            if (type == Double.class) {
                nbtTagCompound.setDouble(nbtKey, (Double) get(entity));
            }
            if (type == Long.class) {
                nbtTagCompound.setLong(nbtKey, (Long) get(entity));
            }
            ((ItemStack) get(entity)).writeToNBT(nbtTagCompound);
        }

        @SuppressWarnings("unchecked")
        private void read (Entity entity, NBTTagCompound nbtTagCompound) {
            if (type == Integer.class) {
                update(entity, (T) (Integer) nbtTagCompound.getInteger(nbtKey));
            }
            if (type == Byte.class) {
                update(entity, (T) (Byte) nbtTagCompound.getByte(nbtKey));
            }
            if (type == Float.class) {
                update(entity, (T) (Float) nbtTagCompound.getFloat(nbtKey));
            }
            if (type == Short.class) {
                update(entity, (T) (Short) nbtTagCompound.getShort(nbtKey));
            }
            if (type == String.class) {
                update(entity, (T) nbtTagCompound.getString(nbtKey));
            }
            if (type == int[].class) {
                update(entity, (T) nbtTagCompound.getIntArray(nbtKey));
            }
            if (type == byte[].class) {
                update(entity, (T) nbtTagCompound.getByteArray(nbtKey));
            }
            if (type == Boolean.class) {
                update(entity, (T) (Boolean) nbtTagCompound.getBoolean(nbtKey));
            }
            if (type == Double.class) {
                update(entity, (T) (Double) nbtTagCompound.getDouble(nbtKey));
            }
            if (type == Long.class) {
                update(entity, (T) (Long) nbtTagCompound.getLong(nbtKey));
            }
            update(entity, (T) ItemStack.loadItemStackFromNBT(nbtTagCompound));
        }
    }
}
