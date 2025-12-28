
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class PlumSpreader
extends RedPlumMonster {
    public PlumSpreader(EntityType<? extends PlumSpreader> type, Level level) {
        super(type, level);
    }

    public void aiStep() {
        super.aiStep();
        if (this.getRandomUtil().nextInt(10) == 0
            && checkConditions(this)) {
            spreadPlum(this);
        }
    }

    public static boolean checkConditions(Entity pEntity) {
        return !pEntity.level().getBlockState(pEntity.blockPosition().below()).is(BoTags.RED_PLUM_BLOCKS)
                && RedPlumUtil.canSpreadPlum(pEntity.level()) && pEntity.fallDistance < 1F;
    }

    public static void spreadPlum(Entity pEntity) {
        pEntity.level().setBlock(pEntity.blockPosition().below(),
                BlueOceansBlocks.RED_PLUM_BLOCK.get().defaultBlockState(), 0);
    }
}
