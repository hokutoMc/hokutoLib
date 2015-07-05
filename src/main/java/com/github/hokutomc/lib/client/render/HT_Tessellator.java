package com.github.hokutomc.lib.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

/**
 * Created by user on 2015/05/22.
 */
public final class HT_Tessellator {
    private HT_Tessellator () {
    }

    public static final Tessellator tessellator = Tessellator.getInstance();
    public static final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

    public static void startDrawingQuads () {
        worldRenderer.startDrawingQuads();
    }

    public static void draw () {
        tessellator.draw();
    }

    public static void addVertex (double x, double y, double z) {
        worldRenderer.addVertex(x, y, z);
    }

    public static void addVertexWithUV (double x, double y, double z, double u, double v) {
        worldRenderer.addVertexWithUV(x, y, z, u, v);
    }
}
