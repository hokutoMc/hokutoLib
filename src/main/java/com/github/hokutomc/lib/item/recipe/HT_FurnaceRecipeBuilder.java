package com.github.hokutomc.lib.item.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by user on 2015/01/29.
 */
public class HT_FurnaceRecipeBuilder {

    private ItemStack m_source;
    private ItemStack m_result;

    private float m_chanceXp;

    public HT_FurnaceRecipeBuilder () {
        init();
    }

    private void init () {
        this.m_source = null;
        this.m_result = null;
        this.m_chanceXp = 0f;
    }

    public HT_FurnaceRecipeBuilder addRecipe () {
        if (this.m_source == null) {
            throw new IllegalStateException("source of furnace recipe should be specified.");
        }
        if (this.m_result == null) {
            throw new IllegalStateException("result of furnace recipe should be specified.");
        }
        GameRegistry.addSmelting(this.m_source, this.m_result, this.m_chanceXp);
        init();
        return this;
    }

    public HT_FurnaceRecipeBuilder from (Item item) {
        return this.from(new ItemStack(item));
    }

    public HT_FurnaceRecipeBuilder from (Block block) {
        return this.from(new ItemStack(block));
    }

    public HT_FurnaceRecipeBuilder from (ItemStack itemStack) {
        this.m_source = itemStack;
        return this;
    }

    public HT_FurnaceRecipeBuilder to (Item item) {
        return this.to(new ItemStack(item));
    }

    public HT_FurnaceRecipeBuilder to (Block block) {
        return this.to(new ItemStack(block));
    }

    public HT_FurnaceRecipeBuilder to (ItemStack itemStack) {
        this.m_result = itemStack;
        return this;
    }

    public HT_FurnaceRecipeBuilder withXp (double xp) {
        this.m_chanceXp = (float) xp;
        return this;
    }
}
