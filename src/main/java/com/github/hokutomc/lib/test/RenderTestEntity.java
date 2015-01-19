package com.github.hokutomc.lib.test;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user on 2015/01/05.
 */
public class RenderTestEntity extends RenderLiving{
    public RenderTestEntity (ModelBase p_i1262_1_, float p_i1262_2_) {
        super(p_i1262_1_, p_i1262_2_);
    }

    @Override
    protected ResourceLocation getEntityTexture (Entity p_110775_1_) {
        return new ResourceLocation("textures/entity/villager/farmer.png");
    }
}
