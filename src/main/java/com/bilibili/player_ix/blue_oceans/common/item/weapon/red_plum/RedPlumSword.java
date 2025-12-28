
package com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.bilibili.player_ix.blue_oceans.api.item.IPlumItem;
import com.github.player_ix.ix_api.IXApi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class RedPlumSword
extends SwordItem
implements IPlumItem {
    public RedPlumSword() {
        super(BoTier.RED_PLUM, 3, -2.4F, new Properties());
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        this.addPlumEffect(pTarget);
        if (pAttacker.level() instanceof ServerLevel serverLevel)
            IXApi.onRemove(pTarget, serverLevel);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
