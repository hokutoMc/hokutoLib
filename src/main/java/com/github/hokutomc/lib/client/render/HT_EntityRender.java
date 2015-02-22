package com.github.hokutomc.lib.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user on 2015/02/22.
 */
public abstract class HT_EntityRender<E extends Entity> extends Render implements HT_I_EntityRender<E> {
    @Override
    @SuppressWarnings("unchecked")
    public void doRender (Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        doRender((E) entity, x, y, z, p_76986_8_, p_76986_9_, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ResourceLocation getEntityTexture (Entity entity) {
        return getEntityTexture((E) entity, null);
    }
}
