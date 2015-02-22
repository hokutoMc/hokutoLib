package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.data.HT_BasicObjectData;
import com.github.hokutomc.lib.data.HT_BasicObjectProperties;
import com.github.hokutomc.lib.data.enumerate.HT_I_StringOrderEnumerator;
import com.github.hokutomc.lib.data.enumerate.HT_I_StringOrdered;
import com.github.hokutomc.lib.data.enumerate.HT_SimpleStringEnumerator;
import com.github.hokutomc.lib.nbt.HT_NBTAnnotations;
import com.github.hokutomc.lib.nbt.NBTStringOrdered;
import com.github.hokutomc.lib.process.HT_ItemStackProcess;
import com.github.hokutomc.lib.process.HT_ItemStackRecipe;
import com.github.hokutomc.lib.tileentity.HT_ProcessTile;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by user on 2014/11/26.
 */
public class TestTE extends HT_ProcessTile<TestTE.FurnaceLikeRecipe> {

    public enum Flags {
        A, B, C
    }

    public enum StrSaved implements HT_I_StringOrdered<StrSaved>{
        X, Y;

        private static final HT_I_StringOrderEnumerator<StrSaved> enumerator = new HT_SimpleStringEnumerator<>(X, Y);

        @Override
        public HT_I_StringOrderEnumerator<StrSaved> getEnumerator () {
            return enumerator;
        }
    }

    private final HT_BasicObjectProperties<HT_BasicObjectData<?>> props;
    final HT_BasicObjectData<EnumSet<Flags>> save_flags;

    @NBTStringOrdered(value = "strSaved", defaultString = "X")
    public StrSaved strSaved = StrSaved.X;

    @SuppressWarnings("unchecked")
    public TestTE () {
        super(new TestTEProcess());
        this.props = new HT_BasicObjectProperties<>();
        this.save_flags = HT_BasicObjectData.<Flags>createEnumSetData(this, "flagsTE", EnumSet.noneOf(Flags.class));
        this.save_flags.update(this, EnumSet.noneOf(Flags.class));
        this.props.addProperty(save_flags).addProperty(new HT_BasicObjectData<>(this, "strSaved", StrSaved.X));
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
        return 3;
    }

    @Override
    public String HT_getInventoryName () {
        return "hokutoLib.inv";
    }

    @Override
    public boolean HT_hasCustomName () {
        return false;
    }

    @Override
    protected boolean HT_isUsableByPlayer (EntityPlayer entityPlayer) {
        return this.HT_getDistanceWith(entityPlayer) < 3.0;
    }

    @Override
    public boolean HT_isItemValidForSlot (int slot, ItemStack itemStack) {
        return true;
    }

    @Override
    public void HT_readFromNBT (NBTTagCompound nbtTagCompound) {
        super.HT_readFromNBT(nbtTagCompound);
        this.props.HT_readFromNBT(nbtTagCompound, this);
        HT_NBTAnnotations.readFieldsFromNBT(this, nbtTagCompound);
    }

    @Override
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound) {
        super.HT_writeToNBT(nbtTagCompound);
        this.props.HT_writeToNBT(nbtTagCompound, this);
        HT_NBTAnnotations.writeFieldsToNBT(this, nbtTagCompound);
        NBTTagCompound nbtTagCompound1 = nbtTagCompound;
    }

    public static class TestTEProcess extends HT_ItemStackProcess<FurnaceLikeRecipe> {

        private static final List<FurnaceLikeRecipe> recipeList;

        static {
            recipeList = Lists.newArrayList();
        }

        protected TestTEProcess () {
            super(recipeList);
        }



        @Override
        protected int HT_getMaxProgress () {
            return 80;
        }

        @Override
        public FurnaceLikeRecipe HT_getCurrentRecipe () {
            for (FurnaceLikeRecipe e : recipeList) {
                if (e.doesMatchInput(this.HT_getInventory().getStackInSlot(0))) return e;
            }
            return null;
        }

        @Override
        protected boolean HT_canStart () {
            return this.HT_getCurrentRecipe() != null;
        }

        @Override
        protected void HT_onStarting () {
            this.HT_setInventorySlotContents(2, this.HT_getStackInSlot(0).splitStack(this.HT_getCurrentRecipe().getInputs()[0].stackSize));
        }

        @Override
        protected boolean HT_canContinue () {
            return true;
        }

        @Override
        protected void HT_onProgress () {

        }

        @Override
        protected void HT_onFinish () {
            this.HT_setInventorySlotContents(2, null);
            if (this.HT_getCurrentRecipe() == null) return;
            if (this.HT_getStackInSlot(1) == null) {
                this.HT_setInventorySlotContents(1, this.HT_getCurrentRecipe().getResults()[0].copy());
            } else {
                this.HT_getStackInSlot(1).stackSize += this.HT_getCurrentRecipe().getResults()[0].stackSize;
            }
        }
    }

    public static class FurnaceLikeRecipe extends HT_ItemStackRecipe {

        public FurnaceLikeRecipe (ItemStack[] input, ItemStack[] output) {
            super(input, output);
        }
    }
}
