package com.github.hokutomc.lib.item;


import com.github.hokutomc.lib.util.HT_StringUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;
import java.util.Random;

/**
 * Created by user on 2014/10/11.
 */
public abstract class HT_ItemDurable extends HT_AbstractItem<HT_ItemDurable> {
    public static final String KEY_DURABILITY = "durability";
    private static final String KEY_BROKEN = "broken";
    private static Random random = new Random();

    public HT_ItemDurable (String modid, String innerName) {
        super(modid, innerName);
        this.HT_setMaxStackSize(1);
    }

    public ItemStack HT_create (int meta) {
        ItemStack stack = this.HT_create(10, meta);
        this.HT_updateDurability(stack, this.HT_getMaxDurability(stack));
        return stack;
    }

    public ItemStack HT_create (final int durability, int meta) {
        ItemStack stack = HT_ItemStackUtil.createItemStack(this, 1, meta);
        stack.stackTagCompound.setBoolean(KEY_BROKEN, false);
        stack.stackTagCompound.setInteger(KEY_DURABILITY, durability);
        return stack;
    }

    @Override
    public void HT_addInformation (ItemStack itemStack, EntityPlayer player, List<String> list, boolean b) {
        super.HT_addInformation(itemStack, player, list, b);
        if (this.HT_isBroken(itemStack)) {
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("item.hokutoUtil.status.broken.name").trim());
        } else {
            EnumChatFormatting red = EnumChatFormatting.RESET;
            if (this.HT_getDurability(itemStack) * 20 < this.HT_getMaxDurability(itemStack)) {
                red = EnumChatFormatting.RED;
            }
            list.add(
                    red
                            + StatCollector.translateToLocal("item.hokutoUtil.status.durability.name").trim()
                            + HT_StringUtil.blanks(6)
                            + this.HT_getDurability(itemStack)
                            + "/"
                            + this.HT_getMaxDurability(itemStack));
        }
    }

    @Override
    public boolean showDurabilityBar (ItemStack stack) {
        return !this.HT_isBroken(stack) || this.HT_getDurability(stack) < this.HT_getMaxDurability(stack);
    }

    @Override
    public double getDurabilityForDisplay (ItemStack stack) {
        return ((double) this.HT_getDurability(stack)) / ((double) this.HT_getMaxDurability(stack));
    }

    public abstract int HT_getMaxDurability (ItemStack itemStack);

    public int HT_getDurability (ItemStack itemStack) {
        NBTTagCompound tag = itemStack.stackTagCompound;
        return tag.getInteger(KEY_DURABILITY);
    }

    public void HT_updateDurability (ItemStack itemStack, int durability) {
        if (durability <= 0) {
            itemStack.stackTagCompound.setBoolean(KEY_BROKEN, true);
            this.HT_onUsingBrokenItem(itemStack);
            durability = 0;
        } else if (this.HT_isBroken(itemStack)) {
            itemStack.stackTagCompound.setBoolean(KEY_BROKEN, false);
        }
        if (this.HT_getMaxDurability(itemStack) < durability) {
            durability = this.HT_getMaxDurability(itemStack);
        }

        itemStack.stackTagCompound.setInteger(KEY_DURABILITY, durability);

    }

    public boolean HT_isBroken (ItemStack itemStack) {
        return itemStack.stackTagCompound.getBoolean(KEY_BROKEN);
    }

    protected void HT_onUsingBrokenItem (ItemStack itemStack) {
    }

    public void HT_decreaseDurability (ItemStack itemStack, int damage) {
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        for (int i = 0; j > 0 && i < damage; i++) {
            if (EnchantmentDurability.negateDamage(itemStack, j, random)) {
                damage -= this.HT_getBounusWithEfficency(itemStack);
            }
        }
        this.HT_updateDurability(itemStack, this.HT_getDurability(itemStack) - damage);
    }

    protected abstract int HT_getBounusWithEfficency (ItemStack itemStack);

    @Override
    public abstract void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list);

}
