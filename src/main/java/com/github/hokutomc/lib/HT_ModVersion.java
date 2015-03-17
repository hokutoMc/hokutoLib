package com.github.hokutomc.lib;


import com.github.hokutomc.lib.util.HT_ModUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Properties;

/**
 * This class is easy way to read version from version.properties file.
 *
 * 2014/09/27.
 */
public class HT_ModVersion {
    private final Mod mod;
    private final FMLPreInitializationEvent event;
    private String major;
    private String minor;
    private String rev;
    private String build;
    private String mcversion;

    public HT_ModVersion (Object mod, FMLPreInitializationEvent event) {
        this.mod = HT_ModUtil.getMod(mod);
        this.event = event;
    }

    public HT_ModVersion apply () {
        Properties properties = this.event.getVersionProperties();
        if (properties != null) {
            String id = this.mod.modid();
            this.major = properties.getProperty(id + ".major");
            this.minor = properties.getProperty(id + ".minor");
            this.rev = properties.getProperty(id + ".revision");
            this.build = properties.getProperty(id + ".build");
            this.mcversion = properties.getProperty(id + ".mcversion");
        }
        event.getModMetadata().version = String.format("%s.%s.%s build %s", this.major, this.minor, this.rev, this.build);
        return this;
    }
}
