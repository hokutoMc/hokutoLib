package com.github.hokutomc.lib.process;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by user on 2014/11/08.
 */
public class HT_ItemStackRecipe {
    private final ItemStack[] input;
    private final ItemStack[] output;

    public HT_ItemStackRecipe (ItemStack[] input, ItemStack[] output) {
        this.input = input;
        this.output = output;
    }

    public boolean HT_doesMatchInput (ItemStack... stacks) {
        for (int i = 0; i < this.input.length; i++) {
            ItemStack required = this.input[i];
            ItemStack actual = null;
            if (i <= stacks.length) {
                actual = stacks[i];
            }
            if (!HT_doesSatisfyRequirement(required, actual)) {
                return false;
            }
        }
        return true;
    }

    private boolean HT_doesSatisfyRequirement (ItemStack required, ItemStack actual) {
        return required == null || OreDictionary.itemMatches(required, actual, false) && (required.stackSize <= actual.stackSize);
    }


    public ItemStack[] HT_getResults () {
        return output;
    }

    public ItemStack[] getInputs () {
        return input;
    }
}
