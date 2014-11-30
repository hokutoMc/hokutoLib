package com.github.hokutomc.lib.common.config;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

/**
 * Created by user on 2014/11/10.
 */
public abstract class HT_Config extends Configuration {
    /**
     * ForgeModLoaderの標準の場所に、modのidを用いた名前のファイルを参照する設定オブジェクトを生成します。
     * @param event 初期化前イベントのオブジェクト
     */
    public HT_Config (FMLPreInitializationEvent event) {
        super(event.getSuggestedConfigurationFile());
    }

    public HT_Config (File file) {
        super(file);
    }

    public HT_Config (File file, String configVersion, boolean caseSensitiveCustomCategories) {
        super(file, configVersion, caseSensitiveCustomCategories);
    }

    public HT_Config (File file, String configVersion) {
        super(file, configVersion);
    }

    public HT_Config (File file, boolean caseSensitiveCustomCategories) {
        super(file, caseSensitiveCustomCategories);
    }

    public HT_Config HT_apply () {
        try {
            this.load();
            this.HT_configure();
        } catch (Exception e) {
            FMLLog.log(Level.WARN, e, "Error!");
        } finally {
            this.save();
        }
        return this;
    }

    public abstract void HT_configure ();
}
