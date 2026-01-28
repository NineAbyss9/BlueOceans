
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;
import java.util.Optional;

public interface IMiner {
    @Nullable
    BlockPos targetPos();

    default Optional<BlockPos> targetPosOptional() {
        return Optional.ofNullable(targetPos());
    }

    void setTargetPos(@Nullable BlockPos pPos);
}
