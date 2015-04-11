package com.github.hokutomc.lib.oredict;

import com.github.hokutomc.lib.item.matcher.HT_ItemMatcher;
import com.github.hokutomc.lib.item.matcher.HT_ItemMatcherItem;
import com.github.hokutomc.lib.item.matcher.HT_ItemMatcherOre;
import com.github.hokutomc.lib.util.HT_OreUtil;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2015/04/10.
 */
public class HT_ShapedOreRecipePlus implements IRecipe {
    private static final int MAX_CRAFT_GRID_WIDTH = 3;
    private static final int MAX_CRAFT_GRID_HEIGHT = 3;

    private ItemStack output = null;
    private HT_ItemMatcher[] input = null;
    private int width = 0;
    private int height = 0;
    private boolean mirrored = true;

    public HT_ShapedOreRecipePlus (Block result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public HT_ShapedOreRecipePlus (Item result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public HT_ShapedOreRecipePlus (ItemStack result, Object... recipe) {
        output = result.copy();

        String shape = "";
        int idx = 0;

        if (recipe[idx] instanceof Boolean) {
            mirrored = (Boolean) recipe[idx];
            if (recipe[idx + 1] instanceof Object[]) {
                recipe = (Object[]) recipe[idx + 1];
            } else {
                idx = 1;
            }
        }

        if (recipe[idx] instanceof String[]) {
            String[] parts = ((String[]) recipe[idx++]);

            for (String s : parts) {
                width = s.length();
                shape += s;
            }

            height = parts.length;
        } else {
            while (recipe[idx] instanceof String) {
                String s = (String) recipe[idx++];
                shape += s;
                width = s.length();
                height++;
            }
        }

        if (width * height != shape.length()) {
            String ret = "Invalid shaped ore recipe: ";
            for (Object tmp : recipe) {
                ret += tmp + ", ";
            }
            ret += output;
            throw new RuntimeException(ret);
        }

        HashMap<Character, HT_ItemMatcher> itemMap = Maps.newHashMap();

        for (; idx < recipe.length; idx += 2) {
            Character chr = (Character) recipe[idx];
            Object in = recipe[idx + 1];

            if (in instanceof ItemStack) {
                itemMap.put(chr, HT_ItemMatcherItem.ofStack(((ItemStack) in).copy()));
            } else if (in instanceof Item) {
                itemMap.put(chr, new HT_ItemMatcherItem((Item) in));
            } else if (in instanceof Block) {
                itemMap.put(chr, new HT_ItemMatcherItem((Block) in));
            } else if (in instanceof String) {
                itemMap.put(chr, new HT_ItemMatcherOre((String) in));
            } else {
                String ret = "Invalid shaped ore recipe: ";
                for (Object tmp : recipe) {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }

        input = new HT_ItemMatcher[width * height];
        int x = 0;
        for (char chr : shape.toCharArray()) {
            input[x++] = itemMap.get(chr);
        }
    }

    @SuppressWarnings("unchecked")
    public HT_ShapedOreRecipePlus (ShapedOreRecipe replaced) {

        output = replaced.getRecipeOutput();
        width = getWidth(replaced);
        height = getHeight(replaced);

        Object[] otherInput = getInput(replaced);

        input = new HT_ItemMatcher[otherInput.length];

        for (int i = 0; i < input.length; i++) {
            Object ingred = otherInput[i];

            if (ingred == null) continue;

            if (ingred instanceof List) {
                input[i] = new HT_ItemMatcherOre(HT_OreUtil.assumeNameFromStacks((List<ItemStack>) ingred));
            } else if (ingred instanceof ItemStack) {
                input[i] = HT_ItemMatcherItem.ofStack((ItemStack) ingred);
            }
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult (InventoryCrafting var1) {
        return output.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize () {
        return input.length;
    }

    @Override
    public ItemStack getRecipeOutput () {
        return output;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches (InventoryCrafting inv, World world) {
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {
                if (checkMatch(inv, x, y, false)) {
                    return true;
                }

                if (mirrored && checkMatch(inv, x, y, true)) {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean checkMatch (InventoryCrafting inv, int startX, int startY, boolean mirror) {
        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {
                int subX = x - startX;
                int subY = y - startY;
                HT_ItemMatcher target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
                    if (mirror) {
                        target = input[width - subX - 1 + subY * width];
                    } else {
                        target = input[subX + subY * width];
                    }
                }

                ItemStack slot = inv.getStackInRowAndColumn(x, y);

                if (target != null) {
                    if (!target.matches(slot)) return false;
                } else if (slot != null) {
                    return false;
                }
            }
        }

        return true;
    }

    public HT_ShapedOreRecipePlus setMirrored (boolean mirror) {
        mirrored = mirror;
        return this;
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     *
     * @return The recipes input vales.
     */
    public Object[] getInput () {
        return this.input;
    }

    @Override
    public ItemStack[] func_179532_b (InventoryCrafting inv) //getRecipeLeftovers
    {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }

    private static int getWidth (ShapedOreRecipe replaced) {
        return getIntField(replaced, "width");
    }

    private static int getHeight (ShapedOreRecipe replaced) {
        return getIntField(replaced, "height");
    }

    private static int getIntField (ShapedOreRecipe replaced, String name) {
        Class<? extends ShapedOreRecipe> aClass = replaced.getClass();
        try {
            return (int) (aClass.getField(name).get(replaced));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static Object[] getInput (ShapedOreRecipe replaced) {
        Class<? extends ShapedOreRecipe> aClass = replaced.getClass();
        try {
            return (Object[]) (aClass.getField("input").get(replaced));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return new Object[0];
    }
}
