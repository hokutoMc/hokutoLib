package com.github.hokutomc.lib.item;

import com.github.hokutomc.lib.nbt.HT_NBTEvidence;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collections;
import java.util.Set;

/**
 * Created by user on 2015/01/19.
 */
public abstract class HT_ItemCondition extends HT_ItemBuilder {
    private HT_ItemCondition () {
    }

    public abstract boolean check (ItemStack itemStack);

    public static HT_ItemCondition ofItem (Item item) {
        return new ConditionSimpleItem(item);
    }

    public static HT_ItemCondition ofBlock (Block block) {
        return new ConditionSimpleBlock(block);
    }

    /**
     * Creates new ItemCondition that checks the itemStack's block and damage.
     *
     * @param itemStack
     * @return
     */
    public static HT_ItemCondition ofItemStack (ItemStack itemStack) {
        return new ConditionSensitiveItem(itemStack.getItem(), true, itemStack.getItemDamage(), Collections.<EvidenceAndValue>emptySet());
    }

    public static BuilderBlock builder (Block block) {
        return new BuilderBlock(block);
    }

    public static BuilderItem builder (Item item) {
        return new BuilderItem(item);
    }

    public static abstract class ConditionSensitive extends HT_ItemCondition {
        public final boolean checkDamage;
        public final int damage;
        public final Set<EvidenceAndValue> condNBT;

        private ConditionSensitive (boolean checkDamage, int damage, Set<EvidenceAndValue> set) {
            this.checkDamage = checkDamage;
            this.damage = damage;
            this.condNBT = ImmutableSet.copyOf(set);
        }

        protected abstract Item getItem ();

        @Override
        public boolean check (ItemStack itemStack) {
            if (this.getItem() != itemStack.getItem() || (this.checkDamage && this.damage != itemStack.getItemDamage())) {
                return false;
            }

            if (condNBT.isEmpty()) {
                return true;
            }

            for (EvidenceAndValue ev : condNBT) {
                if (!ev.check(itemStack.getTagCompound())) return false;
            }
            return true;
        }

        @Override
        public ItemStack createStack () {
            ItemStack stack = new ItemStack(this.getItem(), 1, this.damage);

            if (condNBT.isEmpty()) {
                return stack;
            }

            NBTTagCompound tag = new NBTTagCompound();

            for (EvidenceAndValue ev : condNBT) {
                ev.write(tag);
            }

            stack.setTagCompound(tag);
            return stack;
        }

        @Override
        public boolean equals (Object obj) {
            if (!(obj instanceof ConditionSensitive)) {
                return false;
            }
            ConditionSensitive other = (ConditionSensitive) obj;
            return this.getItem() == other.getItem()
                    && this.checkDamage == other.checkDamage
                    && this.damage == other.damage
                    && this.condNBT.equals(other.condNBT);
        }
    }

    public static class ConditionSensitiveItem extends ConditionSensitive {
        public final Item item;

        private ConditionSensitiveItem (Item item, boolean checkDamage, int damage, Set<EvidenceAndValue> set) {
            super(checkDamage, damage, set);
            this.item = item;
        }

        @Override
        protected Item getItem () {
            return this.item;
        }

        @Override
        public int hashCode () {
            return Objects.hashCode(this.getItem(), this.checkDamage, this.damage, this.condNBT);
        }

        @Override
        public String toString () {
            return "ItemCondition(block=" + this.getItem().getUnlocalizedName() + (this.checkDamage ? ", damage=" + this.damage : "") + ", nbt = " + this.condNBT + ")";
        }
    }

    public static class ConditionSimpleItem extends HT_ItemCondition {
        public final Item item;

        private ConditionSimpleItem (Item item) {
            this.item = item;
        }

        @Override
        public boolean check (ItemStack itemStack) {
            return this.item == itemStack.getItem();
        }

        @Override
        public ItemStack createStack () {
            return new ItemStack(this.item);
        }

        @Override
        public boolean equals (Object obj) {
            return (obj instanceof ConditionSimpleItem) && this.item == ((ConditionSimpleItem) obj).item;
        }

        @Override
        public int hashCode () {
            return this.item.hashCode();
        }

        @Override
        public String toString () {
            return "ItemCondition(block=" + this.item.getUnlocalizedName() + ")";
        }
    }

    public static class ConditionSimpleBlock extends HT_ItemCondition {
        public final Block block;

        private ConditionSimpleBlock (Block block) {
            this.block = block;
        }

        @Override
        public boolean check (ItemStack itemStack) {
            return Item.getItemFromBlock(this.block) == itemStack.getItem();
        }

        @Override
        public ItemStack createStack () {
            return new ItemStack(this.block);
        }

        @Override
        public boolean equals (Object obj) {
            return (obj instanceof ConditionSimpleBlock) && this.block == ((ConditionSimpleBlock) obj).block;
        }

        @Override
        public int hashCode () {
            return this.block.hashCode();
        }

        @Override
        public String toString () {
            return "ItemCondition(block=" + this.block.getUnlocalizedName() + ")";
        }
    }

    public static class BuilderItem {
        private Set<EvidenceAndValue> set = Sets.newHashSet();
        private boolean checkDamage = false;
        private int damage = 0;

        private final Item item;

        public BuilderItem (Item item) {
            this.item = item;
        }

        public BuilderItem (ItemStack itemStack) {
            this(itemStack.getItem());
            checkDamage(itemStack.getItemDamage());
        }

        public BuilderItem checkDamage (int damage) {
            this.checkDamage = true;
            this.damage = damage;
            return this;
        }

        public <T> BuilderItem addCondition (String key, HT_NBTEvidence<T> ev, T value) {
            this.set.add(new EvidenceAndValue(key, ev, value));
            return this;
        }

        public HT_ItemCondition build () {
            if (!checkDamage && set.isEmpty()) {
                return ofItem(this.item);
            }

            return new ConditionSensitiveItem(this.item, this.checkDamage, this.damage, this.set);
        }
    }

    public static class ConditionSensitiveBlock extends ConditionSensitive {
        public final Block block;

        private ConditionSensitiveBlock (Block block, boolean checkDamage, int damage, Set<EvidenceAndValue> set) {
            super(checkDamage, damage, set);
            this.block = block;
        }

        @Override
        protected Item getItem () {
            return Item.getItemFromBlock(this.block);
        }

        @Override
        public int hashCode () {
            return Objects.hashCode(this.block, this.checkDamage, this.damage, this.condNBT);
        }

        @Override
        public String toString () {
            return "ItemCondition(block=" + this.getItem().getUnlocalizedName() + (this.checkDamage ? ", damage=" + this.damage : "") + ", nbt = " + this.condNBT + ")";
        }
    }

    public static class BuilderBlock {
        private Set<EvidenceAndValue> set = Sets.newHashSet();
        private boolean checkDamage = false;
        private int damage = 0;

        private final Block block;


        public BuilderBlock (Block block) {
            this.block = block;
        }

        public BuilderBlock checkDamage (int damage) {
            this.checkDamage = true;
            this.damage = damage;
            return this;
        }

        public <T> BuilderBlock addCondition (String key, HT_NBTEvidence<T> ev, T value) {
            this.set.add(new EvidenceAndValue(key, ev, value));
            return this;
        }

        public HT_ItemCondition build () {
            if (!checkDamage && set.isEmpty()) {
                return ofBlock(this.block);
            }

            return new ConditionSensitiveBlock(this.block, this.checkDamage, this.damage, this.set);
        }
    }

    private static class EvidenceAndValue {
        private final String key;
        private final HT_NBTEvidence ev;
        private final Object value;

        private EvidenceAndValue (String key, HT_NBTEvidence ev, Object value) {
            this.key = key;
            this.ev = ev;
            this.value = value;
        }

        private boolean check (NBTTagCompound tagCompound) {
            return tagCompound.hasKey(this.key) && this.value == this.ev.read(key, tagCompound);
        }

        @SuppressWarnings("unchecked")
        public void write (NBTTagCompound tagCompound) {
            this.ev.write(this.key, tagCompound, this.value);
        }

        @Override
        public boolean equals (Object obj) {
            return (obj instanceof EvidenceAndValue) && this.key.equals(((EvidenceAndValue) obj).key);
        }

        @Override
        public int hashCode () {
            return this.key.hashCode();
        }

        @Override
        public String toString () {
            return this.key + "<" + this.ev.getTypeString() + ">: " + this.value;
        }
    }
}