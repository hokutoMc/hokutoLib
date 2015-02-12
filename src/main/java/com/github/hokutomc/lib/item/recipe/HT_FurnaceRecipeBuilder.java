package com.github.hokutomc.lib.item.recipe;

import com.github.hokutomc.lib.item.HT_ItemStackBuilder;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/01/29.
 */
public class HT_FurnaceRecipeBuilder extends HT_RecipeBuilder<HT_FurnaceRecipeBuilder> {

    private ItemStack m_source;
    private ItemStack m_result;

    private ISMode m_mode;
    private float m_chanceXp;

    enum ISMode {
        SOURCE,RESULT,OTHER
    }

    public HT_FurnaceRecipeBuilder () {
        init();
    }

    private void init () {
        this.m_source = this.m_result = null;
    }

    @Override
    protected HT_FurnaceRecipeBuilder onReturned (HT_ItemStackBuilder4Recipe itemStackBuilder) {
        switch (m_mode) {
            case SOURCE:this.m_source = itemStackBuilder.build(); break;
            case RESULT: this.m_result = itemStackBuilder.build();
                addRecipe();
                break;
        }
        this.m_mode = ISMode.OTHER;
        return this;
    }

    private void addRecipe () {
        GameRegistry.addSmelting(this.m_source, this.m_result, this.m_chanceXp);
    }

    public ISB4RF from (Item item) {
        return this.from(new ISB4RF(this, item));
    }

    public ISB4RF from (Block block) {
        return this.from(new ISB4RF(this, block));
    }

    private ISB4RF from (ISB4RF isb4r) {
        this.m_source = isb4r.build();
        this.m_mode = ISMode.SOURCE;
        return isb4r;
    }

    public ISB4RF to (Item item) {
        return this.to(new ISB4RF(this, item));
    }

    public ISB4RF to (Block block) {
        return this.to(new ISB4RF(this, block));
    }

    private ISB4RF to (ISB4RF isb4r) {
        this.m_result = isb4r.build();
        this.m_mode = ISMode.RESULT;
        return isb4r;
    }

    public HT_FurnaceRecipeBuilder withXp (double xp) {
        this.m_chanceXp = (float) xp;
        return this;
    }

    public static class ISB4RF extends HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> {

        protected ISB4RF (HT_FurnaceRecipeBuilder m_recipeBuilder, ItemStack template) {
            super(m_recipeBuilder, template);
        }

        ISB4RF (HT_FurnaceRecipeBuilder recipeBuilder, HT_ItemStackBuilder base) {
            super(recipeBuilder, base);
        }

        ISB4RF (HT_FurnaceRecipeBuilder recipeBuilder, Item item) {
            super(recipeBuilder, item);
        }

        ISB4RF (HT_FurnaceRecipeBuilder recipeBuilder, Block block) {
            super(recipeBuilder, block);
        }
    }

}
