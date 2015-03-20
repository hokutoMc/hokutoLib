package com.github.hokutomc.lib.item;


import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import com.github.hokutomc.lib.util.HT_StringUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
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
public abstract class HT_ItemDurable<T extends HT_ItemDurable> extends HT_Item<T> {
    public static final String KEY_DURABILITY = "durability";
    public static final String KEY_BROKEN = "broken";
    private static Random random = new Random();

    public HT_ItemDurable (String modid, String innerName) {
        super(modid, innerName);
        this.HT_setMaxStackSize(1);
        this.m_subItems.clear();
        this.m_subItems.add(new HT_ItemStackBuilder(this).fullDurability());
    }

    @Override
    public T multi (String... subNames) {
        T t = super.multi(subNames);
        for (HT_ItemStackBuilder item : this.m_subItems) {
            item.fullDurability();
        }
        return t;
    }

    public HT_ItemStackBuilder getBuilder (int durability, int meta) {
        return new HT_ItemStackBuilder(this).damage(meta).setInt(KEY_DURABILITY, durability).setBoolean(KEY_BROKEN, false);
    }

    public HT_ItemStackBuilder getBuilder (int meta) {
        return new HT_ItemStackBuilder(this) {
            @Override
            public ItemStack build (int size) {
                ItemStack stack = super.build(size);
                stack.getTagCompound().setInteger(KEY_DURABILITY, getMaxDurability(stack));
                return stack;
            }
        }.damage(meta).setBoolean(KEY_BROKEN, false);
    }

    @Override
    public void HT_addInformation (ItemStack itemStack, EntityPlayer player, List<String> list, boolean b) {
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
        return 1 - (((double) this.getDurability(stack)) / ((double) this.getMaxDurability(stack)));
    }

    public abstract int getMaxDurability (ItemStack itemStack);

    public int getDurability (ItemStack itemStack) {
        NBTTagCompound tag = itemStack.getTagCompound();
        return HT_NBTUtil.getInteger(KEY_DURABILITY, tag, 0);
    }

    public void updateDurability (ItemStack itemStack, int durability) {
        if (durability <= 0) {
            itemStack.getTagCompound().setBoolean(KEY_BROKEN, true);
            this.onUsingBrokenItem(itemStack);
            durability = 0;
        } else if (this.isBroken(itemStack)) {
            itemStack.getTagCompound().setBoolean(KEY_BROKEN, false);
        }
        if (this.getMaxDurability(itemStack) < durability) {
            durability = this.getMaxDurability(itemStack);
        }

        itemStack.getTagCompound().setInteger(KEY_DURABILITY, durability);

    }

    public boolean isBroken (ItemStack itemStack) {
        return HT_NBTUtil.getBoolean(KEY_BROKEN, itemStack.getTagCompound(), false);
    }

    protected void onUsingBrokenItem (ItemStack itemStack) {
    }

    public void decreaseDurabilityBy (ItemStack itemStack, int damage) {
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        for (int i = 0; j > 0 && i < damage; i++) {
            if (EnchantmentDurability.negateDamage(itemStack, j, random)) {
                damage -= this.getBonusWithEfficency(itemStack);
            }
        }
        this.updateDurability(itemStack, this.getDurability(itemStack) - damage);
    }


    protected abstract int getBonusWithEfficency (ItemStack itemStack);

}
