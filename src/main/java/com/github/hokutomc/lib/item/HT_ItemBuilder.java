package com.github.hokutomc.lib.item;

import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/06/15.
 */
public abstract class HT_ItemBuilder {
    public abstract ItemStack createStack ();

    public static abstract class WithCondition extends HT_ItemBuilder {
        public final HT_ItemCondition condition;

        public WithCondition (HT_ItemCondition condition) {
            this.condition = condition;
        }

        @Override
        public ItemStack createStack () {
            ItemStack stack = condition.createStack();
            manipulateStack(stack);
            return stack;
        }

        public abstract void manipulateStack (ItemStack itemStack);
    }
}
