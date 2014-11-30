package com.github.hokutomc.lib.common.direction;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by user on 2014/10/12.
 */
public interface HT_I_Direction {
    ForgeDirection toForgeDirection ();

    <T extends HT_I_Direction> T as ();

    int getXOf ();

    int getYOf ();

    int getZOf ();

    HT_I_Direction get (int x, int y, int z);

    float getOrientation ();
}
