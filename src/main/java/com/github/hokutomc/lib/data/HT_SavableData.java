package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.nbt.HT_I_NBTData;
import com.github.hokutomc.lib.reflect.HT_Reflections;
import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import com.sun.istack.internal.NotNull;
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

    protected final String nbtKey;
    protected Class<P> type;
    protected Class<? extends Enum> flagType;
    private Object data;

    @SafeVarargs
    public HT_SavableData (String nbtKey, @NotNull P... empty) {
        this.nbtKey = nbtKey;
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
            HT_NBTUtil.writeEnumToNBT(nbtKey, nbtTagCompound, ((Enum) get(obj)));
        }
        if (type == Integer.class) {
            nbtTagCompound.setInteger(nbtKey, (Integer) get(obj));
        }
        if (type == Byte.class) {
            nbtTagCompound.setByte(nbtKey, (Byte) get(obj));
        }
        if (type == Float.class) {
            nbtTagCompound.setFloat(nbtKey, (Float) get(obj));
        }
        if (type == Short.class) {
            nbtTagCompound.setShort(nbtKey, (Short) get(obj));
        }
        if (type == String.class) {
            nbtTagCompound.setString(nbtKey, (String) get(obj));
        }
        if (type == int[].class) {
            nbtTagCompound.setIntArray(nbtKey, (int[]) get(obj));
        }
        if (type == byte[].class) {
            nbtTagCompound.setByteArray(nbtKey, (byte[]) get(obj));
        }
        if (type == Boolean.class) {
            nbtTagCompound.setBoolean(nbtKey, (Boolean) get(obj));
        }
        if (type == Double.class) {
            nbtTagCompound.setDouble(nbtKey, (Double) get(obj));
        }
        if (type == Long.class) {
            nbtTagCompound.setLong(nbtKey, (Long) get(obj));
        }
        if (type == ItemStack.class) {
            ((ItemStack) get(obj)).writeToNBT(nbtTagCompound);
        }
        if (EnumSet.class.isAssignableFrom(type)) {
            HT_NBTUtil.writeEnumSetToNBT(nbtKey, nbtTagCompound, (EnumSet) get(obj), flagType);
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
            update(entity, (P) HT_NBTUtil.readEnumFromNBT(nbtKey, nbtTagCompound, clazz));
        }
        if (type == Integer.class) {
            update(entity, (P) (Integer) nbtTagCompound.getInteger(nbtKey));
        }
        if (type == Byte.class) {
            update(entity, (P) (Byte) nbtTagCompound.getByte(nbtKey));
        }
        if (type == Float.class) {
            update(entity, (P) (Float) nbtTagCompound.getFloat(nbtKey));
        }
        if (type == Short.class) {
            update(entity, (P) (Short) nbtTagCompound.getShort(nbtKey));
        }
        if (type == String.class) {
            update(entity, (P) nbtTagCompound.getString(nbtKey));
        }
        if (type == int[].class) {
            update(entity, (P) nbtTagCompound.getIntArray(nbtKey));
        }
        if (type == byte[].class) {
            update(entity, (P) nbtTagCompound.getByteArray(nbtKey));
        }
        if (type == Boolean.class) {
            update(entity, (P) (Boolean) nbtTagCompound.getBoolean(nbtKey));
        }
        if (type == Double.class) {
            update(entity, (P) (Double) nbtTagCompound.getDouble(nbtKey));
        }
        if (type == Long.class) {
            update(entity, (P) (Long) nbtTagCompound.getLong(nbtKey));
        }
        if (type == ItemStack.class) {
            update(entity, (P) ItemStack.loadItemStackFromNBT(nbtTagCompound));
        }
        if (EnumSet.class.isAssignableFrom(type)) {
            if (flagType == null) {
                try {
                    throw new NBTException("Data of EnumSet should indicate the type of the set's elements. You can use HT_BasicObjectData.createEnumSetData()");
                } catch (NBTException e) {
                    e.printStackTrace();
                }
            }
            update(entity, (P) HT_NBTUtil.readEnumSetFromNBT(nbtKey, nbtTagCompound, flagType));
        }
        update(entity, (P) ItemStack.loadItemStackFromNBT(nbtTagCompound));
    }

    public String getNBTKey () {
        return nbtKey;
    }


}
