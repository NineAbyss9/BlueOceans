
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import com.bilibili.player_ix.blue_oceans.util.BoDamageSource;
import com.github.NineAbyss9.ix_api.api.item.BaseItem;
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

    public void onRemove(LivingEntity pEntity)
    {
        pEntity.die(BoDamageSource.importantOrganDeath(pEntity.level()));
    }
}
