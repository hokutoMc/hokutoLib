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
    public final void doRender (Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        this.HT_doRender((E) entity, x, y, z, p_76986_8_, p_76986_9_);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected final ResourceLocation getEntityTexture (Entity entity) {
        return this.HT_getEntityTexture((E) entity);
    }
}
