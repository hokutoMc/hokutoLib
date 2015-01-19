package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.client.model.HT_ModelPartBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

/**
 * Created by user on 2015/01/05.
 */
public class ModelTestEntity extends ModelBase {
    HT_ModelPartBase part1;
    HT_ModelPartBase part2;

    public ModelTestEntity () {
        super();
        part1 = new HT_ModelPartBase(this);
        part1.addCube(-8.0f, -8.0f, -8.0f, 16, 16, 16, 0.0f);

        part2 = new HT_ModelPartBase(this, part1, 0, 0);
        part2.addCube(0, 2.0f, 0, 16, 16, 16, 0.0f);
    }

    @Override
    public void render (Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
        part1.render(p_78088_7_);
        part2.render(p_78088_7_);
    }
}
