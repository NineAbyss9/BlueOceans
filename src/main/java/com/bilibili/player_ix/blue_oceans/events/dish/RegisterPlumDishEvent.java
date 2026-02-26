
package com.bilibili.player_ix.blue_oceans.events.dish;

import com.bilibili.player_ix.blue_oceans.api.potion.PlumDishPotion;
import com.bilibili.player_ix.blue_oceans.api.potion.PlumDishPotions;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RegisterPlumDishEvent
extends Event {
    public RegisterPlumDishEvent() {
        super();
    }

    public void register(String pName, int pId, PlumDishPotion pPotion) {
        PlumDishPotions.register(pName, pId, pPotion);
    }
}
