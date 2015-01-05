package com.github.hokutomc.lib.client.gui;

import com.github.hokutomc.lib.inventory.HT_ContainerProcessor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by user on 2014/12/08.
 */
public abstract class HT_GuiContainer<T extends TileEntity> extends GuiContainer {
    private HT_ContainerProcessor<T> m_container;

    public HT_GuiContainer (HT_ContainerProcessor<T> container) {
        super(container);

        this.m_container = container;
    }

    @SuppressWarnings("unchecked")
    protected T getTileEntity () {
        return m_container.m_tileEntity;
    }
}
