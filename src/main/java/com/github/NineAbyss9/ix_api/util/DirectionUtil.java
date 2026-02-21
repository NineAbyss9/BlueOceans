
package com.github.NineAbyss9.ix_api.util;

import net.minecraft.core.Direction;

public class DirectionUtil {
    public static boolean isVertical(Direction pFacing) {
        return pFacing == Direction.EAST || pFacing == Direction.NORTH || pFacing == Direction.WEST
                || pFacing == Direction.SOUTH;
    }

    public static boolean isHorizontal(Direction pFacing) {
        return pFacing == Direction.UP || pFacing == Direction.DOWN;
    }
}
