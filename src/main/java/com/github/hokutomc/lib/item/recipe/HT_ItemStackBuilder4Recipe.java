package com.github.hokutomc.lib.item.recipe;

import com.github.hokutomc.lib.item.HT_ItemStackBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/01/29.
 */
public class HT_ItemStackBuilder4Recipe<T extends HT_RecipeBuilder<T>> extends HT_ItemStackBuilder<HT_ItemStackBuilder4Recipe<T>> {
    private T m_recipeBuilder;

    protected HT_ItemStackBuilder4Recipe (T m_recipeBuilder, ItemStack template) {
        super(template);
        this.m_recipeBuilder = m_recipeBuilder;
    }

    HT_ItemStackBuilder4Recipe (T recipeBuilder, HT_ItemStackBuilder base) {
        this(recipeBuilder, base.build());
    }

    HT_ItemStackBuilder4Recipe (T recipeBuilder, Item item) {
        super(item);
        this.m_recipeBuilder = recipeBuilder;
    }

    HT_ItemStackBuilder4Recipe (T recipeBuilder, Block block) {
        super(block);
        this.m_recipeBuilder = recipeBuilder;
    }

    public T endItem () {
        return m_recipeBuilder.onReturned(this);
    }

}
