package com.github.hokutomc.lib.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by user on 2014/10/11.
 */
public interface HT_I_Proxy {
    public World getClientWorld ();

    public EntityPlayer getClientPlayer ();

    public void registerTileEntity ();
}
