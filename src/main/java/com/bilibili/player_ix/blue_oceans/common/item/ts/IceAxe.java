
package com.bilibili.player_ix.blue_oceans.common.item.ts;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.github.NineAbyss9.ix_api.api.item.ApiAxe;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IceAxe
extends ApiAxe
implements IIceUtil {
    public IceAxe() {
        super(BoTier.ICE, 4, -3.F, new Properties());
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        this.addEffects(pTarget);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
