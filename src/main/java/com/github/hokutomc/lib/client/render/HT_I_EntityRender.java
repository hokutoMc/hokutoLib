package com.github.hokutomc.lib.client.render;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user on 2015/02/22.
 */
public interface HT_I_EntityRender<E extends Entity> {
    void HT_doRender (E entity, double x, double y, double z, float p_76986_8_, float p_76986_9_);

    ResourceLocation HT_getEntityTexture (E entity);

}
