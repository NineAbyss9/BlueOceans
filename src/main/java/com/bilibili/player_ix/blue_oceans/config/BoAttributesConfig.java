
package com.bilibili.player_ix.blue_oceans.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BoAttributesConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.DoubleValue NEO_PLUM_HEALTH;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        NEO_PLUM_HEALTH = builder.comment("Max health value for neo plums & neo fighters.Default:12")
                .defineInRange("NeoPlumHealth", 12d, 1d, Double.MAX_VALUE);
        SPEC = builder.build();
    }
}
