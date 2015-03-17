package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.client.render.HT_I_EntityRender;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user on 2015/01/05.
 */
public class RenderTestEntity extends RenderLiving implements HT_I_EntityRender<TestMob>{
    public RenderTestEntity (RenderManager renderManager1, ModelBase p_i1262_1_, float p_i1262_2_) {
        super(renderManager1, p_i1262_1_, p_i1262_2_);
    }

    @Override
    protected ResourceLocation getEntityTexture (Entity p_110775_1_) {
        return new ResourceLocation("textures/entity/villager/farmer.png");
    }

    @Override
    public void HT_doRender (TestMob entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {

    }

    @Override
    public ResourceLocation HT_getEntityTexture (TestMob entity) {
        return null;
    }
}
