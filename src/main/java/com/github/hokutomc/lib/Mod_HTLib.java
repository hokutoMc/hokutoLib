package com.github.hokutomc.lib;


import com.github.hokutomc.lib.common.config.HT_Config;
import com.github.hokutomc.lib.item.HT_ItemTool;
import com.github.hokutomc.lib.entity.HT_EntityUtil;
import com.github.hokutomc.lib.test.Debug;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

/**
 * Created by user on 2014/09/26.
 */
@Mod(modid = "hokutomc.lib")
public class Mod_HTLib {

    public static boolean isDebug = true;

    public static HT_ModVersion version;

    @Mod.Instance("hokutomc.lib")
    public static Mod_HTLib INSTANCE;

    private HT_Config config;


    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        version = new HT_ModVersion(this, event).apply();
        config = new HT_Config(event) {
            @Override
            public void configure () {
                isDebug = 1 == this.getInt("isDebugMode", "all", 0, 0, 1, "0 : not in debug mode, 1 : debug mods");
            }
        };

        config.apply();

        if (isDebug) {
            Debug.preinit();
        }


        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {

        if (isDebug) {
            Debug.init();
        }


    }

    @SubscribeEvent
    public void toolHitEntityHook (AttackEntityEvent event) {
        ItemStack currentItem = event.entityPlayer.inventory.getCurrentItem();
        if (currentItem!= null && currentItem.getItem()!= null && currentItem.getItem() instanceof HT_ItemTool) {
            ItemStack tool = event.entityPlayer.inventory.getCurrentItem();
            HT_ItemTool itemObj = (HT_ItemTool) tool.getItem();
            if (event.target instanceof EntityLivingBase) {
                itemObj.HT_hitEntity(tool, (EntityLivingBase) event.target, event.entityPlayer);
            }
            float damage = itemObj.HT_getAttackDamage(tool);
            float reaching = HT_EntityUtil.getReachingSpeed(event.entityPlayer, event.target);
            float bonus = itemObj.getRangeBonus(tool, event.entityPlayer.getDistanceToEntity(event.target));
            damage *= bonus;
            damage *= Math.pow(2.0, reaching);
            damage *= Math.pow(1.4, event.entityPlayer.posY - event.target.posY);
            if (damage > 0.5f) {
                event.target.attackEntityFrom(DamageSource.causePlayerDamage(event.entityPlayer), damage);
                System.out.println("damage:" + damage);
            }
        }
    }
}
