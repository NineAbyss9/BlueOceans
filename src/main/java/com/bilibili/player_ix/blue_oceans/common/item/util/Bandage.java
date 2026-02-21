
package com.bilibili.player_ix.blue_oceans.common.item.util;

import com.github.NineAbyss9.ix_api.api.item.UseItem;
import com.github.NineAbyss9.ix_api.util.ItemUtil;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Rarity;

public class Bandage
extends UseItem {
    public Bandage() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(16), (
                pLevel, pPlayer, pUsedHand) -> {
            if (pPlayer.getHealth() < pPlayer.getMaxHealth()) {
                pPlayer.heal(pPlayer.getMaxHealth() / 4);
                ItemUtil.shrink(pPlayer.getItemInHand(pUsedHand), pPlayer);
                if (!pLevel.isClientSide) {
                    ParticleUtil.addParticleAroundSelf(pPlayer, ParticleTypes.HEART, 12);
                }
            }
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        });
    }
}
