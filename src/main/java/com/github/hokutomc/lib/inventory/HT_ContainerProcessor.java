package com.github.hokutomc.lib.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by user on 2014/11/28.
 */
public abstract class HT_ContainerProcessor<T extends TileEntity> extends Container {
    private final InventoryPlayer m_inventoryPlayer;
    public final T m_tileEntity;

    public HT_ContainerProcessor (EntityPlayer player, T tileEntity) {
        this.m_tileEntity = tileEntity;
        this.m_inventoryPlayer = player.inventory;
    }

    protected abstract int getInventorySize ();

    protected HT_ContainerProcessor setPlayerSlotPosition (int bottomY) {
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(m_inventoryPlayer,
                        j + i * 9 + 9,
                        8 + j * 18,
                        bottomY - 67 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(m_inventoryPlayer, i, 8 + i * 18, bottomY - 9));
        }
        return this;
    }
}
