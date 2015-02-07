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

    public void addProperty (P property) {
        m_listData.add(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound, Object... objects) {
        this.HT_writeToNBT(nbtTagCompound, (E) objects[0]);
    }

    public void HT_writeToNBT (NBTTagCompound nbtTagCompound, E object) {
//        NBTTagList tagList = new NBTTagList();
//
//        for (int i = 0; i < this.m_listData.size(); i++) {
//            if (m_listData.get(i) != null) {
//                NBTTagCompound itemTagCompound = new NBTTagCompound();
//                this.m_listData.get(i).HT_writeToNBT(itemTagCompound);
//                tagList.appendTag(itemTagCompound);
//            }
//        }
//
//        nbtTagCompound.setTag("props", tagList);
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
//        NBTTagList tagList = nbtTagCompound.getTagList("props", Constants.NBT.TAG_COMPOUND);
//
//        for (int i = 0; i < this.m_listData.size(); i++) {
//            NBTTagCompound propTagCompound = tagList.getCompoundTagAt(i);
//
//            this.m_listData.get(i).HT_writeToNBT(propTagCompound, object);
//
//        }
        for (P e : m_listData) {
            e.HT_readFromNBT(nbtTagCompound, object);
        }
    }


}
