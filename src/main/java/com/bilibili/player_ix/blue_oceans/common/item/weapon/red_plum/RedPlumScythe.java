
package com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.bilibili.player_ix.blue_oceans.api.item.IPlumItem;
import com.bilibili.player_ix.blue_oceans.common.item.farming.ScytheItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class RedPlumScythe
extends ScytheItem
implements IPlumItem {
    public RedPlumScythe() {
        super(4, -2.0F, BoTier.RED_PLUM, new Properties().rarity(Rarity.UNCOMMON));
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        this.addPlumEffect(pTarget, 2);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
