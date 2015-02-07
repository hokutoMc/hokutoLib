package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import com.github.hokutomc.lib.util.HT_GeneralUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

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

    public NBTTagCompound getNBTTag () {
        return HT_GeneralUtil.nullChecked(m_template.getTagCompound(), new NBTTagCompound());
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

    @Override
    public String toString () {
        return this.m_template.getItem().toString() + "@" + this.m_template.getItemDamage();
    }
}