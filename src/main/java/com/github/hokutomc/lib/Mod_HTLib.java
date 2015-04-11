package com.github.hokutomc.lib;


import com.github.hokutomc.lib.common.config.HT_Config;
import com.github.hokutomc.lib.entity.HT_EntityUtil;
import com.github.hokutomc.lib.item.HT_ItemStackUtil;
import com.github.hokutomc.lib.item.tool.HT_ItemTool;
import com.github.hokutomc.lib.oredict.HT_OreDictPlus;
import com.github.hokutomc.lib.test.Debug;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        MinecraftForge.EVENT_BUS.register(HT_OreDictPlus.EVENT_HOOK);
    }

    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {

        if (isDebug) {
            Debug.init();
        }
    }

    @SubscribeEvent
    public void toolHitEntityHook (AttackEntityEvent event) {
        ItemStack currentItem = event.entityPlayer.getCurrentEquippedItem();
        HT_ItemTool item = HT_ItemStackUtil.getItemAs(currentItem, HT_ItemTool.class);
        if (item != null) {
            if (event.target instanceof EntityLivingBase) {
                item.hitEntity(currentItem, (EntityLivingBase) event.target, event.entityPlayer);
            }
            float damage = item.getAttackDamage(currentItem);
            float reaching = HT_EntityUtil.getReachingSpeed(event.entityPlayer, event.target);
            float bonus = item.getRangeBonus(currentItem, event.entityPlayer.getDistanceToEntity(event.target));
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
