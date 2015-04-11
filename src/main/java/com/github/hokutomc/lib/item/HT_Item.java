package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.client.render.HT_RenderUtil;
import com.github.hokutomc.lib.util.HT_ArrayUtil;
import com.github.hokutomc.lib.util.HT_GeneralUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps Items.class.
 *
 * 2014/10/09.
 */
public class HT_Item<T extends HT_Item> extends Item {
    public static class Impl extends HT_Item<Impl> {
        public Impl (String modid, String innerName) {
            super(modid, innerName);
        }
    }

    private String m_shortName;
    protected List<HT_ItemStackBuilder.Raw> m_subItems;
    public final String m_modid;

    private ImmutableList<String> m_multiNames;

    public HT_Item (String modid, String innerName) {
        super();
        this.m_shortName = innerName;
        this.m_modid = modid;
        this.setInnerName(modid, innerName);
        m_subItems = new ArrayList<>();
        m_subItems.add(new HT_ItemStackBuilder.Raw(this));
    }

    @SuppressWarnings("unchecked")
    private static <A> A cast (Item self) {
        return (A) self;
    }

    public T multi(String... subNames) {
        this.m_multiNames = ImmutableList.copyOf(subNames);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        for (int i = 1; i < subNames.length; i++) {
            m_subItems.add(new HT_ItemStackBuilder.Raw(this).damage(i));
        }
        return cast(this);
    }

    public T register () {
        HT_Registries.registerItem(this);
        return cast(this);
    }

    public void registerMesher () {
        if (this.getHasSubtypes()) {
            for (int i = 0; i < m_multiNames.size(); i++) {
                HT_RenderUtil.registerOneItemMesher(this, i, this.m_modid + ":" + this.getShortName() + m_multiNames.get(i));
            }
        } else {
            HT_RenderUtil.registerOneItemMesher(this, 0, this.m_modid + ":" + this.getShortName());
        }
    }

    public List<String> getMultiNames () {
        return HT_GeneralUtil.orElse(m_multiNames, ImmutableList.<String>of());
    }

    public T setInnerName (String modid, String innerName) {
        this.m_shortName = innerName;
        return cast(this.setUnlocalizedName(modid + "." + innerName));
    }

    public String getShortName () {
        return m_shortName;
    }

    // wrapping
    public final T HT_setMaxStackSize (int maxStackSize) {
        return cast(this.setMaxStackSize(maxStackSize));
    }

    public T HT_setHasSubtypes (boolean hasSubtypes) {
        return cast(this.setHasSubtypes(hasSubtypes));
    }


    public T HT_setMaxDamage (int maxDurability) {
        return cast(this.setMaxDamage(maxDurability));
    }

    public T HT_setCreativeTab (CreativeTabs tab) {
        return cast(this.setCreativeTab(tab));
    }

    public T HT_setContainerItem (Item item) {
        return cast(this.setContainerItem(item));
    }

    public T HT_setFull3D () {
        return cast(this.setFull3D());
    }

    public T HT_setNoRepair () {
        return cast(this.setNoRepair());
    }

    public T HT_setPotionEffect (String effect) {
        return cast(this.setPotionEffect(effect));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        this.HT_addInformation(stack, playerIn, (List<String>) tooltip, advanced);
    }

    protected void HT_addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems (Item itemIn, CreativeTabs tab, List subItems) {
        this.HT_registerMulti(itemIn, tab, (List<ItemStack>) subItems);
    }

    protected void HT_registerMulti (Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (HT_ItemStackBuilder b : this.m_subItems) {
            subItems.add(b.build(1));
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack) {
        if (this.getHasSubtypes()) {
            return this.getUnlocalizedName() + "." + HT_ArrayUtil.getWithNoEx(m_multiNames, stack.getItemDamage());
        } else {
            return this.getUnlocalizedName();
        }

    }
}
