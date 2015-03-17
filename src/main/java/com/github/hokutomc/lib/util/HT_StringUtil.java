package com.github.hokutomc.lib.util;

/**
 * Created by user on 2014/10/11.
 */
public final class HT_StringUtil {
    private HT_StringUtil () {
    }

    public static String blanks (int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static String capitalize (String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append(i == 0 ? Character.toUpperCase(string.charAt(i)) : string.charAt(i));
        }
        return stringBuilder.toString();
    }
}
