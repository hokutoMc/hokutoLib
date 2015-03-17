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
public abstract class HT_CraftingRecipeBuilder<T extends HT_CraftingRecipeBuilder<T>> extends HT_RecipeBuilder<T> {

    private ISMode m_mode;

    private char register_ch;

    private Map<Character, Object> params;
    private ItemStack m_result;
    private String[] m_grid;
    private boolean m_isOreRecipe;

    private enum ISMode {
        PARAM,RESULT,OTHER
    }

    protected HT_CraftingRecipeBuilder () {
        this.params = Maps.newHashMap();
        init();
    }

    public static Impl create () {
        return new Impl();
    }

    private void init () {
        this.m_result = null;
        this.m_mode = ISMode.OTHER;
        this.params.clear();
        this.m_isOreRecipe = false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T onReturned (HT_ItemStackBuilder4Recipe itemStackBuilder) {
        switch (this.m_mode) {
            case PARAM: params.put(register_ch, itemStackBuilder.build()); break;
            case RESULT: this.m_result = itemStackBuilder.build();
                addRecipe();
                break;
        }
        this.m_mode = ISMode.OTHER;
        return (T) this;
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

    public HT_ItemStackBuilder4Recipe<T> param (char symbol, Item item) {
        return param(symbol, isb4r(item));
    }

    public HT_ItemStackBuilder4Recipe<T> param (char symbol, Block block) {
        return param(symbol, isb4r(block));
    }

//    @SuppressWarnings("unchecked")
//    public HT_ItemStackBuilder4Recipe<T> param (char synbol, String modid, String name) {
//        ItemStack itemStack = GameRegistry.findItemStack(modid, name, 0);
//        if (itemStack == null){
//            try {
//                throw new ItemNotFoundException(modid, name);
//            } catch (ItemNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return new HT_ItemStackBuilder4Recipe<>((T) this, itemStack);
//    }

    private HT_ItemStackBuilder4Recipe<T> param (char symbol, HT_ItemStackBuilder4Recipe<T> isb4r) {
        this.register_ch = symbol;
        this.m_mode = ISMode.PARAM;
        return isb4r;
    }

    @SuppressWarnings("unchecked")
    public T paramOre (char symbol, String oreName) {
        this.m_isOreRecipe = true;
        this.params.put(symbol, oreName);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T grid (String... grid) {
        this.m_grid = grid;
        return (T) this;
    }

    public HT_ItemStackBuilder4Recipe<T> to (Item item) {
        this.m_mode = ISMode.RESULT;
        return isb4r(item);
    }

    public HT_ItemStackBuilder4Recipe<T> to (Block block) {
        this.m_mode = ISMode.RESULT;
        return isb4r(block);
    }

    public HT_ShapelessRecipeBuilder shapeless () {
        return new HT_ShapelessRecipeBuilder(this);
    }

    @SuppressWarnings("unchecked")
    private HT_ItemStackBuilder4Recipe<T> isb4r (Item item) {
        return new HT_ItemStackBuilder4Recipe<>((T) this, item);
    }

    @SuppressWarnings("unchecked")
    private HT_ItemStackBuilder4Recipe<T> isb4r (Block block) {
        return new HT_ItemStackBuilder4Recipe<>((T) this, block);
    }

    public static class Impl extends HT_CraftingRecipeBuilder<Impl> {
    }
}
