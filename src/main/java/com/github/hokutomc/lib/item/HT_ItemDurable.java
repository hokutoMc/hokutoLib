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
 * This classes adds data of item durability without using damage value.
 *
 * 2014/10/11.
 */
public abstract class HT_ItemDurable extends HT_Item<HT_ItemDurable> {
    public static final String KEY_DURABILITY = "durability";
    private static final String KEY_BROKEN = "broken";
    private static Random random = new Random();

    public HT_ItemDurable (String modid, String innerName) {
        super(modid, innerName);
        this.HT_setMaxStackSize(1);
    }

    public HT_ItemStackBuilder getProducer (int durability, int meta) {
        return new HT_ItemStackBuilder(this, meta).setInt(KEY_DURABILITY, durability).setBoolean(KEY_BROKEN, false);
    }

    public HT_ItemStackBuilder getProducer (int meta) {
        return new HT_ItemStackBuilder(this, meta) {
            @Override
            public ItemStack build (int size) {
                ItemStack stack = super.build(size);
                stack.stackTagCompound.setInteger(KEY_DURABILITY, getMaxDurability(stack));
                return stack;
            }
        }.setBoolean(KEY_BROKEN, false);
    }

    public ItemStack create (int meta) {
        ItemStack stack = this.create(10, meta);
        this.updateDurability(stack, this.getMaxDurability(stack));
        return stack;
    }

    public ItemStack create (final int durability, int meta) {
        ItemStack stack = HT_ItemStackUtil.createItemStack(this, 1, meta);
        stack.stackTagCompound.setBoolean(KEY_BROKEN, false);
        stack.stackTagCompound.setInteger(KEY_DURABILITY, durability);
        return stack;
    }

    @Override
    public void HT_addInformation (ItemStack itemStack, EntityPlayer player, List<String> list, boolean b) {
        super.HT_addInformation(itemStack, player, list, b);
        if (this.isBroken(itemStack)) {
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("item.hokutolib.status.broken.name").trim());
        } else {
            EnumChatFormatting red = EnumChatFormatting.RESET;
            if (this.getDurability(itemStack) * 20 < this.getMaxDurability(itemStack)) {
                red = EnumChatFormatting.RED;
            }
            list.add(
                    red
                            + StatCollector.translateToLocal("item.hokutolib.status.durability.name").trim()
                            + HT_StringUtil.blanks(6)
                            + this.getDurability(itemStack)
                            + "/"
                            + this.getMaxDurability(itemStack));
        }
    }

    @Override
    public boolean showDurabilityBar (ItemStack stack) {
        return !this.isBroken(stack) || this.getDurability(stack) < this.getMaxDurability(stack);
    }

    @Override
    public double getDurabilityForDisplay (ItemStack stack) {
        return ((double) this.getDurability(stack)) / ((double) this.getMaxDurability(stack));
    }

    public abstract int getMaxDurability (ItemStack itemStack);

    public int getDurability (ItemStack itemStack) {
        NBTTagCompound tag = itemStack.stackTagCompound;
        return tag.getInteger(KEY_DURABILITY);
    }

    public void updateDurability (ItemStack itemStack, int durability) {
        if (durability <= 0) {
            itemStack.stackTagCompound.setBoolean(KEY_BROKEN, true);
            this.onUsingBrokenItem(itemStack);
            durability = 0;
        } else if (this.isBroken(itemStack)) {
            itemStack.stackTagCompound.setBoolean(KEY_BROKEN, false);
        }
        if (this.getMaxDurability(itemStack) < durability) {
            durability = this.getMaxDurability(itemStack);
        }

        itemStack.stackTagCompound.setInteger(KEY_DURABILITY, durability);

    }

    public boolean isBroken (ItemStack itemStack) {
        return itemStack.stackTagCompound.getBoolean(KEY_BROKEN);
    }

    protected void onUsingBrokenItem (ItemStack itemStack) {
    }

    public void decreaseDurabilityBy (ItemStack itemStack, int damage) {
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        for (int i = 0; j > 0 && i < damage; i++) {
            if (EnchantmentDurability.negateDamage(itemStack, j, random)) {
                damage -= this.HT_getBonusWithEfficency(itemStack);
            }
        }
        this.updateDurability(itemStack, this.getDurability(itemStack) - damage);
    }


    protected abstract int HT_getBonusWithEfficency (ItemStack itemStack);

    @Override
    public abstract void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list);

}
