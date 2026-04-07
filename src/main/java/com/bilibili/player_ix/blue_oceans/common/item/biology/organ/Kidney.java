
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class Kidney
extends Item
implements Organ {
    public static final String BEFORE_DIE_USED = "KidneyUsed";
    public Kidney(Properties pProperties) {
        super(pProperties);
    }

    public Kidney()
    {
        this(new Properties());
    }

    public void beforeDie(ServerLevel pLevel, LivingEntity pEntity) {
        if (!pEntity.getPersistentData().getBoolean(BEFORE_DIE_USED)) {
            pEntity.getPersistentData().putBoolean(BEFORE_DIE_USED, true);
            pEntity.heal(10.0F);
        }
    }

    public float getPower(LivingEntity pEntity) {
        return 1.0F;
    }
}
