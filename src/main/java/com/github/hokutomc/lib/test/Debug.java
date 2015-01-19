package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.Mod_HTLib;
import com.github.hokutomc.lib.block.*;
import com.github.hokutomc.lib.client.gui.HT_GuiAction;
import com.github.hokutomc.lib.client.gui.HT_GuiHandler;
import com.github.hokutomc.lib.entity.HT_ModEntityList;
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
    public static HT_Item itemMulti;
    public static HT_MultiBlock blockMulti;
    public static HT_Block blockTE;
    public static CreativeTabs tabHTLib = HT_CreativeTabsUtil.create("hokutoLib", "test", Items.iron_sword);
    public static HT_ItemDurable itemArmor;
    private static HT_BlockFalling blockFall;
    private static HT_ModEntityList entityList;

    public static final String MODID = "hokutolib";

    public static void preinit () {

        itemDurable = new HT_ItemTool(MODID, "tool") {

            @Override
            public int HT_getItemEnchantability () {
                return 5;
            }

            @Override
            public int getMaxDurability (ItemStack itemStack) {
                return 1000;
            }

            @Override
            protected int HT_getBonusWithEfficency (ItemStack itemStack) {
                return 0;
            }

            @Override
            public void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list) {
                list.add(this.create(0));
            }

            @Override
            public boolean HT_getIsRepairable (ItemStack itemStack, ItemStack materialItem) {
                return itemStack.getItem() == Items.iron_ingot;
            }

            @Override
            protected int getDamageBreakingBlock (ItemStack itemStack, Block block, int x, int y, int z) {
                return 10;
            }

            @Override
            protected int getDamageHittingEntity (ItemStack itemStack, EntityLivingBase target) {
                return 10;
            }

            @Override
            protected ToolMaterial getToolMaterial (ItemStack stack) {
                return ToolMaterial.IRON;
            }

            @Override
            protected String getToolType (ItemStack itemStack) {
                return "pickaxe";
            }

            @Override
            protected float getMaxAttackDamage (ItemStack itemStack) {
                return 4.0f;
            }
        }.HT_setTextureName("iron_sword").HT_setCreativeTab(tabHTLib).register();

        itemArmor = new HT_ItemArmor(MODID, "armor") {
            @Override
            protected ItemArmor.ArmorMaterial getArmorMaterial (ItemStack itemStack) {
                return ItemArmor.ArmorMaterial.CHAIN;
            }

            @Override
            public ArmorProperties getProperties (EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
                return new ArmorProperties(0, 0, 0);
            }

            @Override
            protected float getPartialDurability (ItemStack armor, Part part) {
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
            public int getMaxDurability (ItemStack itemStack) {
                return 1000;
            }

            @Override
            public void HT_registerMulti (Item item, CreativeTabs tab, List<ItemStack> list) {
                for (Part part : Part.values()) {
                    list.add(this.HT_create(part, 0));
                }
            }
        }.HT_setCreativeTab(tabHTLib).register();
        itemMulti = new HT_MultiItem(MODID, "multi", new String[]{"a", "b"}).HT_setCreativeTab(tabHTLib).register();

        blockMulti = new HT_MultiBlock(MODID, Material.wood, "multiblock", new String[]{"one", "two"}).HT_setCreativeTab(tabHTLib).register();
        blockFall = new HT_BlockFalling(MODID, Material.sand, "falling").HT_setCreativeTab(tabHTLib).register();
        blockTE = new TestContainerBlock().HT_setCreativeTab(tabHTLib).register();
        new HT_ContainerBlock(MODID, Material.iron, "dummy") {

            @Override
            public TileEntity createNewTileEntity (World p_149915_1_, int p_149915_2_) {
                return new DummyTE();
            }
        }.HT_setCreativeTab(tabHTLib).register();

        new HT_BlockDoubleSlab(MODID, Material.cloth, "slab", "a", "b").HT_setCreativeTab(tabHTLib).register();
        entityList = new HT_ModEntityList(Mod_HTLib.INSTANCE, MODID);
    }

    public static void init () {

        HT_Registries.registerCommonTileEntity(TestTE.class, "htTest");
        HT_Registries.registerCommonTileEntity(DummyTE.class, "ht_dummy");

        new HT_GuiHandler().register(Mod_HTLib.INSTANCE).addGui(0,
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

        entityList.register(TestMob.class, "httestentity", 0x808040, 0xf0f0d0);

        HT_Registries.registerEntityRenderer(TestMob.class, new RenderTestEntity(new ModelTestEntity(), 0.5f));
    }


}
