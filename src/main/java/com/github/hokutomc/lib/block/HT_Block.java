package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.HT_Registries;
import com.github.hokutomc.lib.client.render.HT_RenderUtil;
import com.github.hokutomc.lib.item.HT_ItemBuilder;
import com.github.hokutomc.lib.item.HT_ItemCondition;
import com.github.hokutomc.lib.util.HT_GeneralUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * This class wraps Block.class.
 * <p/>
 * 2014/09/23.
 */
public class HT_Block<T extends HT_Block<T>> extends Block implements HT_I_Block<T> {
    private boolean m_hasSubTypes;
    private String m_shortName;
    protected boolean m_fallInstantly;
    public String m_modid;
    protected List<HT_ItemBuilder> m_subItems;

    private ImmutableList<String> m_multiNames;

    public HT_Block (String modid, Material material, String innerName) {
        super(material);
        this.m_hasSubTypes = false;
        this.m_modid = modid;
        this.m_shortName = innerName;
        this.setInnerName(modid, innerName);
        m_subItems = Lists.<HT_ItemBuilder>newArrayList(HT_ItemCondition.ofBlock(this));
    }

    @Override
    public String getNameToRegister () {
        return this.getShortName();
    }

    private T setInnerName (String modid, String innerName) {
        this.m_shortName = innerName;
        return cast(this.setUnlocalizedName(modid + "." + innerName));
    }

    @SuppressWarnings("unchecked")
    private T cast (Block block) {
        return (T) block;
    }

    public T multi (String... subNames) {
        this.m_multiNames = ImmutableList.copyOf(subNames);
        this.setHasSubTypes(true);
        for (int i = 1; i < m_multiNames.size(); i++) {
            m_subItems.add(HT_ItemCondition.builder(this).checkDamage(i).build());
        }
        return cast(this);
    }

    public T register () {
        if (this.m_hasSubTypes) HT_Registries.<HT_Block>registerMultiBlock(this);
        else HT_Registries.<HT_Block>registerBlock(this);
        return cast(this);
    }

    public void registerMesher () {
        if (this.getHasSubTypes()) {
            for (int i = 0; i < m_multiNames.size(); i++) {
                HT_RenderUtil.registerOneItemMesher(this.getItem(), i, this.m_modid + ":" + this.getShortName() + m_multiNames.get(i));
            }
        } else {
            HT_RenderUtil.registerOneItemMesher(this.getItem(), 0, this.m_modid + ":" + this.getShortName());
        }
    }


    public List<String> getMultiNames () {
        return HT_GeneralUtil.orElse(this.m_multiNames, ImmutableList.<String>of());
    }

    public boolean getHasSubTypes () {
        return this.m_hasSubTypes;
    }

    public T setHasSubTypes (boolean b) {
        this.m_hasSubTypes = b;
        return cast(this);
    }

    public Item getItem () {
        return Item.getItemFromBlock(this);
    }

    public void fall (World world, BlockPos pos) {
        if (canContinueFalling(world, pos) && pos.getY() >= 0) {
            byte b0 = 32;

            if (!m_fallInstantly && world.isAreaLoaded(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0))) {
                if (!world.isRemote) {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), world.getBlockState(pos));
                    this.fallAnvil(entityfallingblock);
                    world.spawnEntityInWorld(entityfallingblock);
                }
            } else {
                world.setBlockToAir(pos);

                int counter = 0;

                while (canContinueFalling(world, pos.add(0, -1, 0)) && pos.getY() > 0) {
                    counter++;
                }

                if (pos.getY() - counter > 0) {
                    world.setBlockState(pos.add(0, -counter, 0), this.getDefaultState());
                }
            }
        }
    }

    private boolean canContinueFalling (World world, BlockPos pos) {
        IBlockState blockState1 = world.getBlockState(pos);

        if (blockState1.getBlock().isAir(world, pos)) {
            return true;
        } else if (blockState1.getBlock() == Blocks.fire) {
            return true;
        } else {
            Material material = blockState1.getBlock().getMaterial();
            return material == Material.water || material == Material.lava;
        }
    }

    private void fallAnvil (EntityFallingBlock entityfallingblock) {
    }

    @Override
    public String toString () {
        return this.getShortName();
    }

    public String getShortName () {
        return this.m_shortName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubBlocks (Item itemIn, CreativeTabs tab, List list) {
        this.HT_registerMulti(itemIn, tab, (List<ItemStack>) list);
    }

    public void HT_registerMulti (Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (HT_ItemBuilder b : this.m_subItems) {
            subItems.add(b.createStack());
        }
    }

    /**
     * Sets the footstep sound for the block. Returns the object for convenience in constructing.
     */
    public T HT_setStepSound (Block.SoundType sound) {
        return cast(this.setStepSound(sound));
    }

    /**
     * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
     */
    public T HT_setLightOpacity (int opacity) {
        return cast(this.setLightOpacity(opacity));
    }

    /**
     * Sets the light value that the block emits. Returns resulting block instance for constructing convenience. Args:
     * level
     */
    public T HT_setLightLevel (float value) {
        return cast(this.setLightLevel(value));
    }

    /**
     * Sets the the blocks resistance to explosions. Returns the object for convenience in constructing.
     */
    public T HT_setResistance (float resistance) {
        return cast(this.setResistance(resistance));
    }

    /**
     * Sets how many hits it takes to break a block.
     */
    public T HT_setHardness (float hardness) {
        return cast(this.setHardness(hardness));
    }

    public T HT_setBlockUnbreakable () {
        return cast(this.setBlockUnbreakable());
    }

    /**
     * Sets whether this block type will receive random update ticks
     */
    public T HT_setTickRandomly (boolean shouldTick) {
        return cast(this.setTickRandomly(shouldTick));
    }

    public T HT_setCreativeTab (CreativeTabs tab) {
        return cast(this.setCreativeTab(tab));
    }
}
