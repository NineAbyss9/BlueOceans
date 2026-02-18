
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import com.bilibili.player_ix.blue_oceans.events.OnUseContentEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import org.NineAbyss9.value_holder.BooleanValueHolder;

public class BlueOceansHooks {
    public static BooleanValueHolder<Content> onUseContent(LivingEntity pEntity, Content pContent) {
        OnUseContentEvent event = new OnUseContentEvent(pEntity, pContent);
        return new BooleanValueHolder<>(MinecraftForge.EVENT_BUS.post(event), event.getContent());
    }
}
