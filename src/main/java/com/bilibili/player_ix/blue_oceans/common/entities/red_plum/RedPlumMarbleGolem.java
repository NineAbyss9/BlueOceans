
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.api.mob.MobTypes;
import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.boss.MarbleGolem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class RedPlumMarbleGolem
extends MarbleGolem
implements RedPlumMob {
    public RedPlumMarbleGolem(EntityType<? extends MarbleGolem> type, Level level) {
        super(type, level);
    }

    public MobTypes getMobTypes() {
        return MobTypes.HOSTILE;
    }
}
