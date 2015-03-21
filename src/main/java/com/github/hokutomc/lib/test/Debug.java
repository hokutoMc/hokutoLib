package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.Mod_HTLib;
import com.github.hokutomc.lib.block.HT_Block;
import com.github.hokutomc.lib.block.HT_BlockDoubleSlab;
import com.github.hokutomc.lib.block.HT_BlockFalling;
import com.github.hokutomc.lib.client.gui.HT_GuiAction;
import com.github.hokutomc.lib.client.gui.HT_GuiHandler;
import com.github.hokutomc.lib.entity.HT_ModEntityList;
import com.github.hokutomc.lib.item.HT_Item;
import com.github.hokutomc.lib.item.HT_ItemArmor;
import com.github.hokutomc.lib.item.HT_ItemDurable;
import com.github.hokutomc.lib.item.recipe.HT_CraftingRecipeBuilder;
import com.github.hokutomc.lib.item.recipe.HT_FurnaceRecipeBuilder;
import com.github.hokutomc.lib.item.tool.HT_ItemTool;
import com.github.hokutomc.lib.world.gen.HT_OreGenGen;
import com.github.hokutomc.lib.world.gen.HT_OreGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

/**
 * Created by user on 2014/11/03.
 */
public class Debug {


    public static HT_ItemDurable itemDurable;
    public static HT_Item itemMulti;
    public static HT_Block blockMulti;
    public static HT_Block blockTE;
    public static CreativeTabs tabHTLib = new CreativeTabs("hokutoLib" + ".test") {
        @Override
        public Item getTabIconItem () {
            return Items.iron_sword;
        }
    };
    public static HT_ItemDurable itemArmor;
    private static HT_BlockFalling.Raw blockFall;
    private static HT_ModEntityList entityList;

    public static final String MODID = "hokutolib";

    public static void preinit () {

        itemDurable = new HT_ItemTool.Raw(MODID, "tool") {

            @Override
            public int getItemEnchantability () {
                return 5;
            }

            @Override
            public int getMaxDurability (ItemStack itemStack) {
                return 1000;
            }

            @Override
            protected int getBonusWithEfficency (ItemStack itemStack) {
                return 0;
            }

            @Override
            public boolean getIsRepairable (ItemStack itemStack, ItemStack materialItem) {
                return itemStack.getItem() == Items.iron_ingot;
            }


            @Override
            protected int getDamageBreakingBlock (ItemStack itemStack, Block block, BlockPos pos) {
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
                return "hoe";
            }

            @Override
            protected float getMaxAttackDamage (ItemStack itemStack) {
                return 4.0f;
            }
        }.HT_setCreativeTab(tabHTLib).register();

        itemArmor = new HT_ItemArmor.Raw(MODID, "armor") {
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


        }.HT_setCreativeTab(tabHTLib).register();
        itemMulti = new HT_Item.Impl(MODID, "multi").multi("a", "b").HT_setCreativeTab(tabHTLib).register();

        blockMulti = new HT_Block(MODID, Material.wood, "multiblock").multi("a", "b").HT_setCreativeTab(tabHTLib).register();
        blockFall = new HT_BlockFalling.Raw(MODID, Material.sand, "falling").HT_setCreativeTab(tabHTLib).register();
        blockTE = new TestContainerBlock().HT_setCreativeTab(tabHTLib).register();
//        new HT_ContainerBlock(MODID, Material.iron, "dummy") {
//
//            @Override
//            public TileEntity createNewTileEntity (World p_149915_1_, int p_149915_2_) {
//                return new DummyTE();
//            }
//        }.HT_setCreativeTab(tabHTLib).register();

        new HT_BlockDoubleSlab(MODID, Material.cloth, "slab", "a", "b").HT_setCreativeTab(tabHTLib).register();

        final HT_OreGenGen gen = new HT_OreGenGen(128, 2, 60) {
            @Override
            protected void gen (World world, Random random, int genX, int genY, int genZ) {
                new WorldGenMinable(Blocks.emerald_block.getDefaultState(), 20).generate(world, random, new BlockPos(genX, genY, genZ));
            }
        };

        new HT_OreGenerator() {
            @Override
            protected void generateSurface (World world, Random random, int x, int z) {
                gen.generateRandomPos(world, random, x, z);
            }
        }.register(10);

        entityList = new HT_ModEntityList(Mod_HTLib.INSTANCE, MODID);
    }

    public static void init () {

        HT_Registries.registerCommonTileEntity(TestTE.class, "htTest");
//        HT_Registries.registerCommonTileEntity(DummyTE.class, "ht_dummy");

        new HT_GuiHandler().register(Mod_HTLib.INSTANCE).addGui(0,
                new HT_GuiAction<ContainerSampleTE>() {
                    @Override
                    public ContainerSampleTE get (EntityPlayer player, World world, int x, int y, int z) {
                        return new ContainerSampleTE(player, ((TestTE) world.getTileEntity(new BlockPos(x, y, z))));
                    }
                },
                new HT_GuiAction<ContainerSampleTE.GuiSampleTE>() {
                    @Override
                    public ContainerSampleTE.GuiSampleTE get (EntityPlayer player, World world, int x, int y, int z) {
                        return new ContainerSampleTE.GuiSampleTE(player, ((TestTE) world.getTileEntity(new BlockPos(x, y, z))));
                    }
                }
        );

        entityList.register(TestMob.class, "httestentity", 0x808040, 0xf0f0d0);

        HT_Registries.registerEntityRenderer(TestMob.class, new RenderTestEntity(Minecraft.getMinecraft().getRenderManager(), new ModelTestEntity(), 0.5f));

        HT_CraftingRecipeBuilder.create()
                .param('X', Items.string).endItem()
                .grid("XXX", "XXX", "XXX")
                .to(Blocks.wool).endItem()
        .paramOre('X', "ingotIron")
                .grid("XXX")
                .to(Items.diamond).size(2).endItem()
        .shapeless()
                .from(Blocks.dirt).size(2).endItem()
                .andOre("ingotIron")
                .to(Blocks.iron_block).size(2).endItem();

        new HT_FurnaceRecipeBuilder()
                .from(Blocks.dirt).endItem()
                .withXp(100.0)
                .to(Blocks.obsidian).endItem()
                .from(Blocks.snow).endItem()
                .withXp(1.0)
                .to(Items.water_bucket).endItem();

    }


}
