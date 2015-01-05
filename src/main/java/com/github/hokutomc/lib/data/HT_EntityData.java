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
    protected final boolean m_doSync;
    protected final int m_dwId;
    public final P m_initialValue;
    private P m_data;

    @SuppressWarnings("unchecked")
    public HT_EntityData (int dwId, String nbtKey, P initialValue) {
        super(nbtKey, initialValue);
        this.m_dwId = dwId;
        this.m_data = null;
        this.m_initialValue = initialValue;
        this.m_doSync = true;
    }

    @SuppressWarnings("unchecked")
    public HT_EntityData (String nbtKey, P initialValue) {
        super(nbtKey, initialValue);
        this.m_initialValue = initialValue;
        this.m_data = null;
        this.m_dwId = -1;
        this.m_doSync = false;
    }

    @Override
    protected void update (Entity entity, P property) {
        if (this.m_doSync) {
            if (type.isEnum()) {
                HT_DataWatcherUtil.updateEnum(entity, m_dwId, (Enum) property);
            } else if (property instanceof EnumSet) {
                Class clazz = type;
                HT_DataWatcherUtil.updateEnumSet(entity, m_dwId, (EnumSet) property, clazz);
            }
            entity.getDataWatcher().updateObject(m_dwId, property);
        } else {
            this.m_data = property;
        }

    }

    @SuppressWarnings("unchecked")
    protected P get (Entity entity) {
        if (this.m_doSync) {
            if (type.isEnum()) {
                return type.getEnumConstants()[entity.getDataWatcher().getWatchableObjectInt(m_dwId)];
            }
            if (type == Integer.class) {
                return (P) (Integer) entity.getDataWatcher().getWatchableObjectInt(m_dwId);
            }
            if (type == Byte.class) {
                return (P) (Byte) entity.getDataWatcher().getWatchableObjectByte(m_dwId);
            }
            if (type == Float.class) {
                return (P) (Float) entity.getDataWatcher().getWatchableObjectFloat(m_dwId);
            }
            if (type == Short.class) {
                return (P) (Short) entity.getDataWatcher().getWatchableObjectShort(m_dwId);
            }
            if (type == String.class) {
                return (P) entity.getDataWatcher().getWatchableObjectString(m_dwId);
            }
            if (type == ItemStack.class) {
                return (P) entity.getDataWatcher().getWatchableObjectItemStack(m_dwId);
            }
            if (EnumSet.class.isAssignableFrom(type)) {
                Class clazz = type;
                return (P) HT_DataWatcherUtil.getWatchableEnumSet(entity, m_dwId, clazz);
            }
        }
        return m_data;
    }

    public boolean getDoSync () {
        return this.m_doSync;
    }

    public int getDataWatchId () {
        return m_dwId;
    }
}
