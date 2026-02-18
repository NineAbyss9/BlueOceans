
package com.github.player_ix.ix_api.init;

import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

@PAMAreNonnullByDefault
public class ApiTabs {
    public static void addTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ApiItems.TESTER_SPAWN_EGG.get());
        }
    }
}
