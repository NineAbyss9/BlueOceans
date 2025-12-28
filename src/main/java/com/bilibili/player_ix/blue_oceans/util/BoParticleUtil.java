
package com.bilibili.player_ix.blue_oceans.util;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class BoParticleUtil {
    public static void spark(ServerLevel pLevel, Vec3 pPos, int pCount) {
        pLevel.sendParticles(BlueOceansParticleTypes.SPARK.get(), pPos.x,
                pPos.y, pPos.z, pCount,  0, 0, 0, 0);
    }

    public static void spark(ServerLevel pLevel, Vec3 pPos) {
        spark(pLevel, pPos, 12);
    }
}
