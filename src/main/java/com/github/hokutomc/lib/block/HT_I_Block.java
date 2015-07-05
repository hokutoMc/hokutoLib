package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;

/**
 * Created by user on 2015/06/10.
 */
public interface HT_I_Block<T extends Block & HT_I_Block<T>> {
    String getNameToRegister ();
}
