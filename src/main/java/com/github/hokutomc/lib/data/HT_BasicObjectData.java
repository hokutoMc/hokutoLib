package com.github.hokutomc.lib.data;

import com.github.hokutomc.lib.reflect.HT_Reflections;
import com.sun.istack.internal.NotNull;
import net.minecraft.tileentity.TileEntity;

import java.util.EnumSet;

/**
 * Created by user on 2014/12/08.
 */
@Deprecated
public class HT_BasicObjectData<P> extends HT_SavableData<TileEntity, P, HT_BasicObjectData<P>> {
    private P value;

    @SafeVarargs
    public HT_BasicObjectData (String nbtKey, @NotNull P... empty) {
        super(nbtKey, empty);
    }

    @SafeVarargs
    public static <R extends Enum<R>> HT_BasicObjectData<EnumSet<R>> createEnumSetData (String nbtKey, @NotNull R... empty) {
        HT_BasicObjectData<EnumSet<R>> data = new HT_BasicObjectData<>(nbtKey);
        data.flagType = HT_Reflections.getClass(empty);
        return data;
    }

    @Override
    public void update (TileEntity entity, @NotNull P property) {
        if (property != null) {
            this.value = property;
        }
    }

    public void reset () {
        this.value = null;
    }

    @Override
    public P get (TileEntity entity) {
        return this.value;
    }


}
