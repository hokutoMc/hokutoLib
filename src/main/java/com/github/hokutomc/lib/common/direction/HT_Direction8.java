package com.github.hokutomc.lib.common.direction;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by user on 2014/10/12.
 */
public enum HT_Direction8 implements HT_I_Direction {
    NORTH(ForgeDirection.NORTH, ForgeDirection.UNKNOWN),
    NE(ForgeDirection.NORTH, ForgeDirection.EAST),
    EAST(ForgeDirection.UNKNOWN, ForgeDirection.EAST),
    SE(ForgeDirection.SOUTH, ForgeDirection.EAST),
    SOUTH(ForgeDirection.SOUTH, ForgeDirection.UNKNOWN),
    SW(ForgeDirection.SOUTH, ForgeDirection.WEST),
    WEST(ForgeDirection.UNKNOWN, ForgeDirection.WEST),
    NW(ForgeDirection.NORTH, ForgeDirection.WEST),;
    private final ForgeDirection NS;
    private final ForgeDirection EW;

    HT_Direction8 (ForgeDirection ns, ForgeDirection ew) {
        this.NS = ns;
        this.EW = ew;
    }

    @Override
    public ForgeDirection toForgeDirection () {
        if (this.EW == ForgeDirection.UNKNOWN) {
            return this.NS;
        }
        if (this.NS == ForgeDirection.UNKNOWN) {
            return this.EW;
        }
        return ForgeDirection.UNKNOWN;
    }

    @Override
    public <T extends HT_I_Direction> T as () {
        return null;
    }

    @Override
    public int getXOf () {
        return this.EW.offsetX;
    }

    @Override
    public int getYOf () {
        return 0;
    }

    @Override
    public int getZOf () {
        return this.NS.offsetZ;
    }

    @Override
    public HT_I_Direction get (int x, int y, int z) {
        return null;
    }

    @Override
    public float getOrientation () {
        return 0;
    }


}
