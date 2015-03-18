package com.github.hokutomc.lib.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;

/**
 * Created by user on 2015/03/18.
 */
public final class HT_RenderUtil {
    private HT_RenderUtil () {
    }

    public static RenderManager getRenderManager () {
        return Minecraft.getMinecraft().getRenderManager();
    }
}
