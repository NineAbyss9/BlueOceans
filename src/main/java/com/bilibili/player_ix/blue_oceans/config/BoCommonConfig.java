package com.bilibili.player_ix.blue_oceans.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

/**learn from
 *<a href="https://github.com/Polarice3/Goety-2/blob/1.20/src/main/java/com/Polarice3/Goety/config/AttributesConfig.java">...</a>*/
public class BoCommonConfig {
    public static final ForgeConfigSpec.DoubleValue ALCOHOL_LAMP_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue COMFORTABLE_HEAL_AMOUNT;
    public static final ForgeConfigSpec.DoubleValue DAMAGE_BOOST_PLUS_VALUE;
    public static final ForgeConfigSpec.IntValue PLUM_INVADE_LEVEL;
    public static final ForgeConfigSpec.BooleanValue SPAWN_NEO_PLUM;
    public static final ForgeConfigSpec SPEC;
    public BoCommonConfig() {
    }

    public static void load(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Common configs");
        builder.push("Common");
        ALCOHOL_LAMP_DAMAGE = builder.comment("The damage amount of alcohol lamps, Default:4.0F")
                .defineInRange("alcoholLampDamage", 4.0, Float.MIN_VALUE, Float.MAX_VALUE);
        COMFORTABLE_HEAL_AMOUNT = builder.defineInRange("ComfortableHealAmount", 0.5, 0,
                Float.MAX_VALUE);
        DAMAGE_BOOST_PLUS_VALUE = builder.defineInRange("ModDamageBoostPlusValue",
                3.0, 0.1, Double.MAX_VALUE);
        builder.pop();

        //Plum
        builder.push("RedPlum");
        PLUM_INVADE_LEVEL = builder.comment("The level of PlumInvade mobs will affect you, Default:3")
                .defineInRange("PlumInvadeLevel", 1, 0, 254);
        SPAWN_NEO_PLUM = builder.comment("Will NeoPlums&NeoFighters spawn?Default:true").define("SpawnNeoPlum",
                true);
        builder.pop();
        builder.pop();
        SPEC = builder.build();
    }
}
