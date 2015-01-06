package com.github.hokutomc.lib.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * This class allows you to create TileEntity with inventory easily.
 *
 * 2014/10/25.
 */
public abstract class HT_StorageTile extends HT_TileEntity implements ISidedInventory {
    private ItemStack[] m_contents;

    public HT_StorageTile () {
        super();
        this.m_contents = new ItemStack[this.getSizeInventory()];
    }


    // Wrapper====

    @Override
    public final int[] getAccessibleSlotsFromSide (int p_94128_1_) {
        return this.HT_getAccessibleSlotsFromSide(p_94128_1_);
    }

    public abstract int[] HT_getAccessibleSlotsFromSide (int side);

    @Override
    public final boolean canInsertItem (int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return this.HT_canInsertItem(p_102007_1_, p_102007_2_, p_102007_3_);
    }

    public abstract boolean HT_canInsertItem (int slot, ItemStack itemStack, int side);

    @Override
    public final boolean canExtractItem (int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return this.HT_canExtractItem(p_102008_1_, p_102008_2_, p_102008_3_);
    }

    public abstract boolean HT_canExtractItem (int slot, ItemStack itemStack, int side);

    @Override
    public final int getSizeInventory () {
        return this.HT_getSizeInventory();
    }

    public abstract int HT_getSizeInventory ();

    @Override
    public final ItemStack getStackInSlot (int p_70301_1_) {
        return this.HT_getStackInSlot(p_70301_1_);
    }

    public ItemStack HT_getStackInSlot (int slot) {
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
    public final String getInventoryName () {
        return this.HT_getInventoryName();
    }

    public abstract String HT_getInventoryName ();

    @Override
    public final boolean hasCustomInventoryName () {
        return this.HT_hasCustomName();
    }

    public abstract boolean HT_hasCustomName ();

    @Override
    public final int getInventoryStackLimit () {
        return this.HT_getInventoryStackLimit();
    }

    public int HT_getInventoryStackLimit () {
        return 64;
    }

    @Override
    public final boolean isUseableByPlayer (EntityPlayer p_70300_1_) {
        return this.HT_isUsableByPlayer(p_70300_1_);
    }

    protected abstract boolean HT_isUsableByPlayer (EntityPlayer entityPlayer);

    @Override
    public final void openInventory () {
        this.HT_openInventory();
    }

    public void HT_openInventory () {

    }

    @Override
    public final void closeInventory () {
        this.HT_closeInventory();
    }

    public void HT_closeInventory () {

    }

    @Override
    public final boolean isItemValidForSlot (int p_94041_1_, ItemStack p_94041_2_) {
        return HT_isItemValidForSlot(p_94041_1_, p_94041_2_);
    }

    public abstract boolean HT_isItemValidForSlot (int slot, ItemStack itemStack);

    @Override
    public void HT_readFromNBT (NBTTagCompound nbtTagCompound) {
        super.HT_readFromNBT(nbtTagCompound);
        NBTTagList itemTagList = nbtTagCompound.getTagList("items", Constants.NBT.TAG_COMPOUND);
        this.m_contents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < this.m_contents.length; i++) {
            NBTTagCompound itemTagCompound = itemTagList.getCompoundTagAt(i);

            int slotIndex = itemTagCompound.getInteger("slot");

            if (slotIndex >= 0 && slotIndex < this.m_contents.length) {
                this.m_contents[slotIndex] = ItemStack.loadItemStackFromNBT(itemTagCompound);
            }
        }
    }

    @Override
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound) {
        super.HT_writeToNBT(nbtTagCompound);
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < this.m_contents.length; i++) {
            if (m_contents[i] != null) {
                NBTTagCompound itemTagCompound = new NBTTagCompound();
                itemTagCompound.setInteger("slot", i);
                this.m_contents[i].writeToNBT(itemTagCompound);
                tagList.appendTag(itemTagCompound);
            }
        }

        nbtTagCompound.setTag("items", tagList);
    }

}
