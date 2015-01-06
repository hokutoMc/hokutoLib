package com.github.hokutomc.lib.entity;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.item.HT_ItemMobEgg;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user on 2015/01/06.
 */
public class HT_ModEntityList implements Iterable<Class<? extends Entity>>{


    private class HT_EntityRegistration {
        private Class<? extends Entity> m_class;
        private String m_name;

        private HT_EntityRegistration (Class<? extends Entity> classEntity, String name) {
            this.m_class = classEntity;
            this.m_name = name;
        }
    }

    private ArrayList<HT_EntityRegistration> m_listRegistrations;
    private Object m_modObj;

    public HT_ModEntityList (Object mod, String modid) {
        m_listRegistrations = new ArrayList<>();
        this.m_modObj = mod;
        new HT_ItemMobEgg(modid, this).register();
    }

    public HT_ModEntityList register(Class<? extends Entity> classEntity, String name) {
        HT_Registries.registerEntity(classEntity, name, add(classEntity, name), this.m_modObj);
        return this;
    }

    private int add (Class<? extends Entity> classEntity, String name) {
        HT_EntityRegistration registration = new HT_EntityRegistration(classEntity, name);
        this.m_listRegistrations.add(registration);
        return this.m_listRegistrations.indexOf(registration);
    }

    public Class<? extends Entity> getClassAt (int index) {
        return this.m_listRegistrations.get(index).m_class;
    }

    public String getNameAt (int index) {
        return this.m_listRegistrations.get(index).m_name;
    }

    public Entity createEntityById (int id, World world) {
        Class<? extends Entity> classEntity = this.getClassAt(id);
        try {
            return classEntity.getConstructor(World.class).newInstance(world);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterator<Class<? extends Entity>> iterator () {
        ArrayList<Class<? extends Entity>> listClass = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            listClass.set(i, m_listRegistrations.get(i).m_class);
        }
        return listClass.iterator();
    }

    public int size () {
        return m_listRegistrations.size();
    }
}
