package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.nbt.HT_I_NBTData;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

/**
 * Created by user on 2014/10/25.
 */
public class HT_ObjectProperties<E, P extends HT_SavableData<E, ?, ?>> implements HT_I_NBTData<HT_ObjectProperties>{

    protected final ArrayList<P> m_listData = Lists.newArrayList();

    public HT_ObjectProperties<E, P> addProperty (P property) {
        m_listData.add(property);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound, Object... objects) {
        this.HT_writeToNBT(nbtTagCompound, (E) objects[0]);
    }

    public void HT_writeToNBT (NBTTagCompound nbtTagCompound, E object) {
        for (P e : m_listData) {
            e.HT_writeToNBT(nbtTagCompound, object);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public HT_ObjectProperties HT_readFromNBT (NBTTagCompound nbtTagCompound, Object... objects) {
        this.HT_readFromNBT(nbtTagCompound, (E) objects[0]);
        return this;
    }

    public void HT_readFromNBT (NBTTagCompound nbtTagCompound, E object) {
        for (P e : m_listData) {
            e.HT_readFromNBT(nbtTagCompound, object);
        }
    }


}
