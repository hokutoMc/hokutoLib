package com.github.hokutomc.lib.process;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class can express recipe with some ItemStacks.
 *
 * 2014/11/08.
 */
public class HT_ItemStackRecipe {
    private final ItemStack[] m_input;
    private final ItemStack[] m_output;

    public HT_ItemStackRecipe (ItemStack[] input, ItemStack[] output) {
        this.m_input = input;
        this.m_output = output;
    }

    public boolean doesMatchInput (ItemStack... stacks) {
        for (int i = 0; i < this.m_input.length; i++) {
            ItemStack required = this.m_input[i];
            ItemStack actual = null;
            if (i <= stacks.length) {
                actual = stacks[i];
            }
            if (!doesSatisfyRequirement(required, actual)) {
                return false;
            }
        }
        return true;
    }

    private boolean doesSatisfyRequirement (ItemStack required, ItemStack actual) {
        return required == null || OreDictionary.itemMatches(required, actual, false) && (required.stackSize <= actual.stackSize);
    }


    public ItemStack[] getResults () {
        return m_output;
    }

    public ItemStack[] getInputs () {
        return m_input;
    }
}
