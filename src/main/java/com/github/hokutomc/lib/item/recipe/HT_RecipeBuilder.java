package com.github.hokutomc.lib.item.recipe;

/**
 * Created by user on 2015/01/29.
 */
public abstract class HT_RecipeBuilder<T extends HT_RecipeBuilder> {
    protected abstract T onReturned (HT_ItemStackBuilder4Recipe itemStackBuilder);
}
