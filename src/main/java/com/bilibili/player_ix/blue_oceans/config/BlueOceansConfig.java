
package com.bilibili.player_ix.blue_oceans.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BlueOceansConfig {
    public static final String DEFAULT = "Default";
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public static ForgeConfigSpec.DoubleValue ALCOHOL_LAMP_DAMAGE;
        public static ForgeConfigSpec.BooleanValue SPAWN_NEO_PLUM;
        public static ForgeConfigSpec SPEC;

        public Common(ForgeConfigSpec.Builder builder) {
            ALCOHOL_LAMP_DAMAGE = builder.comment("The damage amount of alcohol lamps, Default:4.0F")
                    .defineInRange("alcoholLampDamage", 4.0, Float.MIN_VALUE, Float.MAX_VALUE);
            SPAWN_NEO_PLUM = builder.comment("Will neo plums spawn?Default:true").define("SpawnNeoPlum",
                    true);
            SPEC = builder.build();
        }
    }
}
