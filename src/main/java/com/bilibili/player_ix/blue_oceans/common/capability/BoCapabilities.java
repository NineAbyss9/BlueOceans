
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class BoCapabilities {
    public static final Capability<LivingHealth> LIVING_HEALTH;
    private BoCapabilities()
    {
        throw new AssertionError();
    }

    static {
        LIVING_HEALTH = CapabilityManager.get(new CapabilityToken<LivingHealth>() {});
    }
}
