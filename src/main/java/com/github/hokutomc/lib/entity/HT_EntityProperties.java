package com.github.hokutomc.lib.entity;

import com.github.hokutomc.lib.data.HT_EntityData;
import com.github.hokutomc.lib.data.HT_ObjectProperties;
import net.minecraft.entity.Entity;

/**
 * Created by user on 2014/12/07.
 */
@Deprecated
public class HT_EntityProperties extends HT_ObjectProperties<Entity, HT_EntityData<Entity>> {

    @Override
    public void addProperty (HT_EntityData property) {
        if (property.getDoSync()) {
            for (HT_EntityData e : this.m_listData) {
                if (property.getDataWatchId() == e.getDataWatchId()){
                    throw new IllegalArgumentException("conflicting of data-watching id ( key : " + property.getNBTKey() + ", id : " + property.getDataWatchId());
                }
            }
        }
        super.addProperty(property);
    }

    public void init(Entity entity) {
        for (HT_EntityData e : this.m_listData) {
            if (e.getDoSync()) entity.getDataWatcher().addObject(e.getDataWatchId(), e.m_initialValue);
        }
    }


}
