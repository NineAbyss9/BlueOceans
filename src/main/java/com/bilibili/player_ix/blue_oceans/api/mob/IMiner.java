
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.core.BlockPos;
import org.NineAbyss9.util.Option;

import javax.annotation.Nullable;

public interface IMiner {
    @Nullable
    BlockPos targetPos();

    default Option<BlockPos> targetPosOptional() {
        return Option.ofNullable(targetPos());
    }

    void setTargetPos(@Nullable BlockPos pPos);
}
