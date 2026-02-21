
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import com.github.NineAbyss9.ix_api.api.item.BaseItem;
import net.minecraft.world.entity.LivingEntity;

public class Lung extends BaseItem implements Organ {
    public Lung(Properties pP) {
        super(pP);
    }

    public Lung() {
        super();
    }

    public float getPower(LivingEntity pEntity) {
        return 0.0F;
    }
}
