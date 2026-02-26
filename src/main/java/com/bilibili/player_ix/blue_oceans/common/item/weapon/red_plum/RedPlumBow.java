
package com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.plum.RedPlumArrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Rarity;

public class RedPlumBow
extends BowItem {
    public RedPlumBow() {
        super(new Properties().durability(1999).rarity(Rarity.UNCOMMON));
    }

    public AbstractArrow customArrow(AbstractArrow arrow) {
        RedPlumArrow newArrow = null;
        newArrow.setCustomName(arrow.getCustomName());
        newArrow.setKnockback(arrow.getKnockback());
        newArrow.setBaseDamage(arrow.getBaseDamage());
        newArrow.setCritArrow(arrow.isCritArrow());
        return super.customArrow(arrow);
    }
}
