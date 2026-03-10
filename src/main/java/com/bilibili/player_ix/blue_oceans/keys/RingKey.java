
package com.bilibili.player_ix.blue_oceans.keys;

import com.bilibili.player_ix.blue_oceans.common.entities.traffic.AbstractTransport;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.github.NineAbyss9.ix_api.api.annotation.OnlyInClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

@OnlyInClient
public class RingKey
extends KeyMapping {
    private boolean isDownOld;
    public RingKey() {
        super("key.blue_oceans.ring", GLFW.GLFW_KEY_R, "key.categories.misc");
    }

    public void setDown(boolean pValue) {
        super.setDown(pValue);
        Player player = Minecraft.getInstance().player;
        if (this.isDownOld != pValue && pValue && player != null) {
            if (player.getVehicle() instanceof AbstractTransport util) {
                SoundEvent soundEvent;
                if (util.getTrafficType() == 1)
                    soundEvent = BlueOceansSounds.HONK.get();
                else
                    soundEvent = BlueOceansSounds.RING.get();
                player.playSound(soundEvent);
            }
        }
        this.isDownOld = pValue;
    }
}
