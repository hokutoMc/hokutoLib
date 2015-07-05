package com.github.hokutomc.lib.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

/**
 * Created by user on 2015/03/26.
 */
public abstract class HT_ItemFood<T extends HT_ItemFood<T>> extends HT_Item<T> {
    public static class Impl extends HT_ItemFood<Impl> {
        public Impl (String modid, String innerName, int healAmount, float saturationModifier) {
            super(modid, innerName, healAmount, saturationModifier);
        }

        public Impl (String modid, String innerName, int healAmount) {
            this(modid, innerName, healAmount, 0.6f);
        }
    }

    /**
     * Number of ticks to run while 'EnumAction'ing until result.
     */
    public final int itemUseDuration;
    /**
     * The amount this food block heals the player.
     */
    private final int healAmount;
    private final float saturationModifier;
    /**
     * If this field is true, the food can be consumed even if the player don't need to eat.
     */
    private boolean alwaysEdible;
    /**
     * represents the potion effect that will occurr upon eating this food. Set by setPotionEffect
     */
    private int potionId;
    /**
     * set by setPotionEffect
     */
    private int potionDuration;
    /**
     * set by setPotionEffect
     */
    private int potionAmplifier;
    /**
     * probably of the set potion effect occurring
     */
    private float potionEffectProbability;

    public HT_ItemFood (String modid, String innerName, int healAmount, float saturationModifier) {
        super(modid, innerName);
        this.healAmount = healAmount;
        this.saturationModifier = saturationModifier;
        itemUseDuration = 32;
    }

    public HT_ItemFood (String modid, String innerName, int healAmount) {
        this(modid, innerName, healAmount, 0.6f);
    }


    @Override
    public ItemStack onItemRightClick (ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if (this.getIsAlwaysEdible(itemStackIn))
            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        return itemStackIn;
    }
    
    @Override
    public ItemStack onItemUseFinish (ItemStack stack, World worldIn, EntityPlayer playerIn) {
        --stack.stackSize;
        playerIn.getFoodStats().addStats(this.getHealAmount(stack), this.getSaturationModifier(stack));
        worldIn.playSoundAtEntity(playerIn, "random.burp", 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, worldIn, playerIn);
        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return stack;
    }

    protected boolean getIsAlwaysEdible (ItemStack itemStack) {
        return this.alwaysEdible;
    }

    protected float getSaturationModifier (ItemStack stack) {
        return this.saturationModifier;
    }

    protected int getHealAmount (ItemStack stack) {
        return this.healAmount;
    }

    protected void onFoodEaten (ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (!worldIn.isRemote && this.potionId > 0 && worldIn.rand.nextFloat() < this.potionEffectProbability) {
            playerIn.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }

    @Override
    public int getMaxItemUseDuration (ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction (ItemStack stack) {
        return EnumAction.EAT;
    }

    @SuppressWarnings("unchecked")
    public T setPotionEffect (int p_77844_1_, int duration, int amplifier, float probability) {
        this.potionId = p_77844_1_;
        this.potionDuration = duration;
        this.potionAmplifier = amplifier;
        this.potionEffectProbability = probability;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setAlwaysEdible () {
        this.alwaysEdible = true;
        return (T) this;
    }
}
