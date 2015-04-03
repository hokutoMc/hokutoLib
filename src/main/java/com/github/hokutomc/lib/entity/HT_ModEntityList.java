package com.github.hokutomc.lib.entity;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.client.render.HT_RenderUtil;
import com.github.hokutomc.lib.item.HT_ItemMobEgg;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This contains entity classes with spawn egg colors and inner name.
 * This adds mob eggs of each entity classes registered in this.
 * 2015/01/06.
 */
public class HT_ModEntityList implements Iterable<Class<? extends Entity>>{

    private static final ModelResourceLocation modelEgg = new ModelResourceLocation("hokutomc.lib", "mobEgg");

    private class HT_EntityRegistration {
        private Class<? extends Entity> m_class;
        private String m_name;
        public int m_colorBase;
        public int m_colorSpot;

        private HT_EntityRegistration (Class<? extends Entity> classEntity, String name, int colorBase, int colorSpot) {
            this.m_class = classEntity;
            this.m_name = name;
            this.m_colorBase = colorBase;
            this.m_colorSpot = colorSpot;
        }
    }

    public HT_ItemMobEgg m_itemMobEgg;

    private ArrayList<HT_EntityRegistration> m_listRegistrations;
    private Object m_modObj;

    public HT_ModEntityList (Object mod, String modid) {
        m_listRegistrations = new ArrayList<>();
        this.m_modObj = mod;
        m_itemMobEgg = new HT_ItemMobEgg(modid, this).register();
    }

    public void registerModel () {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            HT_RenderUtil.getItemModelMesher().register(m_itemMobEgg, new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation (ItemStack p_178113_1_) {
                    return modelEgg;
                }
            });
        }
    }

    public HT_ModEntityList register(Class<? extends Entity> classEntity, String name, int colorBase, int colorSpot) {
        HT_Registries.registerEntity(classEntity, name, add(classEntity, name, colorBase, colorSpot), this.m_modObj);
        return this;
    }

    private int add (Class<? extends Entity> classEntity, String name, int colorBase, int colorSpot) {
        HT_EntityRegistration registration = new HT_EntityRegistration(classEntity, name, colorBase, colorSpot);
        this.m_listRegistrations.add(registration);
        return this.m_listRegistrations.indexOf(registration);
    }

    public Class<? extends Entity> getClassAt (int index) {
        return this.m_listRegistrations.get(index).m_class;
    }

    public String getNameAt (int index) {
        return this.m_listRegistrations.get(index).m_name;
    }

    public int getBaseColorAt (int index) {
        return this.m_listRegistrations.get(index).m_colorBase;
    }

    public int getSpotColorAt (int index) {
        return this.m_listRegistrations.get(index).m_colorSpot;
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
