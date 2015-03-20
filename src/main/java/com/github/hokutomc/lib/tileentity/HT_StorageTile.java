package com.github.hokutomc.lib.tileentity;

import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * This class allows you to create TileEntity with inventory easily.
 *
 * 2014/10/25.
 */
public abstract class HT_StorageTile extends HT_TileEntity implements ISidedInventory {
    protected ItemStack[] m_contents;

    public HT_StorageTile () {
        super();
        this.m_contents = new ItemStack[this.getSizeInventory()];
    }

    public void dropAll () {
        for (int i = 0; i < this.getSizeInventory(); i++) {
            if (this.getStackInSlot(i) != null) this.drop(this.getStackInSlot(i));
        }
    }

    public void drop (ItemStack itemStack, double yOffset) {
        this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.pos.getX(), this.pos.getY() + yOffset, this.pos.getZ(), itemStack));
    }

    public void drop (ItemStack itemStack) {
        drop(itemStack, 0.0);
    }

    // Wrapper====


    @Override
    public int[] getSlotsForFace (EnumFacing side) {
        return new int[0];
    }

    @Override
    public final boolean canInsertItem (int slot, ItemStack itemStack, EnumFacing side) {
        return false;
    }

    @Override
    public final boolean canExtractItem (int slot, ItemStack itemStack, EnumFacing side) {
        return false;
    }

    @Override
    public final ItemStack getStackInSlot (int slot) {
        return slot < this.m_contents.length ? m_contents[slot] : null;
    }

    @Override
    public final ItemStack decrStackSize (int p_70298_1_, int p_70298_2_) {
        return this.takeItemStackOut(p_70298_1_, p_70298_2_);
    }

    public ItemStack takeItemStackOut (int slot, int size) {
        if (this.m_contents[slot] != null) {
            if (this.m_contents[slot].stackSize <= slot) {
                ItemStack itemStack = this.m_contents[slot].copy();
                this.m_contents[slot] = null;
                return itemStack;
            }
            ItemStack itemStack = this.m_contents[slot].splitStack(size);
            if (this.m_contents[slot].stackSize == 0) {
                this.m_contents[slot] = null;
            }
            return itemStack;
        }
        return null;
    }

    @Override
    public final ItemStack getStackInSlotOnClosing (int p_70304_1_) {
        return this.HT_getStackInSlotOnClosing(p_70304_1_);
    }

    public ItemStack HT_getStackInSlotOnClosing (int slot) {
        return null;
    }

    @Override
    public final void setInventorySlotContents (int p_70299_1_, ItemStack p_70299_2_) {
        this.HT_setInventorySlotContents(p_70299_1_, p_70299_2_);
    }

    public void HT_setInventorySlotContents (int slot, ItemStack itemStack) {
        if (slot < this.m_contents.length) {
            this.m_contents[slot] = itemStack;
        }
    }

    @Override
    public final boolean hasCustomName () {
        return false;
    }


    @Override
    public int getInventoryStackLimit () {
        return 64;
    }



    @Override
    public void readFromNBT (NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.m_contents = HT_NBTUtil.readItemStacks(nbtTagCompound, this.getSizeInventory());
    }

    @Override
    public void writeToNBT (NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        HT_NBTUtil.writeItemStacks(nbtTagCompound, this.m_contents);
    }
}
