package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.nbt.HT_I_NBTData;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

/**
 * Created by user on 2014/10/25.
 */
@Deprecated
public class HT_ObjectProperties<E, P extends HT_SavableData<E, ?, ?>> implements HT_I_NBTData<HT_ObjectProperties>{

    protected final ArrayList<P> datas = Lists.newArrayList();

    public void addProperty (P property) {
        datas.add(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound, Object... objects) {
        this.HT_writeToNBT(nbtTagCompound, (E) objects[0]);
    }

    public void HT_writeToNBT (NBTTagCompound nbtTagCompound, E object) {
//        NBTTagList tagList = new NBTTagList();
//
//        for (int i = 0; i < this.datas.size(); i++) {
//            if (datas.get(i) != null) {
//                NBTTagCompound itemTagCompound = new NBTTagCompound();
//                this.datas.get(i).HT_writeToNBT(itemTagCompound);
//                tagList.appendTag(itemTagCompound);
//            }
//        }
//
//        nbtTagCompound.setTag("props", tagList);
        for (P e : datas) {
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
//        for (int i = 0; i < this.datas.size(); i++) {
//            NBTTagCompound propTagCompound = tagList.getCompoundTagAt(i);
//
//            this.datas.get(i).HT_writeToNBT(propTagCompound, object);
//
//        }
        for (P e : datas) {
            e.HT_readFromNBT(nbtTagCompound, object);
        }
    }


}
