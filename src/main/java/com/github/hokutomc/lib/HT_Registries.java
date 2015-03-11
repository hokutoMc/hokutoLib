package com.github.hokutomc.lib;


import com.github.hokutomc.lib.block.HT_Block;
import com.github.hokutomc.lib.block.HT_MultiItemBlock;
import com.github.hokutomc.lib.client.render.HT_I_EntityRender;
import com.github.hokutomc.lib.client.render.HT_I_TileEntityRender;
import com.github.hokutomc.lib.item.HT_Item;
import com.github.hokutomc.lib.item.recipe.HT_FurnaceRecipeBuilder;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by user on 2014/09/23.
 */
public final class HT_Registries {
    private HT_Registries () {
    }


    public static <T extends HT_Block> T registerBlock (T block) {
        GameRegistry.registerBlock(block, block.HT_getShortName());
        return block;
    }

    public static <T extends HT_Block> T registerMultiBlock (T block) {
        GameRegistry.registerBlock(block, HT_MultiItemBlock.class, block.HT_getShortName());
        return block;
    }

    public static <T extends HT_Item> T registerItem (T item) {
        GameRegistry.registerItem(item, item.getShortName());
        return item;
    }

    public static void registerCommonTileEntity (Class<? extends TileEntity> tileClass, String id) {
        GameRegistry.registerTileEntity(tileClass, id);
    }

    public static void registerGUIHandler (Object mod, IGuiHandler guiHandler) {
        NetworkRegistry.INSTANCE.registerGuiHandler(mod, guiHandler);
    }

    public static void registerEntity (Class<? extends Entity> entityClass, String name, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(entityClass, name, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    public static void registerEntity (Class<? extends Entity> entityClass, String name, int id, Object mod) {
        registerEntity(entityClass, name, id, mod, 250, 1, false);
    }


    public static void registerWorldGenerator (IWorldGenerator worldGenerator, int weight) {
        GameRegistry.registerWorldGenerator(worldGenerator, weight);
    }

    public static void registerForgeEventBus (Object observer) {
        MinecraftForge.EVENT_BUS.register(observer);
    }

    public static void registerDispencerBehavior (Item item, IBehaviorDispenseItem behavior) {
        BlockDispenser.dispenseBehaviorRegistry.putObject(item, behavior);
    }

    public static HT_FurnaceRecipeBuilder addSmelting () {
        return new HT_FurnaceRecipeBuilder();
    }

    public static void registerBlock (HT_Block block, Class<? extends ItemBlock> itemBlockClass) {
        GameRegistry.registerBlock(block, itemBlockClass, block.HT_getShortName());
    }

    public static <E extends Entity, R extends Render & HT_I_EntityRender<E>> void registerEntityRenderer (Class<E> entityClass, R render) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            RenderingRegistry.registerEntityRenderingHandler(entityClass, render);
        }
    }

    public static <T extends TileEntity, R extends TileEntitySpecialRenderer & HT_I_TileEntityRender<T>> void bindTESR
            (Class<T> teClass, R render) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ClientRegistry.bindTileEntitySpecialRenderer(teClass, render);
        }
    }
}
