package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.util.HT_ArrayUtil;
import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Created by user on 2014/10/11.
 */
public class HT_MultiItem extends HT_Item<HT_MultiItem> {
    private final ImmutableSet<String> m_multiNames;

    @SideOnly(Side.CLIENT)
    private IIcon[] m_multiIcons;

    public HT_MultiItem (String modid, String innerName, String[] subNameList) {
        super(modid, innerName);
        this.m_multiNames = ImmutableSet.copyOf(subNameList);
        this.HT_setMaxDamage(0);
        this.HT_setHasSubtypes(true);
        this.m_multiIcons = new IIcon[subNameList.length];
        for (int i = 1; i < subNameList.length; i++) {
            m_subItems.add(new HT_ItemStackBuilder(this, i));
        }
    }

    @Override
    public String[] getMultiNames () {
        return HT_ArrayUtil.toArray(ImmutableSet.copyOf(m_multiNames), String.class);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon HT_getIconFromDamage (int damage) {
        return this.m_multiIcons[damage];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void HT_registerIcons (IIconRegister iconRegister) {
        super.HT_registerIcons(iconRegister);
        for (int i = 0; i < m_multiNames.size(); i++) {
            this.m_multiIcons[i] = iconRegister.registerIcon(this.HT_getIconString() + "_" + m_multiNames.toArray()[i]);
        }
    }

    @Override
    public String HT_getUnlocalizedName (ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + m_multiNames.toArray()[itemStack.getItemDamage() % m_multiNames.size()];
    }
}
