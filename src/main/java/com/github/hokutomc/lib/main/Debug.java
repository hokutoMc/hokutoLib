package com.github.hokutomc.lib.main;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.block.HT_Block;
import com.github.hokutomc.lib.block.HT_BlockFalling;
import com.github.hokutomc.lib.block.HT_ContainerBlock;
import com.github.hokutomc.lib.block.HT_MultiBlock;
import com.github.hokutomc.lib.client.gui.HT_GuiAction;
import com.github.hokutomc.lib.client.gui.HT_GuiHandler;
import com.github.hokutomc.lib.item.*;
import com.github.hokutomc.lib.util.HT_CreativeTabsUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by user on 2014/11/03.
 */
public class Debug {


    public static HT_ItemDurable itemDurable;
    public static HT_AbstractItem itemMulti;
    public static HT_MultiBlock blockMulti;
    public static HT_Block blockTE;
    public static CreativeTabs tabHTLib = HT_CreativeTabsUtil.create("hokutoLib", "test", Items.iron_sword);
    public static HT_ItemDurable itemArmor;
    private static HT_BlockFalling blockFall;

    public static void preinit () {
        itemDurable = new HT_ItemTool("hokutoLib", "tool") {

            @Override
            public int HT_getItemEnchantability () {
                return 5;
            }

            @Override
            public int HT_getMaxDurability (ItemStack itemStack) {
                return 1000;
            }

            @Override
            protected int HT_getBounusWithEfficency (ItemStack itemStack) {
                return 0;
            }

            @Override
            public void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list) {
                list.add(this.HT_create(0));
            }

            @Override
            public boolean HT_getIsRepairable (ItemStack itemStack, ItemStack materialItem) {
                return itemStack.getItem() == Items.iron_ingot;
            }

            @Override
            protected int HT_getDamageBreakingBlock (ItemStack itemStack, Block block, int x, int y, int z) {
                return 10;
            }

            @Override
            protected int HT_getDamageHittingEntity (ItemStack itemStack, EntityLivingBase target) {
                return 10;
            }

            @Override
            protected ToolMaterial HT_getToolMaterial (ItemStack stack) {
                return ToolMaterial.IRON;
            }

            @Override
            protected String HT_getToolType (ItemStack itemStack) {
                return "pickaxe";
            }

            @Override
            protected float HT_getMaxAttackDamage (ItemStack itemStack) {
                return 4.0f;
            }
        }.HT_setTextureName("iron_sword").HT_setCreativeTab(tabHTLib).HT_register();

        itemArmor = new HT_ItemArmor("hokutoLib", "armor") {
            @Override
            protected ItemArmor.ArmorMaterial HT_getMaterial (ItemStack itemStack) {
                return ItemArmor.ArmorMaterial.CHAIN;
            }

            @Override
            public ArmorProperties getProperties (EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
                return new ArmorProperties(0, 0, 0);
            }

            @Override
            protected float HT_getPartialDurability (ItemStack armor, Part part) {
                switch (part) {
                    case HEAD:
                        return 6.0F;
                    case CHEST:
                        return 6.0F;
                    case ARM:
                        return 4.0F;
                    case LEG:
                        return 4.0F;
                    default:
                        return 0;
                }
            }

            @Override
            public int HT_getMaxDurability (ItemStack itemStack) {
                return 1000;
            }

            @Override
            public void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list) {
                for (Part part : Part.values()) {
                    list.add(this.HT_create(part, 0));
                }
            }
        }.HT_setCreativeTab(tabHTLib).HT_register();
        itemMulti = new HT_MultiItem("hokutoLib", "multi", new String[]{"a", "b"}).HT_setCreativeTab(tabHTLib).HT_register();

        blockMulti = new HT_MultiBlock("hokutoLib", Material.wood, "multiblock", new String[]{"one", "two"}).HT_setCreativeTab(tabHTLib).HT_register();
        blockFall = new HT_BlockFalling("hokutoLib", Material.sand, "falling").HT_setCreativeTab(tabHTLib).HT_register();
        blockTE = new HT_ContainerBlock("hokutoLib", Material.iron, "te"){

            @Override
            public TileEntity createNewTileEntity (World p_149915_1_, int p_149915_2_) {
                return new TestTE();
            }

            @Override
            public boolean HT_onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float playerX, float playerY, float playerZ) {
                if (!world.isRemote & !player.isSneaking()) { player.openGui(Mod_HTLib.INSTANCE, 0, world, x, y, z); return true; }
                return false;
            }
        }.HT_setCreativeTab(tabHTLib).HT_register();

        HT_Registries.registerCommonTileEntity(TestTE.class, "htTest");
    }

    public static void init () {
        new HT_GuiHandler().HT_register(Mod_HTLib.INSTANCE).HT_addGui(0,
                new HT_GuiAction<ContainerSampleTE>() {
                    @Override
                    public ContainerSampleTE get (EntityPlayer player, World world, int x, int y, int z) {
                        return new ContainerSampleTE(player, ((TestTE) world.getTileEntity(x, y, z)));
                    }
                },
                new HT_GuiAction<ContainerSampleTE.GuiSampleTE>() {
                    @Override
                    public ContainerSampleTE.GuiSampleTE get (EntityPlayer player, World world, int x, int y, int z) {
                        return new ContainerSampleTE.GuiSampleTE(player, ((TestTE) world.getTileEntity(x, y, z)));
                    }
                }
        );
    }


}
