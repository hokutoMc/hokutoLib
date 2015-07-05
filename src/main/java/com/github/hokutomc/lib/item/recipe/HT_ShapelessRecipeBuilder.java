package com.github.hokutomc.lib.item.recipe;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/02/07.
 */
public class HT_ShapelessRecipeBuilder {

    private List<ItemStack> itemStackList;
    private ItemStack result;
    private boolean isOreRecipe;
    private List<String> oreNameList;


    public HT_ShapelessRecipeBuilder () {
        this.itemStackList = Lists.newArrayList();
        this.oreNameList = Lists.newArrayList();
        init();
    }

    private void init () {
        this.isOreRecipe = false;
        this.oreNameList.clear();
        this.itemStackList.clear();
        this.oreNameList.clear();
    }

    public HT_ShapelessRecipeBuilder addRecipe () {
        ArrayList<Object> list = new ArrayList<>();
        list.addAll(this.itemStackList);
        if (this.isOreRecipe) {
            list.addAll(this.oreNameList);
            GameRegistry.addRecipe(new ShapelessOreRecipe(this.result, list.toArray()));
        } else {
            GameRegistry.addShapelessRecipe(this.result, list.toArray());
        }

        init();
        return this;
    }

    public HT_ShapelessRecipeBuilder from (Item item) {
        return from(new ItemStack(item));
    }

    public HT_ShapelessRecipeBuilder from (Block block) {
        return from(new ItemStack(block));
    }

    public HT_ShapelessRecipeBuilder from (ItemStack itemStack) {
        this.itemStackList.add(itemStack);
        return this;
    }

    public HT_ShapelessRecipeBuilder fromOre (String oreName) {
        return andOre(oreName);
    }

    public HT_ShapelessRecipeBuilder and (Item item) {
        return and(new ItemStack(item));
    }

    public HT_ShapelessRecipeBuilder and (Block block) {
        return and(new ItemStack(block));
    }

    public HT_ShapelessRecipeBuilder and (ItemStack itemStack) {
        this.itemStackList.add(itemStack);
        return this;
    }

    public HT_ShapelessRecipeBuilder andOre (String oreName) {
        this.isOreRecipe = true;
        this.oreNameList.add(oreName);
        return this;
    }

    public HT_ShapelessRecipeBuilder to (Item item) {
        return to(new ItemStack(item));
    }

    public HT_ShapelessRecipeBuilder to (Block block) {
        return to(new ItemStack(block));
    }


    public HT_ShapelessRecipeBuilder to (ItemStack itemStack) {
        this.result = itemStack;
        return this;
    }
}
