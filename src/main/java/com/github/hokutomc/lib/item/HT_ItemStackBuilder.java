package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import javax.naming.OperationNotSupportedException;

/**
 * Created by user on 2015/01/19.
 */
public class HT_ItemStackBuilder<T extends HT_ItemStackBuilder> {
    private Block m_block;
    private int m_damage;
    private ItemStack m_template;
    private boolean m_isBlockEvaluated;
    private int m_size;

    protected HT_ItemStackBuilder (ItemStack template) {
        this.m_size = 1;
        this.m_template = template;
        this.m_isBlockEvaluated = true;
    }

    public HT_ItemStackBuilder (Item item) {
        this(item, 0);
    }

    public HT_ItemStackBuilder (Item item, int meta) {
        this(new ItemStack(item, 1, meta));
        this.m_damage = meta;
    }

    public HT_ItemStackBuilder (Block block) {
        this(block, 0);
    }

    public HT_ItemStackBuilder (Block block, int meta) {
        this(new ItemStack(block, 1, meta));
        this.m_block = block;
        this.m_damage = meta;
        this.m_isBlockEvaluated = false;
        tryToSolveItemBlock();
    }

    public static HT_ItemStackBuilder start (Item item) {
        return new HT_ItemStackBuilder(item);
    }

    public static HT_ItemStackBuilder apply (Block block) {
        return new HT_ItemStackBuilder(block);
    }

    public ItemStack build () {
        return build(this.m_size);
    }

    public ItemStack build (int size) {
        if (!m_isBlockEvaluated) tryToSolveItemBlock();
        ItemStack copy = m_template.copy();
        copy.stackSize = size;
        copy.setItemDamage(m_damage);
        return copy;
    }

    private void tryToSolveItemBlock () {
        if (m_block == null) return;
        Item item = Item.getItemFromBlock(m_block);
        if (item != null) {
            NBTTagCompound tag = this.m_template.stackTagCompound;
            this.m_template = new ItemStack(m_block, 1, m_damage);
            this.m_template.stackTagCompound = tag;
            this.m_isBlockEvaluated = true;
        }
    }

    @SuppressWarnings("unchecked")
    public T size (int size) {
        this.m_size = size;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T damage (int damage) {
        this.m_damage = damage;
        return (T) this;
    }

    public T wildCard () {
        return this.damage(OreDictionary.WILDCARD_VALUE);
    }

    public NBTTagCompound getNBTTag () {
        if (m_template.getTagCompound() == null) {m_template.stackTagCompound = new NBTTagCompound();}
        return m_template.stackTagCompound;
    }

    @SuppressWarnings("unchecked")
    public T setInt (String key, int value) {
        getNBTTag().setInteger(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setByte (String key, byte value) {
        getNBTTag().setByte(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setShort (String key, short value) {
        getNBTTag().setShort(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setLong (String key, long value) {
        getNBTTag().setLong(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setByteArray (String key, byte[] values) {
        getNBTTag().setByteArray(key, values);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setIntArray (String key, int[] values) {
        getNBTTag().setIntArray(key, values);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setFloat (String key, float value) {
        getNBTTag().setFloat(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setDouble (String key, double value) {
        getNBTTag().setDouble(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setBoolean (String key, boolean value) {
        getNBTTag().setBoolean(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setString (String key, String value) {
        getNBTTag().setString(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <E extends Enum<E>> T setEnum (String key, E value) {
        HT_NBTUtil.writeEnum(key, getNBTTag(), value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTag (String key, NBTBase nbtBase) {
        getNBTTag().setTag(key, nbtBase);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setItemStack (String key, ItemStack value) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        value.writeToNBT(getNBTTag());
        setTag(key, tagCompound);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setItemStackArray (String key, ItemStack[] values) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        HT_NBTUtil.writeItemStacks(getNBTTag(), values);
        setTag(key, tagCompound);
        return (T) this;
    }

    public T setPart (HT_ItemArmor.Part part) {
        return setInt(HT_ItemArmor.KEY_PART, part.ordinal());
    }

    @SuppressWarnings("unchecked")
    public T fullDurability () {
        ItemStack stack = this.m_template.copy();
        HT_ItemDurable itemDurable = HT_ItemStackUtil.getItemAs(stack, HT_ItemDurable.class);
        if (itemDurable != null) {
            setInt(HT_ItemDurable.KEY_DURABILITY, itemDurable.getMaxDurability(stack));
            return setBoolean(HT_ItemDurable.KEY_BROKEN, false);
        }
        try {
            throw new OperationNotSupportedException("invalid operation fullDurabilty() to itemStackBuilder with non-HT_ItemDurable item");
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
        return (T)this;
    }

    @Override
    public String toString () {
        return this.m_template.getItem().toString() + "@" + this.m_template.getItemDamage();
    }
}
