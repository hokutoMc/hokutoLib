package com.github.hokutomc.lib.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by user on 2015/02/22.
 */
public abstract class HT_TileEntityRender<T extends TileEntity> extends TileEntitySpecialRenderer implements HT_I_TileEntityRender<T>{

    @Override
    @SuppressWarnings("unchecked")
    public void renderTileEntityAt (TileEntity tileEntity, double x, double y, double z, float particleTime) {
        renderTileEntityAt((T) tileEntity, x, y, z, particleTime, null);
    }
}
