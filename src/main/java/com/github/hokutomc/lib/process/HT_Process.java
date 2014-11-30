package com.github.hokutomc.lib.process;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by user on 2014/11/26.
 */
public abstract class HT_Process<T extends HT_ItemStackRecipe> {
    private int progress;
    private IInventory inventory;
    private final List<T> recipeList;

    public HT_Process (List<T> recipeList) {
        this.recipeList = recipeList;
        this.progress = 0;
    }

    public void HT_setInventory (IInventory inventory) {
        this.inventory = inventory;
    }

    public void HT_proceedProgressBy(int value) {
        this.progress += value;
    }

    public void HT_proceedProgress() {
        this.progress++;
    }

    public void HT_resetProgress() {
        this.progress = 0;
    }

    public boolean HT_isWorking () {
        return this.progress > 0;
    }

    public void HT_updateProcess () {
        if (this.progress <= 0 && this.HT_canStart()) {
            this.HT_onStarting();
            this.HT_resetProgress();
            this.HT_proceedProgress();
        } else if (this.progress >= this.HT_getMaxProgress()) {
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
        nbtTagCompound.setInteger("progress", progress);
    }

    public void HT_readFromNBT (NBTTagCompound nbtTagCompound){
        this.progress = nbtTagCompound.getInteger("progress");
    }

    public IInventory HT_getInventory () {
        return this.inventory;
    }

    public ItemStack HT_getStackInSlot (int index) {
        return this.inventory.getStackInSlot(index);
    }

    public void HT_setInventorySlotContents (int index, ItemStack itemStack) {
        if (itemStack != null && itemStack.stackSize == 0) { itemStack = null; }
        this.inventory.setInventorySlotContents(index, itemStack);
    }
}
