package com.github.hokutomc.lib.item.tool;

import com.github.hokutomc.lib.util.HT_I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

/**
 * Created by user on 2015/02/15.
 */
public class HT_ToolType {

    public final String name;

    public HT_ToolType (String name) {
        this.name = name;
    }

    public String getToolName (String material) {
        return name + material;
    }

    /**
     * get localized name from unlocalized name "tool.type.().name" with material name as parameter
     * @param material name to be formatted.
     * @return
     */
    public String getLocalizedName (String material) {
        return HT_I18nUtil.localize("tool.type." + name + ".name", material);
    }

    @Override
    public String toString () {
        return name;
    }

    public float getCapability (ItemStack itemStack, IBlockAccess world, int x, int y, int z) {
        return this.getCapability(itemStack, world.getBlock(x, y, z));
    }

    public float getCapability (ItemStack itemStack, Block block) {
        return 0;
    }
}
