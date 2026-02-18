
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import net.minecraft.world.entity.LivingEntity;

public class PlumHeart
extends Heart {
    public float getPower(LivingEntity pEntity) {
        return super.getPower(pEntity) * 2;
    }
}
