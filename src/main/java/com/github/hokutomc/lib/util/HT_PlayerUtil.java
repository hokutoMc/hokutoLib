package com.github.hokutomc.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * Created by user on 2015/02/26.
 */
public final class HT_PlayerUtil {
    private HT_PlayerUtil(){}

    public static EntityPlayer displayMessage (EntityPlayer player, String message) {
        player.addChatComponentMessage(new ChatComponentText(message));
        return player;
    }
}
