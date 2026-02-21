
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import com.github.NineAbyss9.ix_api.api.item.BaseItem;
import com.github.NineAbyss9.ix_api.api.mobs.MobUtils;
import net.minecraft.world.entity.LivingEntity;

public class Heart
extends BaseItem
implements Organ {
    public Heart(Properties pProperties) {
        super(pProperties);
    }

    public Heart() {
        super();
    }

    public float getPower(LivingEntity pEntity) {
        return MobUtils.isHalfHealth(pEntity) ? 3.0F : 1.0F;
    }
}
