package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.entity.HT_ModEntityList;
import com.github.hokutomc.lib.util.HT_I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by user on 2015/01/06.
 */
public class HT_ItemMobEgg extends HT_Item<HT_ItemMobEgg>{
    private HT_ModEntityList m_listEntity;

    public HT_ItemMobEgg (String modid, HT_ModEntityList entityList) {
        super(modid, "mobEgg");
        this.m_listEntity = entityList;
        this.HT_setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < m_listEntity.size(); i++) {
            list.add(HT_ItemStackUtil.createItemStack(item, 1, i));
        }
    }

    @Override
    public String HT_getItemStackDisplayName (ItemStack itemStack) {
        return HT_I18nUtil.localize("item.hokutoLib.mobEgg.name", HT_I18nUtil.localize("entity." + this.m_listEntity.getNameAt(itemStack.getItemDamage())+ ".name"));
    }

    @Override
    public boolean HT_onItemUse (ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            Block block = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double d0 = 0.0D;

            if (side == 1 && block.getRenderType() == 11) {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(world, itemStack.getItemDamage(), (double) x + 0.5D, (double) y + d0, (double) z + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                    ((EntityLiving)entity).setCustomNameTag(itemStack.getDisplayName());
                }

                if (!player.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
            }

            return true;
        }
    }

    @Override
    public ItemStack HT_onItemRightClick (ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        } else {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null) {
                return itemStack;
            } else {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!world.canMineBlock(player, i, j, k)) {
                        return itemStack;
                    }

                    if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStack)) {
                        return itemStack;
                    }

                    if (world.getBlock(i, j, k) instanceof BlockLiquid) {
                        Entity entity = spawnCreature(world, itemStack.getItemDamage(), (double) i, (double) j, (double) k);

                        if (entity != null) {
                            if (entity instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                                ((EntityLiving)entity).setCustomNameTag(itemStack.getDisplayName());
                            }

                            if (!player.capabilities.isCreativeMode) {
                                --itemStack.stackSize;
                            }
                        }
                    }
                }

                return itemStack;
            }
        }

    }

    private Entity spawnCreature (World world, int itemDamage, double x, double y, double z) {
        Entity entity = m_listEntity.createEntityById(itemDamage, world);
        if (entity != null && entity instanceof EntityLivingBase) {
            EntityLiving entityliving = (EntityLiving)entity;
            entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
            entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            entityliving.onSpawnWithEgg(null);
            world.spawnEntityInWorld(entity);
            entityliving.playLivingSound();
        }
        return entity;
    }
}
