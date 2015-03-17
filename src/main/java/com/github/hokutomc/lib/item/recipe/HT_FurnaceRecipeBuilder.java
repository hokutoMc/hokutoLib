package com.github.hokutomc.lib.item.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    public HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> from (Item item) {
        return this.from(new HT_ItemStackBuilder4Recipe<>(this, item));
    }

    public HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> from (Block block) {
        return this.from(new HT_ItemStackBuilder4Recipe<>(this, block));
    }

    private HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> from (HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> isb4r) {
        this.m_source = isb4r.build();
        this.m_mode = ISMode.SOURCE;
        return isb4r;
    }

    public HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> to (Item item) {
        return this.to(new HT_ItemStackBuilder4Recipe<>(this, item));
    }

    public HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> to (Block block) {
        return this.to(new HT_ItemStackBuilder4Recipe<>(this, block));
    }

    private HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> to (HT_ItemStackBuilder4Recipe<HT_FurnaceRecipeBuilder> isb4r) {
        this.m_result = isb4r.build();
        this.m_mode = ISMode.RESULT;
        return isb4r;
    }

    public HT_FurnaceRecipeBuilder withXp (double xp) {
        this.m_chanceXp = (float) xp;
        return this;
    }

}
