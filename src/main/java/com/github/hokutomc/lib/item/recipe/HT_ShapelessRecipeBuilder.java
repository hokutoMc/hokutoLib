package com.github.hokutomc.lib.item.recipe;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/02/07.
 */
public class HT_ShapelessRecipeBuilder extends HT_RecipeBuilder<HT_ShapelessRecipeBuilder> {
    private HT_CraftingRecipeBuilder craftingRecipeBuilder;
    private ISMode mode;

    private List<ItemStack> itemStackList;
    private ItemStack result;
    private boolean isOreRecipe;
    private List<String> oreNameList;

    private enum ISMode {
        SOURCE,RESULT,OTHER
    }

    HT_ShapelessRecipeBuilder (HT_CraftingRecipeBuilder craftingRecipeBuilder) {
        this.craftingRecipeBuilder = craftingRecipeBuilder;
        this.itemStackList = Lists.newArrayList();
        this.oreNameList = Lists.newArrayList();
        this.mode = ISMode.OTHER;
    }

    public HT_ShapelessRecipeBuilder () {
        this(new HT_CraftingRecipeBuilder());
    }

    @Override
    protected HT_ShapelessRecipeBuilder onReturned (HT_ItemStackBuilder4Recipe itemStackBuilder) {
        switch (this.mode) {
            case SOURCE: this.itemStackList.add(itemStackBuilder.build()); break;
            case RESULT: this.result = itemStackBuilder.build();
                addRecipe();
                break;
        }
        return this;
    }

    private void addRecipe () {
        ArrayList<Object> list = new ArrayList<>();
        list.addAll(this.itemStackList);
        if (this.isOreRecipe) {
            list.addAll(this.oreNameList);
            GameRegistry.addRecipe(new ShapelessOreRecipe(this.result,list.toArray()));
        } else {
            GameRegistry.addShapelessRecipe(this.result, list.toArray());
        }

        this.isOreRecipe = false;
        this.oreNameList.clear();
        this.itemStackList.clear();
        this.oreNameList.clear();
    }

    public HT_ItemStackBuilder4Recipe<HT_ShapelessRecipeBuilder> from (Item item) {
        this.mode = ISMode.SOURCE;
        return new HT_ItemStackBuilder4Recipe<>(this, item);
    }

    public HT_ItemStackBuilder4Recipe<HT_ShapelessRecipeBuilder> from (Block block) {
        this.mode = ISMode.SOURCE;
        return new HT_ItemStackBuilder4Recipe<>(this, block);
    }

    public HT_ShapelessRecipeBuilder fromOre (String oreName) {
        this.mode = ISMode.SOURCE;
        return andOre(oreName);
    }

    public HT_ItemStackBuilder4Recipe<HT_ShapelessRecipeBuilder> and (Item item) {
        return new HT_ItemStackBuilder4Recipe<>(this, item);
    }

    public HT_ItemStackBuilder4Recipe<HT_ShapelessRecipeBuilder> and (Block block) {
        return new HT_ItemStackBuilder4Recipe<>(this, block);
    }

    public HT_ShapelessRecipeBuilder andOre (String oreName) {
        this.isOreRecipe = true;
        this.oreNameList.add(oreName);
        return this;
    }

    public HT_ItemStackBuilder4Recipe<HT_ShapelessRecipeBuilder> to (Item item) {
        this.mode = ISMode.RESULT;
        return new HT_ItemStackBuilder4Recipe<>(this, item);
    }

    public HT_ItemStackBuilder4Recipe<HT_ShapelessRecipeBuilder> to (Block block) {
        this.mode = ISMode.RESULT;
        return new HT_ItemStackBuilder4Recipe<>(this, block);
    }


    public HT_CraftingRecipeBuilder shaped () {
        return this.craftingRecipeBuilder;
    }
}
