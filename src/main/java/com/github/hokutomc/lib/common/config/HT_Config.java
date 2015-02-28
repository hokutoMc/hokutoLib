package com.github.hokutomc.lib.common.config;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

/**
 * Advanced configuration.
 * 2014/11/10.
 */
public abstract class HT_Config extends Configuration {
    /**
     * Configuration file is created in suggested place.
     * @param event event.
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

    /**
     * Call this method to load the configuration and you don't have to call load() or save() method.
     * This method calls configure() where you wrote Configuration-loading methods.
     * @return this object
     */
    public final HT_Config apply () {
        try {
            this.load();
            this.configure();
        } catch (Exception e) {
            FMLLog.log(Level.WARN, e, "Error!");
        } finally {
            this.save();
        }
        return this;
    }

    /**
     * You must override this method and this method is called automatically from start() method.
     * You needn't call this method manually.
     */
    protected abstract void configure ();
}
