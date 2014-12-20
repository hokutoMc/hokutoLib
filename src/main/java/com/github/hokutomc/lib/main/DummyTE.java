package com.github.hokutomc.lib.main;

import com.github.hokutomc.lib.tileentity.HT_ProcessTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2014/12/09.
 */
public class DummyTE extends HT_ProcessTile<TestTE.FurnaceLikeRecipe>{
    public DummyTE () {
        super(new TestTE.TestTEProcess());
    }

    @Override
    public int[] HT_getAccessibleSlotsFromSide (int side) {
        return new int[0];
    }

    @Override
    public boolean HT_canInsertItem (int slot, ItemStack itemStack, int side) {
        return false;
    }

    @Override
    public boolean HT_canExtractItem (int slot, ItemStack itemStack, int side) {
        return false;
    }

    @Override
    public int HT_getSizeInventory () {
        return 0;
    }

    @Override
    public String HT_getInventoryName () {
        return null;
    }

    @Override
    public boolean HT_hasCustomName () {
        return false;
    }

    @Override
    protected boolean HT_isUsableByPlayer (EntityPlayer entityPlayer) {
        return false;
    }

    @Override
    public boolean HT_isItemValidForSlot (int slot, ItemStack itemStack) {
        return false;
    }
}
