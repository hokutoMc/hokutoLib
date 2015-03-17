package com.github.hokutomc.lib.item.tool;

import com.github.hokutomc.lib.item.HT_ItemDurable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class can be used to make tool.
 * The tool doesn't use metadata.
 * You can make a wide variety of tools using this class only in one instance.
 * <p/>
 * 2014/10/12.
 */
public abstract class HT_ItemTool<T extends HT_ItemTool<T>> extends HT_ItemDurable<T> {

    public abstract static class Raw extends HT_ItemTool<Raw> {

        public Raw (String modid, String innerName) {
            super(modid, innerName);
        }
    }

    protected float efficiencyOnProperMaterial = 4.0F;
    private static Random random = new Random();

    public HT_ItemTool (String modid, String innerName) {
        super(modid, innerName);
    }

//    @Override
//    public void HT_addInformation (ItemStack itemStack, EntityPlayer player, List<String> list, boolean b) {
//        super.HT_addInformation(itemStack, player, list, b);
//        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("item.hokutolib.status.attack.name", Math.round(this.getMaxAttackDamage(itemStack))));
//    }


    @Override
    public void HT_addInformation (ItemStack itemStack, EntityPlayer player, List<String> list, boolean b) {
        super.HT_addInformation(itemStack, player, list, b);
        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("item.hokutolib.status.attack.name", Math.round(this.getMaxAttackDamage(itemStack))));
    }

    @Override
    public boolean isItemTool (ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean hitEntity (ItemStack itemStack, EntityLivingBase target, EntityLivingBase holder) {
        this.decreaseDurabilityBy(itemStack, this.getDamageHittingEntity(itemStack, target));
        return true;
    }

    @Override
    public boolean onBlockDestroyed (ItemStack itemStack, World world, Block block, BlockPos pos, EntityLivingBase holder) {
        if ((double) block.getBlockHardness(world, pos) != 0.0D) {
            this.decreaseDurabilityBy(itemStack, this.getDamageBreakingBlock(itemStack, block, pos));
        }
        return true;
    }

    protected abstract int getDamageBreakingBlock (ItemStack itemStack, Block block, BlockPos pos);

    protected abstract int getDamageHittingEntity (ItemStack itemStack, EntityLivingBase target);

    @Override
    public boolean isFull3D () {
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

    public float getAttackDamage (ItemStack itemStack) {
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
    public float getDigSpeed (ItemStack stack, IBlockState state) {
        if (this.isBroken(stack)) {
            return 0.001f;
        }
        if (state.getBlock().isToolEffective(this.getToolType(stack), state)) {
            return efficiencyOnProperMaterial;
        }
        return super.getDigSpeed(stack, state);
    }

    @Override
    public Multimap getAttributeModifiers (ItemStack stack) {
        return super.getAttributeModifiers(stack);
    }

    protected boolean isHoe (ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean onItemUse (ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!player.func_175151_a(pos, side, itemStack)) {
            return false;
        } else if (isHoe(itemStack)) {
            UseHoeEvent event = new UseHoeEvent(player, itemStack, world, pos);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            }

//            if (event.getResult() == Event.Result.ALLOW) {
//                itemStack.damageItem(1, player);
//                return true;
//            }

            IBlockState state = world.getBlockState(pos);

            if (side != EnumFacing.UP && world.getBlockState(pos.add(0, 1, 0)).getBlock().isAir(world, pos.add(0, 1, 0)) && (state.getBlock() == Blocks.grass || state.getBlock() == Blocks.dirt)) {
                Block block1 = Blocks.farmland;
                world.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), block1.stepSound.getStepSound(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getFrequency() * 0.8F);

                if (world.isRemote) {
                    return true;
                } else {
                    world.setBlockState(pos, block1.getDefaultState());
                    itemStack.damageItem(1, player);
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
