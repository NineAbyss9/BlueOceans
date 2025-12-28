
package com.bilibili.player_ix.blue_oceans.common.item.ts;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.github.player_ix.ix_api.api.item.ApiPickaxe;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IcePickaxe
extends ApiPickaxe
implements IIceUtil {
    public IcePickaxe() {
        super(BoTier.ICE, 1, -3.2F, new Properties());
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        this.addEffects(pTarget);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
