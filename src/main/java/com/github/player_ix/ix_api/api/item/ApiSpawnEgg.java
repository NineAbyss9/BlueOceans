
package com.github.player_ix.ix_api.api.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class ApiSpawnEgg
extends ForgeSpawnEggItem {
    public ApiSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor,
                       Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    public String getDescriptionId() {
        return this.getDefaultType().getDescriptionId();
    }

    public Component getDescription() {
        return Component.translatable("item.blue_oceans.spawn_egg",
                this.getDefaultType().getDescription());
    }
}
