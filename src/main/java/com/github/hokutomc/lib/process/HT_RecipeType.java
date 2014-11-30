package com.github.hokutomc.lib.process;

/**
 * Created by user on 2014/11/08.
 */
public class HT_RecipeType {
    private static HT_RecipeType[] cache = new HT_RecipeType[64];

    private final int input;
    private final int output;

    private HT_RecipeType (int input, int output) {
        this.input = input;
        this.output = output;
    }

    public static HT_RecipeType HT_newRecipeType (int input, int output) {
        for (HT_RecipeType e : cache) {
            if (input == e.input && output == e.output) {
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
            return this.input == type.input && this.output == type.output;
        }
        return false;
    }

    public int HT_getInputSize () {
        return this.input;
    }

    public int HT_getOutputSize () {
        return this.output;
    }
}
