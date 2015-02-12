package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.util.HT_Color;
import com.github.hokutomc.lib.util.HT_I_Colored;
import net.minecraft.item.ItemStack;

/**
 * Created by user on 2015/02/12.
 */
public class HT_ItemColored extends HT_Item<HT_ItemColored> implements HT_I_Colored {
    public HT_ItemColored (String modid, String innerName) {
        super(modid, innerName);
        this.multi(HT_Color.colorNames());
    }

    @Override
    public HT_Color getColor (ItemStack itemStack) {
        return HT_Color.get(itemStack.getItemDamage());
    }

    @Override
    public HT_ItemStackBuilder getItemStackFromColor (HT_Color color) {
        return new HT_ItemStackBuilder(this).size(1).damage(color.ordinal());
    }
}
