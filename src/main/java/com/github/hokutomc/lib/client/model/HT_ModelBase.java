package com.github.hokutomc.lib.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.util.Random;

/**
 * Created by user on 2015/01/06.
 */
public class HT_ModelBase extends ModelBase {
    @Override
    public void render (Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        super.render(entity, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
    }

    @Override
    public void setRotationAngles (float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    }

    @Override
    public void setLivingAnimations (EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
        super.setLivingAnimations(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
    }

    @Override
    public ModelRenderer getRandomModelBox (Random random) {
        return super.getRandomModelBox(random);
    }

    @Override
    protected void setTextureOffset (String p_78085_1_, int p_78085_2_, int p_78085_3_) {
        super.setTextureOffset(p_78085_1_, p_78085_2_, p_78085_3_);
    }

    @Override
    public TextureOffset getTextureOffset (String p_78084_1_) {
        return super.getTextureOffset(p_78084_1_);
    }
}
