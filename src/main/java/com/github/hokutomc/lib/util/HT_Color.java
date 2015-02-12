package com.github.hokutomc.lib.util;

import com.github.hokutomc.lib.common.HT_I_International;

/**
 * Created by user on 2015/02/07.
 */
public enum  HT_Color implements HT_I_International {
    BLACK("black", 0x1e1b1b),
    RED("red", 0xb3312c),
    GREEN("green", 0x3b511a),
    BROWN("brown", 0x51301a),
    BLUE("blue", 0x253192),
    PURPLE("purple", 0x7b2fbe),
    CYAN("cyan", 0x287697),
    SILVER("silver", 0xababab),
    GRAY("gray", 0x434343),
    PINK("pink", 0xd88198),
    LIME("lime", 0x41cd34),
    YELLOW("yellow", 0xdecf2a),
    LIGHT_BLUE("lightBlue", 0x6689d3),
    MAGENTA("magenta", 0xc354cd),
    ORANGE("orange", 0xeb8844),
    WHITE("white", 0xf0f0f0);


    private final String name;
    private int rgb;
    private final String capitalized;
    private final String dye;

    private String capitalize (String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    HT_Color (String name, int rgb) {
        this.name = name;
        this.rgb = rgb;
        this.capitalized = capitalize(name);
        this.dye = "dye" + capitalized;
    }

    public int getColorCode () {
        return this.rgb;
    }

    public String getName () {
        return this.name;
    }

    public String getCapitalizedName () {
        return this.capitalized;
    }

    public String getDyeName () {
        return this.dye;
    }

    public int toColoredBlockMeta () {
        return ~this.ordinal() & 15;
    }

    @Override
    public String getUnlocalizedName () {
        return "color." + this.name + ".name";
    }

    @Override
    public String localize () {
        return HT_I18nUtil.localize(this.getUnlocalizedName());
    }



    public static String[] colorNames () {
        String[] names = new String[16];
        for (int i = 0; i < values().length; i++) {
            names[i] = values()[i].getName();
        }
        return names;
    }

    public static HT_Color get (int ordinal) {
        return ordinal < 16 ? values()[ordinal] : WHITE;
    }

    public static HT_Color getByColoredBlockMeta (int meta) {
        for (HT_Color c : values()) {
            if (meta == c.toColoredBlockMeta()) return c;
        }
        return WHITE;
    }

}
