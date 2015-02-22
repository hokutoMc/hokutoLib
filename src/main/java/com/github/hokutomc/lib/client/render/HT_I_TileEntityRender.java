package com.github.hokutomc.lib.client.render;

import net.minecraft.tileentity.TileEntity;

/**
 * Created by user on 2015/02/22.
 */
public interface HT_I_TileEntityRender<T extends TileEntity> {

    void renderTileEntityAt (T tileEntity, double x, double y, double z, float particleTime, Object dummy);
}
