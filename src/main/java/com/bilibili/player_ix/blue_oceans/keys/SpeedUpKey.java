
package com.bilibili.player_ix.blue_oceans.keys;

import com.bilibili.player_ix.blue_oceans.client.SideHandler;
import com.bilibili.player_ix.blue_oceans.network.BoNetwork;
import com.bilibili.player_ix.blue_oceans.network.packet.SpeedUp;
import com.github.player_ix.ix_api.api.annotation.OnlyInClient;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

@OnlyInClient
public class SpeedUpKey
extends KeyMapping {
    public boolean isDownOld;
    public SpeedUpKey() {
        super("key.blue_oceans.speed_up", GLFW
                .GLFW_KEY_LEFT_CONTROL, "key.categories.movement");
    }

    public void setDown(boolean pValue) {
        super.setDown(pValue);
        if (SideHandler.checkConnection())
            BoNetwork.INSTANCE.sendToServer(new SpeedUp(pValue));
        this.isDownOld = pValue;
    }
}
