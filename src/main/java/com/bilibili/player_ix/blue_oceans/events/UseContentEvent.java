
package com.bilibili.player_ix.blue_oceans.events;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Event.HasResult
@Cancelable
public class UseContentEvent
extends LivingEvent {
    private final Content content;
    public UseContentEvent(LivingEntity pEntity, Content pContent) {
        super(pEntity);
        this.content = pContent;
    }

    public Content getContent() {
        return content;
    }
}
