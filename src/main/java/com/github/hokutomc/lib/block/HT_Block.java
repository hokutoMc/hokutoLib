package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.item.HT_ItemStackBuilder;
import com.github.hokutomc.lib.util.HT_ArrayUtil;
import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class wraps Block.class.
 *
 * 2014/09/23.
 */
public class HT_Block<T extends HT_Block> extends Block {
    private boolean m_hasSubTypes;
    private String m_shortName;
    private String m_innerName;
    protected boolean m_fallInstantly;
    public String m_modid;
    protected List<HT_ItemStackBuilder> m_subItems = new ArrayList<>();

    private ImmutableSet<String> m_multiNames;

    @SideOnly(Side.CLIENT)
    private IIcon[] m_multiIcons;

    public HT_Block (String modid, Material material, String innerName) {
        super(material);
        this.m_hasSubTypes = false;
        this.m_modid = modid;
        this.m_shortName = innerName;
        this.HT_setInnerName(modid, innerName);
        this.HT_setTextureName(modid, innerName);
        m_subItems.add(new HT_ItemStackBuilder(this, 0));
    }

    @SuppressWarnings("unchecked")
    public T multi(String... subNames) {
        this.m_multiNames = ImmutableSet.copyOf(subNames);
        this.m_multiIcons = new IIcon[subNames.length];
        for (int i = 1; i < m_multiIcons.length; i++) {
            m_subItems.add(new HT_ItemStackBuilder(this, i));
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T register () {
        return this.m_hasSubTypes ? (T) HT_Registries.registerMultiBlock(this) : (T) HT_Registries.registerBlock(this);
    }

    public String[] getMultiNames () {
        return this.m_hasSubTypes ? HT_ArrayUtil.toArray(this.m_multiNames) : new String[0];
    }

    public boolean getHasSubTypes () {
        return this.m_hasSubTypes;
    }

    @SuppressWarnings("unchecked")
    public T setHasSubTypes (boolean b) {
        this.m_hasSubTypes = b;
        return (T) this;
    }

    public boolean isFrontSide (int side, int meta) {
        return side == meta;
    }


    public void setMetadataByDirection (World world, int x, int y, int z) {
        if (!world.isRemote) {
            Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j()) {
                b0 = 3;
            }

            if (block1.func_149730_j() && !block.func_149730_j()) {
                b0 = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j()) {
                b0 = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j()) {
                b0 = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, b0, 2);
        }
    }

    public void fall (World world, int x, int y, int z) {
        if (canContinueFalling(world, x, y - 1, z) && y >= 0) {
            byte b0 = 32;

            if (!m_fallInstantly && world.checkChunksExist(x - b0, y - b0, z - b0, x + b0, y + b0, z + b0)) {
                if (!world.isRemote) {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this, world.getBlockMetadata(x, y, z));
                    this.fallAnvil(entityfallingblock);
                    world.spawnEntityInWorld(entityfallingblock);
                }
            } else {
                world.setBlockToAir(x, y, z);

                while (canContinueFalling(world, x, y - 1, z) && y > 0) {
                    --y;
                }

                if (y > 0) {
                    world.setBlock(x, y, z, this);
                }
            }
        }
    }

    private boolean canContinueFalling (World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        if (block.isAir(world, x, y, z)) {
            return true;
        } else if (block == Blocks.fire) {
            return true;
        } else {
            Material material = block.getMaterial();
            return material == Material.water || material == Material.lava;
        }
    }

    private void fallAnvil (EntityFallingBlock entityfallingblock) {
    }

    @Override
    public String toString () {
        return this.HT_getShortName();
    }

    // Wrapping ===

    @Override
    public final Block setBlockTextureName (String p_149658_1_) {
        return this.HT_setTextureName("", p_149658_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setTextureName (String modid, String textureName) {
        String str = (modid == null || modid.equals("") ? "" : modid + ":") + textureName;
        return (T) super.setBlockTextureName(str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final String getTextureName () {
        return this.HT_getTextureName();
    }

    @SideOnly(Side.CLIENT)
    public String HT_getTextureName () {
        return super.getTextureName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon func_149735_b (int p_149735_1_, int p_149735_2_) {
        return this.HT_func_149735_b(p_149735_1_, p_149735_2_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon HT_func_149735_b (int side, int meta) {
        return super.func_149735_b(side, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void registerBlockIcons (IIconRegister p_149651_1_) {
        this.HT_registerBlockIcons(p_149651_1_);
    }

    @SideOnly(Side.CLIENT)
    public void HT_registerBlockIcons (IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final String getItemIconName () {
        return this.HT_getItemIconName();
    }

    @SideOnly(Side.CLIENT)
    public String HT_getItemIconName () {
        return super.getItemIconName();
    }


    @Override
    public final Block setBlockName (String p_149663_1_) {
        return super.setBlockName(p_149663_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setInnerName (String mod, String name) {
        this.m_shortName = name;
        this.m_innerName = mod + "." + name;
        return (T) this;
    }

    @Override
    public final String getLocalizedName () {
        return this.HT_getLocalizedName();
    }

    public String HT_getLocalizedName () {
        return super.getLocalizedName();
    }

    @Override
    public final String getUnlocalizedName () {
        return this.HT_getUnlocalizedName();
    }

    public String HT_getUnlocalizedName () {
        return "tile." + this.m_innerName;
    }

    public String HT_getShortName () {
        return this.m_shortName;
    }


    @Override
    public final boolean func_149730_j () {
        return this.HT_isOpaque();
    }

    public boolean HT_isOpaque () {
        return super.func_149730_j();
    }

    @Override
    public final int getLightOpacity () {
        return this.HT_getLightOpacity();
    }

    public int HT_getLightOpacity () {
        return super.getLightOpacity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final boolean getCanBlockGrass () {
        return this.HT_getCanBlockGrass();
    }

    @SideOnly(Side.CLIENT)
    public boolean HT_getCanBlockGrass () {
        return this.canBlockGrass;
    }

    @Override
    public final int getLightValue () {
        return this.HT_getLightValue();
    }

    public int HT_getLightValue () {
        return super.getLightValue();
    }

    @Override
    public final boolean getUseNeighborBrightness () {
        return this.HT_getUseNeighborBrightness();
    }

    public boolean HT_getUseNeighborBrightness () {
        return super.getUseNeighborBrightness();
    }

    @Override
    public final Material getMaterial () {
        return this.HT_getMaterial();
    }

    public Material HT_getMaterial () {
        return super.getMaterial();
    }

    @Override
    public final MapColor getMapColor (int p_149728_1_) {
        return this.HT_getMapColor(p_149728_1_);
    }

    public MapColor HT_getMapColor (int p_149728_1_) {
        return super.getMapColor(p_149728_1_);
    }

    @Override
    public final boolean isNormalCube () {
        return this.HT_isNormalCube();
    }

    public boolean HT_isNormalCube () {
        return super.isNormalCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final boolean isBlockNormalCube () {
        return this.HT_isBlockNormalCube();
    }

    @SideOnly(Side.CLIENT)
    public boolean HT_isBlockNormalCube () {
        return super.isBlockNormalCube();
    }

    @Override
    public final boolean renderAsNormalBlock () {
        return this.HT_renderAsNormalBlock();
    }

    public boolean HT_renderAsNormalBlock () {
        return super.renderAsNormalBlock();
    }

    @Override
    public final Block setStepSound (SoundType p_149672_1_) {
        return this.HT_setStepSound(p_149672_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setStepSound (SoundType soundType) {
        return (T) this.setStepSound(soundType);
    }

    @Override
    public final Block setLightOpacity (int p_149713_1_) {
        return this.HT_setLightOpacity(p_149713_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setLightOpacity (int lightOpacity) {
        return (T) this.setLightOpacity(lightOpacity);
    }

    @Override
    public final Block setLightLevel (float p_149715_1_) {
        return this.HT_setLightLevel(p_149715_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setLightLevel (float lightLevel) {
        return (T) this.setLightLevel(lightLevel);
    }

    @Override
    public final Block setResistance (float p_149752_1_) {
        return this.HT_setResistance(p_149752_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setResistance (float resistance) {
        return (T) this.setResistance(resistance);
    }

    @Override
    public final Block setHardness (float p_149711_1_) {
        return this.HT_setHardness(p_149711_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setHardness (float hardness) {
        return (T) this.setHardness(hardness);
    }

    @Override
    public final Block setBlockUnbreakable () {
        return this.HT_setBlockUnbreakable();
    }

    @SuppressWarnings("unchecked")
    public T HT_setBlockUnbreakable () {
        return (T) this.setBlockUnbreakable();
    }

    @Override
    public final Block setTickRandomly (boolean p_149675_1_) {
        return this.HT_setTickRandomly(p_149675_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setTickRandomly (boolean tickRandomly) {
        return (T) this.setTickRandomly(tickRandomly);
    }

    @Override
    public final boolean getBlocksMovement (IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
        return this.HT_getBlocksMovement(p_149655_1_, p_149655_2_, p_149655_3_, p_149655_4_);
    }

    public boolean HT_getBlocksMovement (IBlockAccess world, int x, int y, int z) {
        return super.getBlocksMovement(world, x, y, z);
    }

    @Override
    public final int getRenderType () {
        return this.HT_getRenderType();
    }

    public int HT_getRenderType () {
        return super.getRenderType();
    }

    @Override
    public final float getBlockHardness (World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_) {
        return this.HT_getBlockHardness(p_149712_1_, p_149712_2_, p_149712_3_, p_149712_4_);
    }

    public float HT_getBlockHardness (World world, int x, int y, int z) {
        return super.getBlockHardness(world, x, y, z);
    }

    @Override
    public final boolean getTickRandomly () {
        return this.HT_getTickRandomly();
    }

    public boolean HT_getTickRandomly () {
        return super.getTickRandomly();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final int getMixedBrightnessForBlock (IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
        return this.HT_getMixedBrightnessForBlock(p_149677_1_, p_149677_2_, p_149677_3_, p_149677_4_);
    }

    @SideOnly(Side.CLIENT)
    public int HT_getMixedBrightnessForBlock (IBlockAccess world, int x, int y, int z) {
        return super.getMixedBrightnessForBlock(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final boolean shouldSideBeRendered (IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return this.HT_shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }

    @SideOnly(Side.CLIENT)
    public boolean HT_shouldSideBeRendered (IBlockAccess world, int x, int y, int z, int side) {
        return super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public final boolean isBlockSolid (IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
        return this.HT_isBlockSolid(p_149747_1_, p_149747_2_, p_149747_3_, p_149747_4_, p_149747_5_);
    }

    public boolean HT_isBlockSolid (IBlockAccess world, int x, int y, int z, int side) {
        return super.isBlockSolid(world, x, y, z, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIcon (IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_) {
        return this.HT_getIcon(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_, p_149673_5_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon HT_getIcon (IBlockAccess world, int x, int y, int z, int meta) {
        return super.getIcon(world, x, y, z, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIcon (int p_149691_1_, int p_149691_2_) {
        return this.HT_getIcon(p_149691_1_, p_149691_2_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon HT_getIcon (int side, int meta) {
        return this.m_hasSubTypes ? HT_ArrayUtil.getWithNoEx(this.m_multiIcons, meta) : super.getIcon(side, meta);
    }

    @Override
    public final void addCollisionBoxesToList (World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        this.HT_addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
    }

    public void HT_addCollisionBoxesToList (World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
    }

    @Override
    public final AxisAlignedBB getCollisionBoundingBoxFromPool (World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return this.HT_getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    public AxisAlignedBB HT_getCollisionBoundingBoxFromPool (World world, int x, int y, int z) {
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final AxisAlignedBB getSelectedBoundingBoxFromPool (World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
        return this.HT_getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB HT_getSelectedBoundingBoxFromPool (World world, int x, int y, int z) {
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public final boolean isOpaqueCube () {
        return this.HT_isOpaqueCube();
    }

    public boolean HT_isOpaqueCube () {
        return super.isOpaqueCube();
    }

    @Override
    public final boolean canCollideCheck (int p_149678_1_, boolean p_149678_2_) {
        return this.HT_canCollideCheck(p_149678_1_, p_149678_2_);
    }

    public boolean HT_canCollideCheck (int meta, boolean isOnBoat) {
        return super.canCollideCheck(meta, isOnBoat);
    }

    @Override
    public final boolean isCollidable () {
        return this.HT_isCollidable();
    }

    public boolean HT_isCollidable () {
        return super.isCollidable();
    }

    @Override
    public final void updateTick (World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        this.HT_updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
    }

    public void HT_updateTick (World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void randomDisplayTick (World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        this.HT_randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
    }

    @SideOnly(Side.CLIENT)
    public void HT_randomDisplayTick (World world, int x, int y, int z, Random random) {
        super.randomDisplayTick(world, x, y, z, random);
    }

    @Override
    public final void onBlockDestroyedByPlayer (World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {
        this.HT_onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
    }

    public void HT_onBlockDestroyedByPlayer (World world, int x, int y, int z, int meta) {
        super.onBlockDestroyedByPlayer(world, x, y, z, meta);
    }

    @Override
    public final void onNeighborBlockChange (World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        this.HT_onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }

    public void HT_onNeighborBlockChange (World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
    }

    @Override
    public final int tickRate (World p_149738_1_) {
        return this.HT_tickRate(p_149738_1_);
    }

    public int HT_tickRate (World world) {
        return super.tickRate(world);
    }

    @Override
    public final void onBlockAdded (World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        this.HT_onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    public void HT_onBlockAdded (World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public final void breakBlock (World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        this.HT_breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    public void HT_breakBlock (World world, int x, int y, int z, Block block, int magic) {
        super.breakBlock(world, x, y, z, block, magic);
    }

    @Override
    public final int quantityDropped (Random p_149745_1_) {
        return this.HT_quantityDropped(p_149745_1_);
    }

    public int HT_quantityDropped (Random random) {
        return super.quantityDropped(random);
    }

    @Override
    public final Item getItemDropped (int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return this.HT_getItemDropped(p_149650_1_, p_149650_2_, p_149650_3_);
    }

    public Item HT_getItemDropped (int p_149650_1_, Random random, int p_149650_3_) {
        return super.getItemDropped(p_149650_1_, random, p_149650_3_);
    }

    @Override
    public final float getPlayerRelativeBlockHardness (EntityPlayer p_149737_1_, World p_149737_2_, int p_149737_3_, int p_149737_4_, int p_149737_5_) {
        return this.HT_getPlayerRelativeBlockHardness(p_149737_1_, p_149737_2_, p_149737_3_, p_149737_4_, p_149737_5_);
    }

    public float HT_getPlayerRelativeBlockHardness (EntityPlayer player, World world, int x, int y, int z) {
        return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
    }

    @Override
    public final void dropBlockAsItemWithChance (World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        this.HT_dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
    }

    public void HT_dropBlockAsItemWithChance (World world, int x, int y, int z, int fx, float fy, int fz) {
        super.dropBlockAsItemWithChance(world, x, y, z, fx, fy, fz);
    }

    @Override
    public final void dropBlockAsItem (World p_149642_1_, int p_149642_2_, int p_149642_3_, int p_149642_4_, ItemStack p_149642_5_) {
        this.HT_dropBlockAsItem(p_149642_1_, p_149642_2_, p_149642_3_, p_149642_4_, p_149642_5_);
    }

    public void HT_dropBlockAsItem (World world, int x, int y, int z, ItemStack itemStack) {
        super.dropBlockAsItem(world, x, y, z, itemStack);
    }

    @Override
    public final void dropXpOnBlockBreak (World p_149657_1_, int p_149657_2_, int p_149657_3_, int p_149657_4_, int p_149657_5_) {
        this.HT_dropXpOnBlockBreak(p_149657_1_, p_149657_2_, p_149657_3_, p_149657_4_, p_149657_5_);
    }

    public void HT_dropXpOnBlockBreak (World world, int x, int y, int z, int meta) {
        super.dropXpOnBlockBreak(world, x, y, z, meta);
    }

    @Override
    public final int damageDropped (int p_149692_1_) {
        return this.HT_damageDropped(p_149692_1_);
    }

    public int HT_damageDropped (int meta) {
        return this.m_hasSubTypes ? meta : super.damageDropped(meta);
    }

    @Override
    public final float getExplosionResistance (Entity p_149638_1_) {
        return this.HT_getExplosionResistance(p_149638_1_);
    }

    public float HT_getExplosionResistance (Entity entity) {
        return super.getExplosionResistance(entity);
    }

    @Override
    public final MovingObjectPosition collisionRayTrace (World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_) {
        return this.HT_collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }

    public MovingObjectPosition HT_collisionRayTrace (World world, int x, int y, int z, Vec3 start, Vec3 end) {
        return super.collisionRayTrace(world, x, y, z, start, end);
    }

    @Override
    public final void onBlockDestroyedByExplosion (World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
        this.HT_onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
    }

    public void HT_onBlockDestroyedByExplosion (World world, int x, int y, int z, Explosion explosion) {
        super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }

    @Override
    public final boolean canReplace (World p_149705_1_, int p_149705_2_, int p_149705_3_, int p_149705_4_, int p_149705_5_, ItemStack p_149705_6_) {
        return this.HT_canReplace(p_149705_1_, p_149705_2_, p_149705_3_, p_149705_4_, p_149705_5_, p_149705_6_);
    }

    public boolean HT_canReplace (World world, int x, int y, int z, int meta, ItemStack itemStack) {
        return super.canReplace(world, x, y, z, meta, itemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final int getRenderBlockPass () {
        return this.HT_getRenderBlockPass();
    }

    @SideOnly(Side.CLIENT)
    public int HT_getRenderBlockPass () {
        return super.getRenderBlockPass();
    }

    @Override
    public final boolean canPlaceBlockOnSide (World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_) {
        return this.HT_canPlaceBlockOnSide(p_149707_1_, p_149707_2_, p_149707_3_, p_149707_4_, p_149707_5_);
    }

    public boolean HT_canPlaceBlockOnSide (World world, int x, int y, int z, int side) {
        return super.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public final boolean canPlaceBlockAt (World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return this.HT_canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }

    public boolean HT_canPlaceBlockAt (World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public final boolean onBlockActivated (World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        return this.HT_onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    public boolean HT_onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float playerX, float playerY, float playerZ) {
        return super.onBlockActivated(world, x, y, z, player, side, playerX, playerY, playerZ);
    }

    @Override
    public final void onEntityWalking (World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_) {
        this.HT_onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
    }

    public void HT_onEntityWalking (World world, int x, int y, int z, Entity entity) {
        super.onEntityWalking(world, x, y, z, entity);
    }

    @Override
    public final int onBlockPlaced (World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        return this.HT_onBlockPlaced(p_149660_1_, p_149660_2_, p_149660_3_, p_149660_4_, p_149660_5_, p_149660_6_, p_149660_7_, p_149660_8_, p_149660_9_);
    }

    public int HT_onBlockPlaced (World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
    }

    @Override
    public final void onBlockClicked (World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {
        this.HT_onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }

    public void HT_onBlockClicked (World world, int x, int y, int z, EntityPlayer entityPlayer) {
        super.onBlockClicked(world, x, y, z, entityPlayer);
    }

    @Override
    public final void velocityToAddToEntity (World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_) {
        this.HT_velocityToAddToEntity(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_, p_149640_5_, p_149640_6_);
    }

    public void HT_velocityToAddToEntity (World world, int x, int y, int z, Entity entity, Vec3 vec3) {
        super.velocityToAddToEntity(world, x, y, z, entity, vec3);
    }

    @Override
    public final void setBlockBoundsBasedOnState (IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.HT_setBlockBoundsBasedOnState(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
    }

    public void HT_setBlockBoundsBasedOnState (IBlockAccess world, int x, int y, int z) {
        super.setBlockBoundsBasedOnState(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final int getBlockColor () {
        return this.HT_getBlockColor();
    }

    @SideOnly(Side.CLIENT)
    public int HT_getBlockColor () {
        return super.getBlockColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final int getRenderColor (int p_149741_1_) {
        return this.HT_getRenderColor(p_149741_1_);
    }

    @SideOnly(Side.CLIENT)
    public int HT_getRenderColor (int meta) {
        return super.getRenderColor(meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final int colorMultiplier (IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        return this.HT_colorMultiplier(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
    }

    @SideOnly(Side.CLIENT)
    public int HT_colorMultiplier (IBlockAccess world, int x, int y, int z) {
        return super.colorMultiplier(world, x, y, z);
    }

    @Override
    public final int isProvidingWeakPower (IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_) {
        return this.HT_isProvidingWeakPower(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, p_149709_5_);
    }

    public int HT_isProvidingWeakPower (IBlockAccess world, int x, int y, int z, int side) {
        return super.isProvidingWeakPower(world, x, y, z, side);
    }

    @Override
    public final boolean canProvidePower () {
        return this.HT_canProvidePower();
    }

    public boolean HT_canProvidePower () {
        return super.canProvidePower();
    }

    @Override
    public final void onEntityCollidedWithBlock (World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        this.HT_onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
    }

    public void HT_onEntityCollidedWithBlock (World world, int x, int y, int z, Entity entity) {
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
    }

    @Override
    public final int isProvidingStrongPower (IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_) {
        return this.HT_isProvidingStrongPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_);
    }

    public int HT_isProvidingStrongPower (IBlockAccess world, int x, int y, int z, int side) {
        return super.isProvidingStrongPower(world, x, y, z, side);
    }

    @Override
    public final void setBlockBoundsForItemRender () {
        this.HT_setBlockBoundsForItemRender();
    }

    public void HT_setBlockBoundsForItemRender () {
        super.setBlockBoundsForItemRender();
    }

    @Override
    public final void harvestBlock (World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        this.HT_harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
    }

    public void HT_harvestBlock (World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
    }

    @Override
    public final boolean canSilkHarvest () {
        return this.HT_canSilkHarvest();
    }

    public boolean HT_canSilkHarvest () {
        return super.canSilkHarvest();
    }

    @Override
    public final ItemStack createStackedBlock (int p_149644_1_) {
        return this.HT_createStackedBlock(p_149644_1_);
    }

    public ItemStack HT_createStackedBlock (int i) {
        return super.createStackedBlock(i);
    }

    @Override
    public final int quantityDroppedWithBonus (int p_149679_1_, Random p_149679_2_) {
        return this.HT_quantityDroppedWithBonus(p_149679_1_, p_149679_2_);
    }

    public int HT_quantityDroppedWithBonus (int range, Random random) {
        return super.quantityDroppedWithBonus(range, random);
    }

    @Override
    public final boolean canBlockStay (World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        return this.HT_canBlockStay(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
    }

    public boolean HT_canBlockStay (World world, int x, int y, int z) {
        return super.canBlockStay(world, x, y, z);
    }

    @Override
    public final void onBlockPlacedBy (World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        this.HT_onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
    }

    public void HT_onBlockPlacedBy (World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, entityLivingBase, itemStack);
    }

    @Override
    public final void onPostBlockPlaced (World p_149714_1_, int p_149714_2_, int p_149714_3_, int p_149714_4_, int p_149714_5_) {
        this.HT_onPostBlockPlaced(p_149714_1_, p_149714_2_, p_149714_3_, p_149714_4_, p_149714_5_);
    }

    public void HT_onPostBlockPlaced (World world, int x, int y, int z, int meta) {
        super.onPostBlockPlaced(world, x, y, z, meta);
    }


    @Override
    public final boolean onBlockEventReceived (World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_) {
        return this.HT_onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
    }

    public boolean HT_onBlockEventReceived (World world, int x, int y, int z, int i, int i2) {
        return super.onBlockEventReceived(world, x, y, z, i, i2);
    }

    @Override
    public final boolean getEnableStats () {
        return this.HT_getEnableStats();
    }

    public boolean HT_getEnableStats () {
        return super.getEnableStats();
    }

    @Override
    public final Block disableStats () {
        return this.HT_disableStats();
    }

    @SuppressWarnings("unchecked")
    public T HT_disableStats () {
        return (T) super.disableStats();
    }

    @Override
    public final int getMobilityFlag () {
        return this.HT_getMobilityFlag();
    }

    public int HT_getMobilityFlag () {
        return super.getMobilityFlag();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final float getAmbientOcclusionLightValue () {
        return this.HT_getAmbientOcclusionLightValue();
    }

    @SideOnly(Side.CLIENT)
    public float HT_getAmbientOcclusionLightValue () {
        return super.getAmbientOcclusionLightValue();
    }

    @Override
    public final void onFallenUpon (World p_149746_1_, int p_149746_2_, int p_149746_3_, int p_149746_4_, Entity p_149746_5_, float p_149746_6_) {
        this.HT_onFallenUpon(p_149746_1_, p_149746_2_, p_149746_3_, p_149746_4_, p_149746_5_, p_149746_6_);
    }

    public void HT_onFallenUpon (World world, int x, int y, int z, Entity entity, float f) {
        super.onFallenUpon(world, x, y, z, entity, f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final Item getItem (World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return this.HT_getItem(p_149694_1_, p_149694_2_, p_149694_3_, p_149694_4_);
    }

    @SideOnly(Side.CLIENT)
    public Item HT_getItem (World world, int x, int y, int z) {
        return super.getItem(world, x, y, z);
    }

    @Override
    public final int getDamageValue (World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
        return this.HT_getDamageValue(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_);
    }

    public int HT_getDamageValue (World world, int x, int y, int z) {
        return super.getDamageValue(world, x, y, z);
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubBlocks (Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        this.HT_registerMulti(p_149666_1_, p_149666_2_, p_149666_3_);
    }

    @SideOnly(Side.CLIENT)
    public void HT_registerMulti (Item item, CreativeTabs tab, final List<ItemStack> list) {
        for (HT_ItemStackBuilder e : m_subItems) {
            list.add(e.build(1));
        }
    }

    @Override
    public final Block setCreativeTab (CreativeTabs p_149647_1_) {
        return this.HT_setCreativeTab(p_149647_1_);
    }

    @SuppressWarnings("unchecked")
    public T HT_setCreativeTab (CreativeTabs tab) {
        return (T) super.setCreativeTab(tab);
    }

    @Override
    public final void onBlockHarvested (World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        this.HT_onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }

    public void HT_onBlockHarvested (World world, int x, int y, int z, int meta, EntityPlayer player) {
        super.onBlockHarvested(world, x, y, z, meta, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final CreativeTabs getCreativeTabToDisplayOn () {
        return this.HT_getCreativeTabToDisplayOn();
    }

    @SideOnly(Side.CLIENT)
    public CreativeTabs HT_getCreativeTabToDisplayOn () {
        return super.getCreativeTabToDisplayOn();
    }

    @Override
    public final void onBlockPreDestroy (World p_149725_1_, int p_149725_2_, int p_149725_3_, int p_149725_4_, int p_149725_5_) {
        this.HT_onBlockPreDestroy(p_149725_1_, p_149725_2_, p_149725_3_, p_149725_4_, p_149725_5_);
    }

    public void HT_onBlockPreDestroy (World world, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(world, x, y, z, meta);
    }

    @Override
    public final void fillWithRain (World p_149639_1_, int p_149639_2_, int p_149639_3_, int p_149639_4_) {
        this.HT_fillWithRain(p_149639_1_, p_149639_2_, p_149639_3_, p_149639_4_);
    }

    public void HT_fillWithRain (World world, int x, int y, int z) {
        super.fillWithRain(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final boolean isFlowerPot () {
        return this.HT_isFlowerPot();
    }

    public boolean HT_isFlowerPot () {
        return super.isFlowerPot();
    }

    @Override
    public final boolean func_149698_L () {
        return this.HT_func_149698_L();
    }

    public boolean HT_func_149698_L () {
        return super.func_149698_L();
    }

    @Override
    public final boolean canDropFromExplosion (Explosion p_149659_1_) {
        return this.HT_canDropFromExplosion(p_149659_1_);
    }

    public boolean HT_canDropFromExplosion (Explosion explosion) {
        return super.canDropFromExplosion(explosion);
    }

    @Override
    public final boolean isAssociatedBlock (Block p_149667_1_) {
        return this.HT_isAssociatedBlock(p_149667_1_);
    }

    public boolean HT_isAssociatedBlock (Block block) {
        return super.isAssociatedBlock(block);
    }

    @Override
    public final boolean hasComparatorInputOverride () {
        return this.HT_hasComparatorInputOverride();
    }

    public boolean HT_hasComparatorInputOverride () {
        return super.hasComparatorInputOverride();
    }

    @Override
    public final int getComparatorInputOverride (World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_) {
        return this.HT_getComparatorInputOverride(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_, p_149736_5_);
    }

    public int HT_getComparatorInputOverride (World world, int x, int y, int z, int side) {
        return super.getComparatorInputOverride(world, x, y, z, side);
    }


    public void HT_setBlockBounds (float xMin, float yMin, float zMin, float xMax, float yMax, float zMax) {
        this.setBlockBounds(xMin, yMin, zMin, xMax, yMax, zMax);
    }


}
