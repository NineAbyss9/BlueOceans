
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BoBlockProperties {
    public static final IntegerProperty GROWTH_AGE = IntegerProperty.create("age", 0, 600);
    public static final BooleanProperty COVERED = BooleanProperty.create("covered");
    public static final IntegerProperty CAPACITY = IntegerProperty.create("capacity",
            0, 8);
    public static final BooleanProperty BURNING = BooleanProperty.create("burning");
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty END = BooleanProperty.create("end");
}
