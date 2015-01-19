package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.util.HT_I18nUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

/**
 * This class allows you to create a wide variety of armors only in one instance.
 *
 * 2014/10/12.
 */
public abstract class HT_ItemArmor extends HT_ItemDurable implements ISpecialArmor {

    private static final String KEY_PART = "armer_part";


    public HT_ItemArmor (String modid, String innerName) {
        super(modid, innerName);
    }

    public ItemStack HT_create (Part part, int meta) {
        ItemStack stack = this.create(meta);
        stack.stackTagCompound.setInteger(KEY_PART, part.ordinal());
        return stack;
    }

    @Override
    public String HT_getItemStackDisplayName (ItemStack itemStack) {
        return HT_I18nUtil.localize(this.getUnlocalizedName(itemStack) + "." + getPart(itemStack).toString() + ".name");
    }

    public Part getPart (ItemStack itemStack) {
        return Part.values()[itemStack.stackTagCompound.getInteger(KEY_PART)];
    }

    protected abstract ItemArmor.ArmorMaterial getArmorMaterial (ItemStack itemStack);

    @Override
    public ItemStack HT_onItemRightClick (ItemStack itemStack, World world, EntityPlayer player) {
        int i = EntityLiving.getArmorPosition(itemStack) - 1;
        ItemStack equipped = player.getCurrentArmor(i);

        if (equipped == null) {
            player.setCurrentItemOrArmor(i + 1, itemStack.copy());  //Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
            itemStack.stackSize = 0;
        }

        return itemStack;
    }

    @Override
    public void onArmorTick (World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
    }

    @Override
    public boolean isValidArmor (ItemStack stack, int armorType, Entity entity) {
        return Part.values()[armorType] == this.getPart(stack);
    }

    @Override
    protected int HT_getBonusWithEfficency (ItemStack itemStack) {
        return 0;
    }

    @Override
    protected void onUsingBrokenItem (ItemStack itemStack) {

    }

    protected int getArmorDamage (int damage, DamageSource damageSource, Part part) {
        return 1;
    }

    // ISpecialArmor Overrides

    @Override
    public abstract ArmorProperties getProperties (EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot);

    @Override
    public int getArmorDisplay (EntityPlayer player, ItemStack armor, int slot) {
        float durability = ((float) this.getDurability(armor)) / ((float) this.getMaxDurability(armor));

        for (Part part : Part.values()) {
            if (part.isSlot(slot)) return Math.round(this.getPartialDurability(armor, part) * durability + 0.5F);
        }
        return 0;
    }

    protected abstract float getPartialDurability (ItemStack armor, Part part);

    @Override
    public void damageArmor (EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        this.decreaseDurabilityBy(stack, this.getArmorDamage(damage, source, Part.getBySlot(slot)));
    }

    public static enum Part {
        HEAD("head"),
        CHEST("chest"),
        ARM("arm"),
        LEG("leg");
        private String name;

        Part (String name) {
            this.name = name;
        }

        public int slot () {
            return 4 - this.ordinal();
        }

        public boolean isSlot (int slot) {
            return slot == 4 - this.ordinal();
        }

        public static Part getBySlot (int slot) {
            for (Part e : values()) {
                if (e.isSlot(slot)) return e;
            }
            return null;
        }


        @Override
        public String toString () {
            return this.name;
        }
    }
}
