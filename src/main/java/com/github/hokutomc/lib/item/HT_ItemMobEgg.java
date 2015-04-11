package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.entity.HT_ModEntityList;
import com.github.hokutomc.lib.util.HT_I18nUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * 2015/01/06.
 */
public class HT_ItemMobEgg extends HT_Item<HT_ItemMobEgg> {
    private HT_ModEntityList m_listEntity;

    public HT_ItemMobEgg (String modid, HT_ModEntityList entityList) {
        super(modid, "mobEgg");
        this.m_listEntity = entityList;
        this.HT_setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list) {
        this.m_subItems.clear();
        for (int i = 0; i < m_listEntity.size(); i++) {
            m_subItems.add(new HT_ItemStackBuilder.Raw(this).damage(i));
        }
        super.HT_registerMulti(item, tab, list);
    }

    @Override
    public String getItemStackDisplayName (ItemStack itemStack) {
        return HT_I18nUtil.localize("item.hokutolib.mobEgg.name", HT_I18nUtil.localize("entity." + this.m_listEntity.getNameAt(itemStack.getItemDamage()) + ".name"));
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            IBlockState blockState = worldIn.getBlockState(pos);
            BlockPos set = pos.offset(side);
            double d0 = 0.0D;

            if (side == EnumFacing.DOWN && blockState.getBlock().getRenderType() == 11) {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(worldIn, stack.getItemDamage(), (double) set.getX() + 0.5D, (double) set.getY() + d0, (double) set.getZ() + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
                    entity.setCustomNameTag(stack.getDisplayName());
                }

                if (!playerIn.capabilities.isCreativeMode) {
                    --stack.stackSize;
                }
            }

            return true;
        }
    }

    @Override
    public ItemStack onItemRightClick (ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        } else {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null) {
                return itemStack;
            } else {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos pos = movingobjectposition.func_178782_a();

                    if (!world.canMineBlockBody(player, pos)) {
                        return itemStack;
                    }

                    if (!player.func_175151_a(pos, movingobjectposition.field_178784_b, itemStack)) {
                        return itemStack;
                    }

                    if (world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                        Entity entity = spawnCreature(world, itemStack.getItemDamage(), pos.getX(), pos.getY(), pos.getZ());

                        if (entity != null) {
                            if (entity instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                                entity.setCustomNameTag(itemStack.getDisplayName());
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
            EntityLiving entityliving = (EntityLiving) entity;
            entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
            entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            world.spawnEntityInWorld(entity);
            entityliving.playLivingSound();
        }
        return entity;
    }

    @Override
    public int getColorFromItemStack (ItemStack stack, int renderPass) {
        int damage = stack.getItemDamage();
        return renderPass == 1 ? this.m_listEntity.getSpotColorAt(damage) : (renderPass == 0 ? this.m_listEntity.getBaseColorAt(damage) : 0xffffff);
    }
}