package com.github.hokutomc.lib.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * This class wraps TileEntity.class.
 *
 * 2014/10/25.
 */
public class HT_TileEntity extends TileEntity {
    public HT_TileEntity () {
        super();
    }

    public final double getDistanceWith (Entity entity) {
        return Math.sqrt(this.pos.distanceSq(entity.posX, entity.posY, entity.posZ));
    }

    public void playSoundEffect (String sound, float volume, float pitch) {
        this.worldObj.playSoundEffect(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5, sound, volume, pitch);
    }

    @Override
    public Packet getDescriptionPacket () {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 1, nbtTagCompound);
    }

    @Override
    public final void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

}
