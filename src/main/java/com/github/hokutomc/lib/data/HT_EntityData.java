package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.entity.HT_DataWatcherUtil;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

/**
 * Created by user on 2014/12/07.
 */
@Deprecated
public class HT_EntityData<P> extends HT_SavableData<Entity, P, HT_EntityData<P>> {
    protected final boolean doSync;
    protected final int dwId;
    public final P initialValue;
    private P data;

    @SuppressWarnings("unchecked")
    public HT_EntityData (int dwId, String nbtKey, P initialValue) {
        super(nbtKey, initialValue);
        this.dwId = dwId;
        this.data = null;
        this.initialValue = initialValue;
        this.doSync = true;
    }

    @SuppressWarnings("unchecked")
    public HT_EntityData (String nbtKey, P initialValue) {
        super(nbtKey, initialValue);
        this.initialValue = initialValue;
        this.data = null;
        this.dwId = -1;
        this.doSync = false;
    }

    @Override
    protected void update (Entity entity, P property) {
        if (this.doSync) {
            if (type.isEnum()) {
                HT_DataWatcherUtil.updateEnum(entity, dwId, (Enum) property);
            } else if (property instanceof EnumSet) {
                Class clazz = type;
                HT_DataWatcherUtil.updateEnumSet(entity, dwId, (EnumSet) property, clazz);
            }
            entity.getDataWatcher().updateObject(dwId, property);
        } else {
            this.data = property;
        }

    }

    @SuppressWarnings("unchecked")
    protected P get (Entity entity) {
        if (this.doSync) {
            if (type.isEnum()) {
                return type.getEnumConstants()[entity.getDataWatcher().getWatchableObjectInt(dwId)];
            }
            if (type == Integer.class) {
                return (P) (Integer) entity.getDataWatcher().getWatchableObjectInt(dwId);
            }
            if (type == Byte.class) {
                return (P) (Byte) entity.getDataWatcher().getWatchableObjectByte(dwId);
            }
            if (type == Float.class) {
                return (P) (Float) entity.getDataWatcher().getWatchableObjectFloat(dwId);
            }
            if (type == Short.class) {
                return (P) (Short) entity.getDataWatcher().getWatchableObjectShort(dwId);
            }
            if (type == String.class) {
                return (P) entity.getDataWatcher().getWatchableObjectString(dwId);
            }
            if (type == ItemStack.class) {
                return (P) entity.getDataWatcher().getWatchableObjectItemStack(dwId);
            }
            if (EnumSet.class.isAssignableFrom(type)) {
                Class clazz = type;
                return (P) HT_DataWatcherUtil.getWatchableEnumSet(entity, dwId, clazz);
            }
        }
        return data;
    }

    public boolean getDoSync () {
        return this.doSync;
    }

    public int getDataWatchId () {
        return dwId;
    }
}
