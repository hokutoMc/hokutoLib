package com.github.hokutomc.lib.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class can be used to make tool.
 * The tool doesn't use metadata.
 * You can make a wide variety of tools using this class only in one instance.
 *
 * 2014/10/12.
 */
public abstract class HT_ItemTool extends HT_ItemDurable {
    protected float efficiencyOnProperMaterial = 4.0F;
    private static Random random = new Random();

    public HT_ItemTool (String modid, String innerName) {
        super(modid, innerName);
    }

    @Override
    public void HT_addInformation (ItemStack itemStack, EntityPlayer player, List<String> list, boolean b) {
        super.HT_addInformation(itemStack, player, list, b);
        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("item.hokutolib.status.attack.name", Math.round(this.getMaxAttackDamage(itemStack))));
    }

    @Override
    public boolean HT_isItemTool (ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean HT_hitEntity (ItemStack itemStack, EntityLivingBase target, EntityLivingBase holder) {
        this.decreaseDurabilityBy(itemStack, this.getDamageHittingEntity(itemStack, target));
        return true;
    }

    @Override
    public boolean HT_onBlockDestroyed (ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase holder) {
        if ((double) block.getBlockHardness(world, x, y, z) != 0.0D) {
            this.decreaseDurabilityBy(itemStack, this.getDamageBreakingBlock(itemStack, block, x, y, z));
        }
        return true;
    }

    protected abstract int getDamageBreakingBlock (ItemStack itemStack, Block block, int x, int y, int z);

    protected abstract int getDamageHittingEntity (ItemStack itemStack, EntityLivingBase target);

    @Override
    public boolean HT_isFull3D () {
        return true;
    }


    @Override
    public int getHarvestLevel (ItemStack stack, String toolClass) {
        int level = super.getHarvestLevel(stack, toolClass);
        if (level == -1 && toolClass != null && toolClass.equals(this.getToolType(stack))) {
            return this.getToolMaterial(stack).getHarvestLevel();
        } else {
            return level;
        }
    }

    protected abstract ToolMaterial getToolMaterial (ItemStack stack);

    protected abstract String getToolType (ItemStack itemStack);

    protected abstract float getMaxAttackDamage (ItemStack itemStack);

    public float HT_getAttackDamage (ItemStack itemStack) {
        if (this.isBroken(itemStack)) {
            return 1.0f;
        }
        return this.getMaxAttackDamage(itemStack);
    }

    public float getRangeBonus (ItemStack tool, float distance) {
        if (distance < 1.0f) return 2.0f;
        if (distance > 3.0f) return 0.0f;
        return 1.0f;
    }

    @Override
    public Set<String> getToolClasses (ItemStack stack) {
        return this.getToolType(stack) != null ? ImmutableSet.of(this.getToolType(stack)) : super.getToolClasses(stack);
    }

    @Override
    public float getDigSpeed (ItemStack stack, Block block, int meta) {
        if (this.isBroken(stack)) {
            return 0.001f;
        }
        if (ForgeHooks.isToolEffective(stack, block, meta)) {
            return efficiencyOnProperMaterial;
        }
        return super.getDigSpeed(stack, block, meta);
    }

    @Override
    public Multimap getAttributeModifiers (ItemStack stack) {
        return super.getAttributeModifiers(stack);
    }


}
