package com.github.hokutomc.lib.process;

/**
 * This class expresses abstract recipe type.
 *
 * on 2014/11/08.
 */
public class HT_RecipeType {
    private static HT_RecipeType[] cache = new HT_RecipeType[64];

    public final int m_input;
    public final int m_output;

    private HT_RecipeType (int input, int output) {
        this.m_input = input;
        this.m_output = output;
    }

    public static HT_RecipeType newRecipeType (int input, int output) {
        for (HT_RecipeType e : cache) {
            if (input == e.m_input && output == e.m_output) {
                return e;
            }
        }
        return new HT_RecipeType(input, output);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof HT_RecipeType) {
            HT_RecipeType type = (HT_RecipeType) obj;
            return this.m_input == type.m_input && this.m_output == type.m_output;
        }
        return false;
    }

}
