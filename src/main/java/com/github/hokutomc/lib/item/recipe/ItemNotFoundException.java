package com.github.hokutomc.lib.item.recipe;

/**
 * Created by user on 2015/01/29.
 */
public class ItemNotFoundException extends Throwable {

    public ItemNotFoundException (String modid, String name) {
        super("Item/Block \"" + modid + ":" + name + " not found so that couldn't add recipe.");
    }
}
