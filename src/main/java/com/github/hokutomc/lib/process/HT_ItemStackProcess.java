package com.github.hokutomc.lib.process;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * This class expresses precess of a TileEntity or Entity.
 * With recipe using ItemStack.
 *
 * 2014/11/26.
 */
public abstract class HT_ItemStackProcess<T extends HT_ItemStackRecipe> {
    private int m_progress;
    private IInventory m_inventory;
    private final List<T> m_listRecipe;

    public HT_ItemStackProcess (List<T> recipeList) {
        this.m_listRecipe = recipeList;
        this.m_progress = 0;
    }

    public void HT_setInventory (IInventory inventory) {
        this.m_inventory = inventory;
    }

    public void HT_proceedProgressBy(int value) {
        this.m_progress += value;
    }

    public void HT_proceedProgress() {
        this.m_progress++;
    }

    public void HT_resetProgress() {
        this.m_progress = 0;
    }

    public boolean HT_isWorking () {
        return this.m_progress > 0;
    }

    public void HT_updateProcess () {
        if (this.m_progress <= 0 && this.HT_canStart()) {
            this.HT_onStarting();
            this.HT_resetProgress();
            this.HT_proceedProgress();
        } else if (this.m_progress >= this.HT_getMaxProgress()) {
            this.HT_onFinish();
            this.HT_resetProgress();
        } else if (this.HT_canContinue()) {
            this.HT_onProgress();
            this.HT_proceedProgress();
        } else {
            this.HT_resetProgress();
        }
    }

    public abstract T HT_getCurrentRecipe ();

    protected abstract int HT_getMaxProgress ();

    protected abstract boolean HT_canStart ();

    protected abstract void HT_onStarting ();

    protected abstract boolean HT_canContinue ();

    protected abstract void HT_onProgress ();

    protected abstract void HT_onFinish ();

    public void HT_writeToNBT (NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("m_progress", m_progress);
    }

    public void HT_readFromNBT (NBTTagCompound nbtTagCompound){
        this.m_progress = nbtTagCompound.getInteger("m_progress");
    }

    public IInventory HT_getInventory () {
        return this.m_inventory;
    }

    public ItemStack HT_getStackInSlot (int index) {
        return this.m_inventory.getStackInSlot(index);
    }

    public void HT_setInventorySlotContents (int index, ItemStack itemStack) {
        if (itemStack != null && itemStack.stackSize == 0) { itemStack = null; }
        this.m_inventory.setInventorySlotContents(index, itemStack);
    }
}
