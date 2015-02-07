package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.nbt.HT_I_NBTData;
import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import com.github.hokutomc.lib.reflect.HT_Reflections;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import java.util.EnumSet;

/**
 * Created by user on 2014/12/08.
 */
@Deprecated
public abstract class HT_SavableData<E, P, S extends HT_SavableData<E, P, S>>
        implements HT_I_NBTData<HT_SavableData>{

    protected final String m_nbtKey;
    protected Class<P> type;
    protected Class<? extends Enum> m_flagType;
    private Object m_data;

    @SafeVarargs
    public HT_SavableData (String nbtKey, P... empty) {
        this.m_nbtKey = nbtKey;
        this.type = HT_Reflections.getClass(empty);
    }



    protected abstract void update (E entity, P property);

    protected abstract P get (E entity);

    @Override
    @SuppressWarnings("unchecked")
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound, Object... objects) {
        this.HT_writeToNBT(nbtTagCompound, (E) objects[0]);
    }

    @SuppressWarnings("unchecked")
    protected void HT_writeToNBT (NBTTagCompound nbtTagCompound, E obj) {
        if (type.isEnum()) {
            HT_NBTUtil.writeEnum(m_nbtKey, nbtTagCompound, ((Enum) get(obj)));
        }
        if (type == Integer.class) {
            nbtTagCompound.setInteger(m_nbtKey, (Integer) get(obj));
        }
        if (type == Byte.class) {
            nbtTagCompound.setByte(m_nbtKey, (Byte) get(obj));
        }
        if (type == Float.class) {
            nbtTagCompound.setFloat(m_nbtKey, (Float) get(obj));
        }
        if (type == Short.class) {
            nbtTagCompound.setShort(m_nbtKey, (Short) get(obj));
        }
        if (type == String.class) {
            nbtTagCompound.setString(m_nbtKey, (String) get(obj));
        }
        if (type == int[].class) {
            nbtTagCompound.setIntArray(m_nbtKey, (int[]) get(obj));
        }
        if (type == byte[].class) {
            nbtTagCompound.setByteArray(m_nbtKey, (byte[]) get(obj));
        }
        if (type == Boolean.class) {
            nbtTagCompound.setBoolean(m_nbtKey, (Boolean) get(obj));
        }
        if (type == Double.class) {
            nbtTagCompound.setDouble(m_nbtKey, (Double) get(obj));
        }
        if (type == Long.class) {
            nbtTagCompound.setLong(m_nbtKey, (Long) get(obj));
        }
        if (type == ItemStack.class) {
            ((ItemStack) get(obj)).writeToNBT(nbtTagCompound);
        }
        if (EnumSet.class.isAssignableFrom(type)) {
            HT_NBTUtil.writeEnumSet(m_nbtKey, nbtTagCompound, (EnumSet) get(obj), m_flagType);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public HT_SavableData HT_readFromNBT (NBTTagCompound nbtTagCompound, Object... objects) {
        this.HT_readFromNBT(nbtTagCompound, (E) objects[0]);
        return this;
    }

    @SuppressWarnings("unchecked")
    protected void HT_readFromNBT (NBTTagCompound nbtTagCompound, E entity){
        if (type.isEnum()) {
            Class clazz = type;
            update(entity, (P) HT_NBTUtil.readEnum(m_nbtKey, nbtTagCompound, clazz));
        }
        if (type == Integer.class) {
            update(entity, (P) (Integer) nbtTagCompound.getInteger(m_nbtKey));
        }
        if (type == Byte.class) {
            update(entity, (P) (Byte) nbtTagCompound.getByte(m_nbtKey));
        }
        if (type == Float.class) {
            update(entity, (P) (Float) nbtTagCompound.getFloat(m_nbtKey));
        }
        if (type == Short.class) {
            update(entity, (P) (Short) nbtTagCompound.getShort(m_nbtKey));
        }
        if (type == String.class) {
            update(entity, (P) nbtTagCompound.getString(m_nbtKey));
        }
        if (type == int[].class) {
            update(entity, (P) nbtTagCompound.getIntArray(m_nbtKey));
        }
        if (type == byte[].class) {
            update(entity, (P) nbtTagCompound.getByteArray(m_nbtKey));
        }
        if (type == Boolean.class) {
            update(entity, (P) (Boolean) nbtTagCompound.getBoolean(m_nbtKey));
        }
        if (type == Double.class) {
            update(entity, (P) (Double) nbtTagCompound.getDouble(m_nbtKey));
        }
        if (type == Long.class) {
            update(entity, (P) (Long) nbtTagCompound.getLong(m_nbtKey));
        }
        if (type == ItemStack.class) {
            update(entity, (P) ItemStack.loadItemStackFromNBT(nbtTagCompound));
        }
        if (EnumSet.class.isAssignableFrom(type)) {
            if (m_flagType == null) {
                try {
                    throw new NBTException("Data of EnumSet should indicate the type of the set's elements. You can use HT_BasicObjectData.createEnumSetData()");
                } catch (NBTException e) {
                    e.printStackTrace();
                }
            }
            update(entity, (P) HT_NBTUtil.readEnumSet(m_nbtKey, nbtTagCompound, m_flagType));
        }
        update(entity, (P) ItemStack.loadItemStackFromNBT(nbtTagCompound));
    }

    public String getNBTKey () {
        return m_nbtKey;
    }


}
