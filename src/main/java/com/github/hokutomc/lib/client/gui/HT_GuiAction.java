package com.github.hokutomc.lib.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * This is an interface to express GUI creation action.
 * 2014/11/05.
 */
public interface HT_GuiAction<T> {
    T get (EntityPlayer player, World world, int x, int y, int z);
}
