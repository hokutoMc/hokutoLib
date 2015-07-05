package com.github.hokutomc.lib.item.recipe;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by user on 2015/01/29.
 */
public class HT_CraftingRecipeBuilder {

    private Map<Character, Object> params;
    private ItemStack m_result;
    private String[] m_grid;
    private boolean m_isOreRecipe;


    public HT_CraftingRecipeBuilder () {
        this.params = Maps.newHashMap();
        init();
    }

    private void init () {
        this.m_grid = new String[0];
        this.m_result = null;
        this.params.clear();
        this.m_isOreRecipe = false;
    }

    public HT_CraftingRecipeBuilder addRecipe () {
        ArrayList<Object> list = new ArrayList<>();
        Collections.addAll(list, m_grid);

        for (Character c : params.keySet()) {
            list.add(c);
            list.add(params.get(c));
        }

        if (this.m_isOreRecipe) {
            GameRegistry.addRecipe(new ShapedOreRecipe(m_result, list.toArray()));
        } else {
            GameRegistry.addRecipe(m_result, list.toArray());
        }
        init();
        return this;
    }

    public HT_CraftingRecipeBuilder param (char symbol, Item item) {
        params.put(symbol, item);
        return this;
    }

    public HT_CraftingRecipeBuilder param (char symbol, Block block) {
        params.put(symbol, block);
        return this;
    }


    private HT_CraftingRecipeBuilder param (char symbol, ItemStack itemStack) {
        params.put(symbol, itemStack);
        return this;
    }

    public HT_CraftingRecipeBuilder paramOre (char symbol, String oreName) {
        this.m_isOreRecipe = true;
        this.params.put(symbol, oreName);
        return this;
    }

    public HT_CraftingRecipeBuilder grid (String... grid) {
        this.m_grid = grid;
        return this;
    }

    public HT_CraftingRecipeBuilder to (Item item) {
        return this.to(new ItemStack(item));
    }

    public HT_CraftingRecipeBuilder to (Block block) {
        return this.to(new ItemStack(block));
    }

    public HT_CraftingRecipeBuilder to (ItemStack itemStack) {
        this.m_result = itemStack;
        return this;
    }
}
