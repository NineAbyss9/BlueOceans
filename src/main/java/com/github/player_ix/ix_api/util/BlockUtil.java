
package com.github.player_ix.ix_api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class BlockUtil {
    public static Vec3 getTruePos(BlockPos pPos) {
        return new Vec3(pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5);
    }
}
