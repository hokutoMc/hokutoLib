package com.github.hokutomc.lib.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by user on 2015/02/22.
 */
public abstract class HT_TileEntityRender<T extends TileEntity> extends TileEntitySpecialRenderer implements HT_I_TileEntityRender<T>{
    @Override
    @SuppressWarnings("unchecked")
    public final void renderTileEntityAt (TileEntity tileEntity, double x, double y, double z, float particleTime, int p_180535_9_) {
        this.HT_renderAt((T) tileEntity, x, y, z, particleTime, p_180535_9_);
    }
}
