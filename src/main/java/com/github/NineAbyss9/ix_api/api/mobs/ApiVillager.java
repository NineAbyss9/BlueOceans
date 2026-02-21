
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;

/**A class that copy {@link AbstractVillager}*/
public interface ApiVillager extends Merchant {
    default void rewardTradeXp(MerchantOffer merchantOffer) {
    }

    void updateTrades();

    default SoundEvent getTradeUpdatedSound(boolean pIsYes) {
        return pIsYes ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
    }

    default void restockAll() {
        for (MerchantOffer offer : this.getOffers()) {
            offer.resetUses();
        }
    }
}
