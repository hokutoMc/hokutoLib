package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.data.enumerate.HT_I_IntOrdered;
import com.github.hokutomc.lib.data.enumerate.HT_I_StringOrdered;
import com.github.hokutomc.lib.nbt.HT_I_NBTData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import java.util.EnumSet;

import static com.github.hokutomc.lib.nbt.HT_NBTUtil.*;

/**
 * Created by user on 2014/12/08.
 */
public abstract class HT_SavableData<E, P, S extends HT_SavableData<E, P, S>>
        implements HT_I_NBTData<HT_SavableData>{

    protected final String m_nbtKey;
    public final P m_initial;
    protected Class<P> m_type;
    protected Class<? extends Enum> m_flagType;

    @SuppressWarnings("unchecked")
    public HT_SavableData (E entity, String nbtKey, P initial) {
        this.m_initial = initial;
        this.m_nbtKey = nbtKey;
        this.m_type = (Class<P>) initial.getClass();
        this.update(entity, initial);
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
        if (m_initial instanceof HT_I_StringOrdered) {
            writeStringOrdered(m_nbtKey, nbtTagCompound, (HT_I_StringOrdered) get(obj));
        } else if (m_initial instanceof HT_I_IntOrdered) {
            writeIntOrdered(m_nbtKey, nbtTagCompound, (HT_I_IntOrdered) get(obj));
        } else if (m_type.isEnum()) {
            writeEnum(m_nbtKey, nbtTagCompound, (Enum) get(obj));
        } else if (m_type == Integer.class) {
            nbtTagCompound.setInteger(m_nbtKey, (Integer) get(obj));
        } else if (m_type == Byte.class) {
            nbtTagCompound.setByte(m_nbtKey, (Byte) get(obj));
        } else if (m_type == Float.class) {
            nbtTagCompound.setFloat(m_nbtKey, (Float) get(obj));
        } else if (m_type == Short.class) {
            nbtTagCompound.setShort(m_nbtKey, (Short) get(obj));
        } else if (m_type == String.class) {
            nbtTagCompound.setString(m_nbtKey, (String) get(obj));
        } else if (m_type == int[].class) {
            nbtTagCompound.setIntArray(m_nbtKey, (int[]) get(obj));
        } else if (m_type == byte[].class) {
            nbtTagCompound.setByteArray(m_nbtKey, (byte[]) get(obj));
        } else if (m_type == Boolean.class) {
            nbtTagCompound.setBoolean(m_nbtKey, (Boolean) get(obj));
        } else if (m_type == Double.class) {
            nbtTagCompound.setDouble(m_nbtKey, (Double) get(obj));
        } else if (m_type == Long.class) {
            nbtTagCompound.setLong(m_nbtKey, (Long) get(obj));
        } else if (m_type == ItemStack.class) {
            ((ItemStack) get(obj)).writeToNBT(nbtTagCompound);
        } else if (m_type == ItemStack[].class) {
            writeItemStacks(nbtTagCompound, (ItemStack[]) get(obj));
        } else if (m_initial instanceof EnumSet) {
            writeEnumSet(m_nbtKey, nbtTagCompound, (EnumSet) get(obj), m_flagType);
        }

    }

    /**
     *
     * @param nbtTagCompound
     * @param objects 1st : entity data, 2nd : int order enumerator (if data is of HT_I_IntOrdered).
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public HT_SavableData HT_readFromNBT (NBTTagCompound nbtTagCompound, Object... objects) {
        if (m_initial instanceof HT_I_IntOrdered || m_initial instanceof HT_I_StringOrdered) {
            HT_readFromNBT(nbtTagCompound, (E)objects[0], objects[1]);
        } else {
            this.HT_readFromNBT(nbtTagCompound, (E) objects[0]);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    public void HT_readFromNBT (NBTTagCompound nbtTagCompound, E entity, Object object) {

    }

    @SuppressWarnings("unchecked")
    public void HT_readFromNBT (NBTTagCompound nbtTagCompound, E entity){
        if (m_initial instanceof HT_I_StringOrdered) {
            update(entity, (P) getStringOrdered(m_nbtKey, nbtTagCompound, (HT_I_StringOrdered) m_initial));
        } else if (m_initial instanceof HT_I_IntOrdered) {
            update(entity, (P) getIntOrdered(m_nbtKey, nbtTagCompound, (HT_I_IntOrdered) m_initial));
        } else if (m_type.isEnum()) {
            update(entity, (P) getEnum(m_nbtKey, nbtTagCompound, (Enum) m_initial));
        } else if (m_type == Integer.class) {
            update(entity, (P) (Integer) getInteger(m_nbtKey, nbtTagCompound, (Integer) m_initial));
        } else if (m_type == Byte.class) {
            update(entity, (P) (Byte) getByte(m_nbtKey, nbtTagCompound, (Byte) m_initial));
        } else if (m_type == Float.class) {
            update(entity, (P) (Float) getFloat(m_nbtKey, nbtTagCompound, (Float) m_initial));
        } else if (m_type == Short.class) {
            update(entity, (P) (Short) getShort(m_nbtKey, nbtTagCompound, (Short) m_initial));
        } else if (m_type == String.class) {
            update(entity, (P) getString(m_nbtKey, nbtTagCompound, (String) m_initial));
        } else if (m_type == int[].class) {
            update(entity, (P) getIntArray(m_nbtKey, nbtTagCompound, (int[]) m_initial));
        } else if (m_type == byte[].class) {
            update(entity, (P) getByteArray(m_nbtKey, nbtTagCompound, (byte[]) m_initial));
        } else if (m_type == Boolean.class) {
            update(entity, (P) (Boolean) getBoolean(m_nbtKey, nbtTagCompound, (Boolean) m_initial));
        } else if (m_type == Double.class) {
            update(entity, (P) (Double) getDouble(m_nbtKey, nbtTagCompound, (Double) m_initial));
        } else if (m_type == Long.class) {
            update(entity, (P) (Long) getLong(m_nbtKey, nbtTagCompound, (Long) m_initial));
        } else if (m_type == ItemStack.class) {
            update(entity, (P) getItemStack(m_nbtKey, nbtTagCompound, (ItemStack) m_initial));
        } else if (m_type == ItemStack[].class) {
            update(entity, (P) getItemStackArray(nbtTagCompound, ((ItemStack[]) m_initial).length, (ItemStack[]) m_initial));
        } else if (EnumSet.class.isAssignableFrom(m_type)) {
            if (m_flagType == null) {
                try {
                    throw new NBTException("Data of EnumSet should indicate the m_type of the set's elements. You can use HT_BasicObjectData.createEnumSetData()");
                } catch (NBTException e) {
                    e.printStackTrace();
                }
            }
            update(entity, (P) getEnumSet(m_nbtKey, nbtTagCompound, m_flagType, (EnumSet)m_initial));
        }

    }

    public String getNBTKey () {
        return m_nbtKey;
    }


}
