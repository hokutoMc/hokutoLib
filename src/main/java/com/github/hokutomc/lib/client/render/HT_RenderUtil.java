package com.github.hokutomc.lib.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

/**
 * Created by user on 2015/03/18.
 */
public final class HT_RenderUtil {
    private HT_RenderUtil () {
    }

    public static RenderManager getRenderManager () {
        return Minecraft.getMinecraft().getRenderManager();
    }


    public static ItemModelMesher getItemModelMesher () {
        return Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
    }

    public static void registerOneItemMesher (Item item, int meta, String file) {
        getItemModelMesher().register(item, meta, new ModelResourceLocation(file, "inventory"));
    }
}
