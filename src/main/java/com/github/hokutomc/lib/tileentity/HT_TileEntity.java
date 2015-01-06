package com.github.hokutomc.lib.tileentity;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * This class wraps TileEntity.class.
 *
 * 2014/10/25.
 */
public class HT_TileEntity extends TileEntity {
    public HT_TileEntity () {
        super();
    }

    public final double HT_getDistanceWith (Entity entity) {
        return this.getDistanceFrom(entity.posX, entity.posY, entity.posZ);
    }

    @Override
    public final World getWorldObj () {
        return this.HT_getWorldObj();
    }

    public World HT_getWorldObj () {
        return super.getWorldObj();
    }

    @Override
    public final void setWorldObj (World p_145834_1_) {
        this.HT_setWorldObj(p_145834_1_);
    }

    public void HT_setWorldObj (World world) {
        super.setWorldObj(world);
    }

    @Override
    public final boolean hasWorldObj () {
        return this.HT_hasWorldObj();
    }

    public boolean HT_hasWorldObj () {
        return super.hasWorldObj();
    }

    @Override
    public final void readFromNBT (NBTTagCompound p_145839_1_) {
        this.HT_readFromNBT(p_145839_1_);
    }

    public void HT_readFromNBT (NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
    }

    @Override
    public final void writeToNBT (NBTTagCompound p_145841_1_) {
        this.HT_writeToNBT(p_145841_1_);
    }

    public void HT_writeToNBT (NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
    }

    @Override
    public final void updateEntity () {
        this.HT_updateEntity();
    }

    public void HT_updateEntity () {
        super.updateEntity();
    }

    @Override
    public final int getBlockMetadata () {
        return this.HT_getBlockMetadata();
    }

    public int HT_getBlockMetadata () {
        return super.getBlockMetadata();
    }

    @Override
    public final void markDirty () {
        this.HT_markDirty();
    }

    public void HT_markDirty () {
        super.markDirty();
    }

    @Override
    public final double getDistanceFrom (double p_145835_1_, double p_145835_3_, double p_145835_5_) {
        return this.HT_getDistanceFrom(p_145835_1_, p_145835_3_, p_145835_5_);
    }

    public double HT_getDistanceFrom (double x, double y, double z) {
        return super.getDistanceFrom(x, y, z);
    }

    @Override
    public final double getMaxRenderDistanceSquared () {
        return this.HT_getMaxRenderDistanceSquared();
    }

    public double HT_getMaxRenderDistanceSquared () {
        return super.getMaxRenderDistanceSquared();
    }

    @Override
    public final Block getBlockType () {
        return this.HT_getBlockType();
    }

    public Block HT_getBlockType () {
        return super.getBlockType();
    }

    @Override
    public final Packet getDescriptionPacket () {
        return this.HT_getDescriptionPacket();
    }

    public Packet HT_getDescriptionPacket () {
        return super.getDescriptionPacket();
    }

    @Override
    public final boolean isInvalid () {
        return this.HT_isInvalid();
    }

    public boolean HT_isInvalid () {
        return super.isInvalid();
    }

    @Override
    public final void invalidate () {
        this.HT_invalidate();
    }

    public void HT_invalidate () {
        super.invalidate();
    }

    @Override
    public final void validate () {
        this.HT_validate();
    }

    public void HT_validate () {
        super.validate();
    }

    @Override
    public final boolean receiveClientEvent (int p_145842_1_, int p_145842_2_) {
        return this.HT_receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    public boolean HT_receiveClientEvent (int p_145842_1_, int p_145842_2_) {
        return super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    @Override
    public final void updateContainingBlockInfo () {
        this.HT_updateContainingBlockInfo();
    }

    public void HT_updateContainingBlockInfo () {
        super.updateContainingBlockInfo();
    }

    @Override
    public final void func_145828_a (CrashReportCategory p_145828_1_) {
        this.HT_onCrashed(p_145828_1_);
    }

    public void HT_onCrashed (CrashReportCategory crashReportCategory) {
        super.func_145828_a(crashReportCategory);
    }


}
