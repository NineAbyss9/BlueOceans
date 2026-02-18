
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import com.github.player_ix.ix_api.api.item.BaseItem;
import net.minecraft.world.entity.LivingEntity;

public class Brain
extends BaseItem
implements Organ {
    public Brain(Properties pProperties) {
        super(pProperties);
    }

    public Brain() {
        this(new Properties().stacksTo(64));
    }

    public float getPower(LivingEntity pEntity) {
        return 3.0F;
    }
}
