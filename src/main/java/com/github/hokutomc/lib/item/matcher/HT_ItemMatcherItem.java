package com.github.hokutomc.lib.item.matcher;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/04/10.
 */
public class HT_ItemMatcherItem extends HT_ItemMatcher {
    public final Item item;
    public final int damage;
    public final boolean damageSensitive;

    private HT_ItemMatcherItem (Item item, int damage, boolean damageSensitive) {
        this.item = item;
        this.damage = damage;
        this.damageSensitive = damageSensitive;
    }

    public HT_ItemMatcherItem (Item item, int damage) {
        this(item, damage, true);
    }

    public HT_ItemMatcherItem (Item item) {
        this(item, -1, false);
    }

    public HT_ItemMatcherItem (Block block, int damage) {
        this(Item.getItemFromBlock(block), damage);
    }

    public HT_ItemMatcherItem (Block block) {
        this(Item.getItemFromBlock(block), -1, false);
    }

    @Override
    protected boolean check (ItemStack itemStack) {
        return item == itemStack.getItem() && (!damageSensitive || damage == itemStack.getItemDamage());
    }

    public static HT_ItemMatcher ofStack (ItemStack itemStack) {
        return new HT_ItemMatcherItem(itemStack.getItem(), itemStack.getItemDamage());
    }
}
