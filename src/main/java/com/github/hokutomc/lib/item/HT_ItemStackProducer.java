package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.nbt.HT_NBTUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by user on 2015/01/19.
 */
public class HT_ItemStackProducer {
    private Block m_block;
    private int m_damage;
    private ItemStack m_template;
    private boolean m_isBlockEvaluated;

    private HT_ItemStackProducer (ItemStack template) {
        this.m_template = template;
        this.m_isBlockEvaluated = true;
    }

    public HT_ItemStackProducer (Item item, int meta) {
        this(new ItemStack(item, 1, meta));
        this.m_damage = meta;
    }

    public HT_ItemStackProducer (Block block, int meta) {
        this(new ItemStack(block, 1, meta));
        this.m_block = block;
        this.m_damage = meta;
        this.m_isBlockEvaluated = false;
        tryToSolveItemBlock();
    }

    public ItemStack produce (int size) {
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

    public NBTTagCompound getNBTTag () {
        return m_template.getTagCompound();
    }

    public HT_ItemStackProducer setInt (String key, int value) {
        getNBTTag().setInteger(key, value);
        return this;
    }

    public HT_ItemStackProducer setByte (String key, byte value) {
        getNBTTag().setByte(key, value);
        return this;
    }

    public HT_ItemStackProducer setShort (String key, short value) {
        getNBTTag().setShort(key, value);
        return this;
    }

    public HT_ItemStackProducer setLong (String key, long value) {
        getNBTTag().setLong(key, value);
        return this;
    }

    public HT_ItemStackProducer setByteArray (String key, byte[] values) {
        getNBTTag().setByteArray(key, values);
        return this;
    }

    public HT_ItemStackProducer setIntArray (String key, int[] values) {
        getNBTTag().setIntArray(key, values);
        return this;
    }

    public HT_ItemStackProducer setFloat (String key, float value) {
        getNBTTag().setFloat(key, value);
        return this;
    }

    public HT_ItemStackProducer setDouble (String key, double value) {
        getNBTTag().setDouble(key, value);
        return this;
    }

    public HT_ItemStackProducer setBoolean (String key, boolean value) {
        getNBTTag().setBoolean(key, value);
        return this;
    }

    public HT_ItemStackProducer setString (String key, String value) {
        getNBTTag().setString(key, value);
        return this;
    }

    public <E extends Enum<E>> HT_ItemStackProducer setEnum (String key, E value) {
        HT_NBTUtil.writeEnumToNBT(key, getNBTTag(), value);
        return this;
    }

    public HT_ItemStackProducer setTag (String key, NBTBase nbtBase) {
        getNBTTag().setTag(key, nbtBase);
        return this;
    }

    public HT_ItemStackProducer setItemStack (String key, ItemStack value) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        value.writeToNBT(getNBTTag());
        setTag(key, tagCompound);
        return this;
    }

    @Override
    public String toString () {
        return this.m_template.getItem().toString() + "@" + this.m_template.getItemDamage();
    }
}
