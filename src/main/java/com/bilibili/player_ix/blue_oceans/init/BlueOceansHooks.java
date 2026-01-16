
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.events.HandleConfigValueEvent;
import net.minecraftforge.common.MinecraftForge;

public class BlueOceansHooks {
    public static void onHandleConfigValue() {
        MinecraftForge.EVENT_BUS.post(new HandleConfigValueEvent());
    }
}
