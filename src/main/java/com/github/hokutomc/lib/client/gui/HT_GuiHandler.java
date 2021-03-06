package com.github.hokutomc.lib.client.gui;

import com.github.hokutomc.lib.HT_Registries;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.Map;

/**
 * This class contains list of GUI-creation actions.
 * You should call register() and you will be able to open GUI in actions that this class's instance has.
 * 2014/11/05.
 */
public class HT_GuiHandler implements IGuiHandler {

    private Map<Integer, HT_GuiRegistration> m_mapGuiRegistration = Maps.newHashMap();


    public HT_GuiHandler register (Object mod) {
        HT_Registries.registerGUIHandler(mod, this);
        return this;
    }

    public HT_GuiHandler addGui (int id, HT_GuiAction<? extends Container> serverAction, HT_GuiAction<? extends Gui> clientAction) {
        this.m_mapGuiRegistration.put(id, new HT_GuiRegistration(id, serverAction, clientAction));
        return this;
    }

    @Override
    public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        HT_GuiRegistration r = m_mapGuiRegistration.get(ID);
        return r != null ? r.actionServer(player, world, x, y, z) : null;
    }

    @Override
    public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        HT_GuiRegistration r = m_mapGuiRegistration.get(ID);
        return r != null ? r.actionClient(player, world, x, y, z) : null;
    }

    public class HT_GuiRegistration {

        final int id;
        private final HT_GuiAction container;
        private final HT_GuiAction gui;

        public HT_GuiRegistration (int id, HT_GuiAction server, HT_GuiAction client) {
            this.id = id;
            this.container = server;
            this.gui = client;
        }

        public Object actionServer (EntityPlayer player, World world, int x, int y, int z) {
            return this.container.get(player, world, x, y, z);
        }

        public Object actionClient (EntityPlayer player, World world, int x, int y, int z) {
            return this.gui.get(player, world, x, y, z);
        }
    }
}
