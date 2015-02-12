package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.util.HT_ArrayUtil;
import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps Items.class.
 *
 * 2014/10/09.
 */
public class HT_Item<T extends HT_Item> extends Item {
    private String m_shortName;
    protected List<HT_ItemStackBuilder> m_subItems;

    private ImmutableSet<String> m_multiNames;

    @SideOnly(Side.CLIENT)
    private IIcon[] m_multiIcons;

    public HT_Item (String modid, String innerName) {
        super();
        this.m_shortName = innerName;
        this.HT_setInnerName(modid, innerName);
        this.HT_setTextureName(modid, innerName);
        m_subItems = new ArrayList<>();
        m_subItems.add(new HT_ItemStackBuilder(this, 0));
    }

    @SuppressWarnings("unchecked")
    public T multi(String... subNames) {
        this.m_multiNames = ImmutableSet.copyOf(subNames);
        this.HT_setMaxDamage(0);
        this.HT_setHasSubtypes(true);
        this.m_multiIcons = new IIcon[subNames.length];
        for (int i = 1; i < subNames.length; i++) {
            m_subItems.add(new HT_ItemStackBuilder(this, i));
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T register () {
        return (T) HT_Registries.registerItem(this);
    }

    public String[] getMultiNames () {
        return m_multiNames != null ? HT_ArrayUtil.toArray(m_multiNames) : new String[0];
    }

    // wrapping

    private void HT_setTextureName (String modid, String innerName) {
        this.HT_setTextureName(modid + ":" + innerName);
    }

    public void HT_setInnerName (String modid, String innerName) {
        this.HT_setUnlocalizedName(modid + "." + innerName);
    }

    @Override
    public final Item setMaxStackSize (int p_77625_1_) {
        return this.HT_setMaxStackSize(p_77625_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setMaxStackSize (int maxSize) {
        return (T) super.setMaxStackSize(maxSize);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final int getSpriteNumber () {
        return this.HT_getSpriteNumber();
    }

    @SideOnly(Side.CLIENT)
    public int HT_getSpriteNumber () {
        return super.getSpriteNumber();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIconFromDamage (int p_77617_1_) {
        return this.HT_getIconFromDamage(p_77617_1_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon HT_getIconFromDamage (int damage) {
        return this.HT_getHasSubtypes() ? HT_ArrayUtil.getWithNoEx(m_multiIcons, damage) : super.getIconFromDamage(damage);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIconIndex (ItemStack p_77650_1_) {
        return this.HT_getIconIndex(p_77650_1_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon HT_getIconIndex (ItemStack itemStack) {
        return this.getIconFromDamage(itemStack.getItemDamageForDisplay());
    }

    @Override
    public final boolean onItemUse (ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        return this.HT_onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
    }

    public boolean HT_onItemUse (ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public final float func_150893_a (ItemStack p_150893_1_, Block p_150893_2_) {
        return this.HT_func_150893_a(p_150893_1_, p_150893_2_);
    }

    public float HT_func_150893_a (ItemStack itemStack, Block block) {
        return super.func_150893_a(itemStack, block);
    }

    @Override
    public final ItemStack onItemRightClick (ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        return this.HT_onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    public ItemStack HT_onItemRightClick (ItemStack itemStack, World world, EntityPlayer player) {
        return super.onItemRightClick(itemStack, world, player);
    }

    @Override
    public final ItemStack onEaten (ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
        return this.HT_onEaten(p_77654_1_, p_77654_2_, p_77654_3_);
    }

    public ItemStack HT_onEaten (ItemStack itemStack, World world, EntityPlayer player) {
        return super.onEaten(itemStack, world, player);
    }

    @Override
    public final int getMetadata (int p_77647_1_) {
        return this.HT_getMetadata(p_77647_1_);
    }

    public int HT_getMetadata (int meta) {
        return super.getMetadata(meta);
    }

    @Override
    public final boolean getHasSubtypes () {
        return this.HT_getHasSubtypes();
    }

    public boolean HT_getHasSubtypes () {
        return super.getHasSubtypes();
    }

    @Override
    public final Item setHasSubtypes (boolean p_77627_1_) {
        return this.HT_setHasSubtypes(p_77627_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setHasSubtypes (boolean isMultiItem) {
        return (T) super.setHasSubtypes(isMultiItem);
    }

    @Override
    public final int getMaxDamage () {
        return this.HT_getMaxDamage();
    }

    public int HT_getMaxDamage () {
        return super.getMaxDamage();
    }

    @Override
    public final Item setMaxDamage (int p_77656_1_) {
        return this.HT_setMaxDamage(p_77656_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setMaxDamage (int maxDamage) {
        return (T) super.setMaxDamage(maxDamage);
    }

    @Override
    public final boolean isDamageable () {
        return this.HT_isDamageable();
    }

    public boolean HT_isDamageable () {
        return super.isDamageable();
    }

    @Override
    public final boolean hitEntity (ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
        return this.HT_hitEntity(p_77644_1_, p_77644_2_, p_77644_3_);
    }

    public boolean HT_hitEntity (ItemStack itemStack, EntityLivingBase target, EntityLivingBase holder) {
        return super.hitEntity(itemStack, target, holder);
    }

    @Override
    public final boolean onBlockDestroyed (ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        return this.HT_onBlockDestroyed(p_150894_1_, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
    }

    public boolean HT_onBlockDestroyed (ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase holder) {
        return super.onBlockDestroyed(itemStack, world, block, x, y, z, holder);
    }

    @Override
    public final boolean func_150897_b (Block p_150897_1_) {
        return this.HT_func_150897_b(p_150897_1_);
    }

    public boolean HT_func_150897_b (Block block) {
        return super.func_150897_b(block);
    }

    @Override
    public final boolean itemInteractionForEntity (ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_) {
        return this.HT_itemInteractionForEntity(p_111207_1_, p_111207_2_, p_111207_3_);
    }

    public boolean HT_itemInteractionForEntity (ItemStack itemStack, EntityPlayer player, EntityLivingBase livingBase) {
        return super.itemInteractionForEntity(itemStack, player, livingBase);
    }

    @Override
    public final Item setFull3D () {
        return this.HT_setFull3D();
    }

    @SuppressWarnings("unchecked")
    public T HT_setFull3D () {
        return (T) super.setFull3D();
    }

    @Override
    public final boolean isFull3D () {
        return this.HT_isFull3D();
    }

    public boolean HT_isFull3D () {
        return super.isFull3D();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final boolean shouldRotateAroundWhenRendering () {
        return this.HT_shouldRotateAroundWhenRendering();
    }

    @SideOnly(Side.CLIENT)
    public boolean HT_shouldRotateAroundWhenRendering () {
        return super.shouldRotateAroundWhenRendering();
    }

    @Override
    public final Item setUnlocalizedName (String p_77655_1_) {
        return this.HT_setUnlocalizedName(p_77655_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setUnlocalizedName (String name) {
        return (T) super.setUnlocalizedName(name);
    }

    @Override
    public final String getUnlocalizedNameInefficiently (ItemStack p_77657_1_) {
        return this.HT_getUnlocalizedNameInefficiently(p_77657_1_);
    }

    public String HT_getUnlocalizedNameInefficiently (ItemStack itemStack) {
        return super.getUnlocalizedNameInefficiently(itemStack);
    }

    @Override
    public final String getUnlocalizedName () {
        return this.HT_getUnlocalizedName();
    }

    public String HT_getUnlocalizedName () {
        return super.getUnlocalizedName();
    }

    @Override
    public final String getUnlocalizedName (ItemStack p_77667_1_) {
        return this.HT_getUnlocalizedName(p_77667_1_);
    }

    public String HT_getUnlocalizedName (ItemStack itemStack) {
        return this.HT_getHasSubtypes()
                ? super.getUnlocalizedName() + "." + HT_ArrayUtil.getWithNoEx(m_multiNames, itemStack.getItemDamage())
                : super.getUnlocalizedName(itemStack);
    }

    @Override
    public final Item setContainerItem (Item p_77642_1_) {
        return this.HT_setContainerItem(p_77642_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setContainerItem (Item item) {
        return (T) super.setContainerItem(item);
    }

    @Override
    public final boolean doesContainerItemLeaveCraftingGrid (ItemStack p_77630_1_) {
        return this.HT_doesContainerItemLeaveCraftingGrid(p_77630_1_);
    }

    public boolean HT_doesContainerItemLeaveCraftingGrid (ItemStack itemStack) {
        return super.doesContainerItemLeaveCraftingGrid(itemStack);
    }

    @Override
    public final boolean getShareTag () {
        return this.HT_getShareTag();
    }

    public boolean HT_getShareTag () {
        return super.getShareTag();
    }

    @Override
    public final Item getContainerItem () {
        return this.HT_getContainerItem();
    }

    @SuppressWarnings("unchecked")
    public T HT_getContainerItem () {
        return (T) super.getContainerItem();
    }

    @Override
    public final boolean hasContainerItem () {
        return super.hasContainerItem();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public final int getColorFromItemStack (ItemStack p_82790_1_, int p_82790_2_) {
        return this.HT_getColorFromItemStack(p_82790_1_, p_82790_2_);
    }

    @SideOnly(Side.CLIENT)
    public int HT_getColorFromItemStack (ItemStack itemStack, int path) {
        return super.getColorFromItemStack(itemStack, path);
    }

    @Override
    public final void onUpdate (ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        this.HT_onUpdate(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

    public void HT_onUpdate (ItemStack itemStack, World world, Entity entity, int i, boolean b) {
        super.onUpdate(itemStack, world, entity, i, b);
    }

    @Override
    public final void onCreated (ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
        this.HT_onCreated(p_77622_1_, p_77622_2_, p_77622_3_);
    }

    public void HT_onCreated (ItemStack itemStack, World world, EntityPlayer player) {
        super.onCreated(itemStack, world, player);
    }

    @Override
    public final boolean isMap () {
        return this.HT_isMap();
    }

    public boolean HT_isMap () {
        return super.isMap();
    }

    @Override
    public final EnumAction getItemUseAction (ItemStack p_77661_1_) {
        return this.HT_getItemUseAction(p_77661_1_);
    }

    public EnumAction HT_getItemUseAction (ItemStack itemStack) {
        return super.getItemUseAction(itemStack);
    }

    @Override
    public final int getMaxItemUseDuration (ItemStack p_77626_1_) {
        return this.HT_getMaxItemUseDuration(p_77626_1_);
    }

    public int HT_getMaxItemUseDuration (ItemStack itemStack) {
        return super.getMaxItemUseDuration(itemStack);
    }

    @Override
    public final void onPlayerStoppedUsing (ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {
        this.HT_onPlayerStoppedUsing(p_77615_1_, p_77615_2_, p_77615_3_, p_77615_4_);
    }

    public void HT_onPlayerStoppedUsing (ItemStack itemStack, World world, EntityPlayer player, int i) {
        super.onPlayerStoppedUsing(itemStack, world, player, i);
    }

    @Override
    public final Item setPotionEffect (String p_77631_1_) {
        return this.HT_setPotionEffect(p_77631_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setPotionEffect (String effect) {
        return (T) super.setPotionEffect(effect);
    }

    @Override
    public final String getPotionEffect (ItemStack p_150896_1_) {
        return this.HT_getPotionEffect(p_150896_1_);
    }

    public String HT_getPotionEffect (ItemStack itemStack) {
        return super.getPotionEffect(itemStack);
    }

    @Override
    public final boolean isPotionIngredient (ItemStack p_150892_1_) {
        return this.HT_isPotionIngredient(p_150892_1_);
    }

    public boolean HT_isPotionIngredient (ItemStack itemStack) {
        return super.isPotionIngredient(itemStack);
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public final void addInformation (ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
        this.HT_addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @SideOnly(Side.CLIENT)
    public void HT_addInformation (ItemStack itemStack, EntityPlayer player, final List<String> list, boolean b) {
        super.addInformation(itemStack, player, list, b);
    }

    @Override
    public final String getItemStackDisplayName (ItemStack p_77653_1_) {
        return this.HT_getItemStackDisplayName(p_77653_1_);
    }

    public String HT_getItemStackDisplayName (ItemStack itemStack) {
        return super.getItemStackDisplayName(itemStack);
    }

    @Override
    public final EnumRarity getRarity (ItemStack p_77613_1_) {
        return this.HT_getRarity(p_77613_1_);
    }

    public EnumRarity HT_getRarity (ItemStack itemStack) {
        return super.getRarity(itemStack);
    }

    @Override
    public final boolean isItemTool (ItemStack p_77616_1_) {
        return this.HT_isItemTool(p_77616_1_);
    }

    public boolean HT_isItemTool (ItemStack itemStack) {
        return super.isItemTool(itemStack);
    }

    @Override
    protected MovingObjectPosition getMovingObjectPositionFromPlayer (World p_77621_1_, EntityPlayer p_77621_2_, boolean p_77621_3_) {
        return this.HT_getMovingObjectPositionFromPlayer(p_77621_1_, p_77621_2_, p_77621_3_);
    }

    public MovingObjectPosition HT_getMovingObjectPositionFromPlayer (World world, EntityPlayer player, boolean b) {
        return super.getMovingObjectPositionFromPlayer(world, player, b);
    }

    @Override
    public final int getItemEnchantability () {
        return this.HT_getItemEnchantability();
    }

    public int HT_getItemEnchantability () {
        return super.getItemEnchantability();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final boolean requiresMultipleRenderPasses () {
        return this.HT_requiresMultipleRenderPasses();
    }

    @SideOnly(Side.CLIENT)
    public boolean HT_requiresMultipleRenderPasses () {
        return super.requiresMultipleRenderPasses();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIconFromDamageForRenderPass (int p_77618_1_, int p_77618_2_) {
        return this.HT_getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon HT_getIconFromDamageForRenderPass (int meta, int pass) {
        return super.getIconFromDamageForRenderPass(meta, pass);
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubItems (Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        this.HT_registerMulti(p_150895_1_, p_150895_2_, p_150895_3_);
    }

    @SideOnly(Side.CLIENT)
    public void HT_registerMulti (Item item, CreativeTabs tab, final List<ItemStack> list) {
        for (HT_ItemStackBuilder e : m_subItems) {
            list.add(e.build(1));
        }
    }

    @Override
    public final Item setCreativeTab (CreativeTabs p_77637_1_) {
        return this.HT_setCreativeTab(p_77637_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setCreativeTab (CreativeTabs tab) {
        return (T) super.setCreativeTab(tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final CreativeTabs getCreativeTab () {
        return this.HT_getCreativeTab();
    }

    @SideOnly(Side.CLIENT)
    public CreativeTabs HT_getCreativeTab () {
        return super.getCreativeTab();
    }

    @Override
    public final boolean canItemEditBlocks () {
        return this.HT_canItemEditBlocks();
    }

    public boolean HT_canItemEditBlocks () {
        return super.canItemEditBlocks();
    }

    @Override
    public final boolean getIsRepairable (ItemStack p_82789_1_, ItemStack p_82789_2_) {
        return this.HT_getIsRepairable(p_82789_1_, p_82789_2_);
    }

    public boolean HT_getIsRepairable (ItemStack itemStack, ItemStack materialItem) {
        return super.getIsRepairable(itemStack, materialItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void registerIcons (IIconRegister p_94581_1_) {
        this.HT_registerIcons(p_94581_1_);
    }

    @SideOnly(Side.CLIENT)
    public void HT_registerIcons (IIconRegister iconRegister) {
        if (this.HT_getHasSubtypes()) {
            for (int i = 0; i < m_multiNames.size(); i++) {
                this.m_multiIcons[i] = iconRegister.registerIcon(this.HT_getIconString() + "_" + m_multiNames.toArray()[i]);
            }
        } else {
            super.registerIcons(iconRegister);
        }
    }

    @Override
    public final Item setTextureName (String p_111206_1_) {
        return this.HT_setTextureName(p_111206_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setTextureName (String name) {
        return (T) super.setTextureName(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected String getIconString () {
        return this.HT_getIconString();
    }

    @SideOnly(Side.CLIENT)
    protected String HT_getIconString () {
        return super.getIconString();
    }

    public String getShortName () {
        return m_shortName;
    }
}
