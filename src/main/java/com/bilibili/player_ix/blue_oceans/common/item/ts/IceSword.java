
package com.bilibili.player_ix.blue_oceans.common.item.ts;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.github.player_ix.ix_api.api.item.ApiSword;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IceSword
extends ApiSword
implements IIceUtil {
    public IceSword() {
        super(BoTier.ICE, 2, -2.4F, new Properties());
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        this.addEffects(pTarget);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
