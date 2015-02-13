package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.data.enumerate.HT_I_IntOrdered;
import com.github.hokutomc.lib.data.enumerate.HT_I_StringOrdered;
import com.github.hokutomc.lib.entity.HT_DataWatcherUtil;
import com.github.hokutomc.lib.reflect.HT_Reflections;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

/**
 * Created by user on 2014/12/07.
 */
public class HT_EntityData<P> extends HT_SavableData<Entity, P, HT_EntityData<P>> {
    protected final boolean m_doSync;
    protected final int m_dwId;
    private P m_data;

    @SuppressWarnings("unchecked")
    public HT_EntityData (Entity entity, int dwId, String nbtKey, P initialValue) {
        super(entity, nbtKey, initialValue);
        this.m_dwId = dwId;
        this.m_data = null;
        this.m_doSync = true;
    }

    @SuppressWarnings("unchecked")
    public HT_EntityData (Entity entity, String nbtKey, P initialValue) {
        super(entity, nbtKey, initialValue);
        this.m_data = null;
        this.m_dwId = -1;
        this.m_doSync = false;
    }

    @SafeVarargs
    public static <R extends Enum<R>> HT_EntityData<EnumSet<R>> createEnumSetData (Entity entity, String nbtKey, EnumSet<R> initial, R... empty) {
        HT_EntityData<EnumSet<R>> data = new HT_EntityData<>(entity, nbtKey, initial);
        data.m_flagType = HT_Reflections.getClass(empty);
        return data;
    }

    @SafeVarargs
    public static <R extends Enum<R>> HT_EntityData<EnumSet<R>> createEnumSetData (Entity entity, int dwid, String nbtKey, EnumSet<R> initial, R... empty) {
        HT_EntityData<EnumSet<R>> data = new HT_EntityData<>(entity, dwid, nbtKey, initial);
        data.m_flagType = HT_Reflections.getClass(empty);
        return data;
    }

    @Override
    protected void update (Entity entity, P property) {
        if (this.m_doSync) {
            if (property instanceof HT_I_StringOrdered) {
                HT_DataWatcherUtil.updateStringOrdered(entity, m_dwId, (HT_I_StringOrdered) property);
            } else if (property instanceof HT_I_IntOrdered) {
                HT_DataWatcherUtil.updateIntOrdered(entity, m_dwId, (HT_I_IntOrdered)property);
            } else if (m_type.isEnum()) {
                HT_DataWatcherUtil.updateEnum(entity, m_dwId, (Enum) property);
            } else if (property instanceof EnumSet) {
                Class clazz = m_type;
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
            if (m_data instanceof HT_I_StringOrdered) {
                return (P) HT_DataWatcherUtil.getWatchableStringOrdered(entity, m_dwId, ((HT_I_StringOrdered) m_data).getEnumerator());
            } else if (m_data instanceof HT_I_IntOrdered) {
                return (P) HT_DataWatcherUtil.getWatchableIntOrdered(entity, m_dwId, ((HT_I_IntOrdered) m_data).getEnumerator());
            } else if (m_type.isEnum()) {
                return m_type.getEnumConstants()[entity.getDataWatcher().getWatchableObjectInt(m_dwId)];
            } else if (m_type == Integer.class) {
                return (P) (Integer) entity.getDataWatcher().getWatchableObjectInt(m_dwId);
            } else if (m_type == Byte.class) {
                return (P) (Byte) entity.getDataWatcher().getWatchableObjectByte(m_dwId);
            } else if (m_type == Float.class) {
                return (P) (Float) entity.getDataWatcher().getWatchableObjectFloat(m_dwId);
            } else if (m_type == Short.class) {
                return (P) (Short) entity.getDataWatcher().getWatchableObjectShort(m_dwId);
            } else if (m_type == String.class) {
                return (P) entity.getDataWatcher().getWatchableObjectString(m_dwId);
            } else if (m_type == ItemStack.class) {
                return (P) entity.getDataWatcher().getWatchableObjectItemStack(m_dwId);
            } else if (EnumSet.class.isAssignableFrom(m_type)) {
                Class clazz = m_type;
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
