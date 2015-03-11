package com.github.hokutomc.lib.util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by user on 2014/10/11.
 * @deprecated pass by value
 */
@Deprecated
public final class HT_CreativeTabsUtil {
    private HT_CreativeTabsUtil () {
    }

    public static CreativeTabs create (String modid, String innerName, final Item iconItem) {
        return new CreativeTabs(modid + "." + innerName) {
            @Override
            public Item getTabIconItem () {
                return iconItem;
            }
        };
    }

    public static CreativeTabs create (String modid, String innerName, final Block iconBlock) {
        return create(modid, innerName, Item.getItemFromBlock(iconBlock));
    }
}
