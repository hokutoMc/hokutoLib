package com.github.hokutomc.lib.item.recipe;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by user on 2015/01/29.
 */
public class HT_CraftingRecipeBuilder extends HT_RecipeBuilder<HT_CraftingRecipeBuilder> {

    private ISMode m_mode;

    private char register_ch;

    private Map<Character, Object> params;
    private ItemStack m_result;
    private String[] m_grid;
    private boolean m_isOreRecipe;

    private enum ISMode {
        PARAM,RESULT,OTHER
    }

    public HT_CraftingRecipeBuilder () {
        this.params = Maps.newHashMap();
        init();
    }

    private void init () {
        this.m_result = null;
        this.m_mode = ISMode.OTHER;
        this.params.clear();
        this.m_isOreRecipe = false;
    }

    @Override
    public HT_CraftingRecipeBuilder onReturned (HT_ItemStackBuilder4Recipe itemStackBuilder) {
        switch (this.m_mode) {
            case PARAM: params.put(register_ch, itemStackBuilder.build()); break;
            case RESULT: this.m_result = itemStackBuilder.build();
                addRecipe();
                break;
        }
        this.m_mode = ISMode.OTHER;
        return this;
    }

    private void addRecipe () {
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
    }

    public HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> param (char symbol, Item item) {
        return param(symbol, isb4r(item));
    }

    public HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> param (char symbol, Block block) {
        return param(symbol, isb4r(block));
    }

    public HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> param (char synbol, String modid, String name) {
        ItemStack itemStack = GameRegistry.findItemStack(modid, name, 0);
        if (itemStack == null){
            try {
                throw new ItemNotFoundException(modid, name);
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new HT_ItemStackBuilder4Recipe<>(this, itemStack);
    }

    private HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> param (char symbol, HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> isb4r) {
        this.register_ch = symbol;
        this.m_mode = ISMode.PARAM;
        return isb4r;
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

    public HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> to (Item item) {
        this.m_mode = ISMode.RESULT;
        return isb4r(item);
    }

    public HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> to (Block block) {
        this.m_mode = ISMode.RESULT;
        return isb4r(block);
    }

    public HT_ShapelessRecipeBuilder shapeless () {
        return new HT_ShapelessRecipeBuilder(this);
    }

    private HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> isb4r (Item item) {
        return new HT_ItemStackBuilder4Recipe<>(this, item);
    }

    private HT_ItemStackBuilder4Recipe<HT_CraftingRecipeBuilder> isb4r (Block block) {
        return new HT_ItemStackBuilder4Recipe<>(this, block);
    }


}
