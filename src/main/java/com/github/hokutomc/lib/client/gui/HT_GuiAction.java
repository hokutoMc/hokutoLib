package com.github.hokutomc.lib.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by user on 2014/11/05.
 */
public interface HT_GuiAction<T> {
    T get (EntityPlayer player, World world, int x, int y, int z);
}
