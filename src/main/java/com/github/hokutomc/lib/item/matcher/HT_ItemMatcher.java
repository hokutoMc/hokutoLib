package com.github.hokutomc.lib.item.matcher;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/04/10.
 */
public abstract class HT_ItemMatcher implements Predicate<ItemStack> {
    protected abstract boolean check (ItemStack itemStack);

    @Override
    public final boolean apply (ItemStack input) {
        return check(input);
    }

    public final boolean matches (ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() != null && check(itemStack);
    }

    /**
     * @param other
     * @return new instance that checks both this and the other condition.
     */
    public final HT_ItemMatcher andThen (HT_ItemMatcher other) {
        return new AndThen(this, other);
    }

    public static class AndThen extends HT_ItemMatcher {
        private final HT_ItemMatcher one;
        private final HT_ItemMatcher other;

        public AndThen (HT_ItemMatcher one, HT_ItemMatcher other) {
            this.one = one;
            this.other = other;
        }

        @Override
        protected boolean check (ItemStack itemStack) {
            return one.check(itemStack) && other.check(itemStack);
        }
    }
}
