
package com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.plum.RedPlumArrow;
import com.bilibili.player_ix.blue_oceans.init.data.ModItemModelProvider;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Rarity;

public class RedPlumBow
extends BowItem
implements ModItemModelProvider.Handed
{
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
