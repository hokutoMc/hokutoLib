package com.github.hokutomc.lib.client.gui;

import com.github.hokutomc.lib.HT_Registries;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by user on 2014/11/05.
 */
public class HT_GuiHandler implements IGuiHandler {

    private ArrayList<HT_GuiRegistration> guiRegistrations = Lists.newArrayList();


    public HT_GuiHandler HT_register (Object mod) {
        HT_Registries.registerGUIHandler(mod, this);
        return this;
    }

    public HT_GuiHandler HT_addGui (int id, HT_GuiAction<? extends Container> serverAction, HT_GuiAction<? extends Gui> clientAction) {
        this.guiRegistrations.add(new HT_GuiRegistration(id, serverAction, clientAction));
        return this;
    }

    @Override
    public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        for (HT_GuiRegistration e : guiRegistrations) {
            if (ID == e.id | e.container != null) {
                return e.HT_actionServer(player, world, x, y, z);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        for (HT_GuiRegistration e : guiRegistrations) {
            if (ID == e.id | e.gui != null) {
                return e.HT_actionClient(player, world, x, y, z);
            }
        }
        return null;
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

        public Object HT_actionServer (EntityPlayer player, World world, int x, int y, int z) {
            return this.container.get(player, world, x, y, z);
        }

        public Object HT_actionClient (EntityPlayer player, World world, int x, int y, int z) {
            return this.gui.get(player, world, x, y, z);
        }
    }
}
