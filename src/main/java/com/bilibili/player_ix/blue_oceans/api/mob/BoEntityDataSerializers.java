
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class BoEntityDataSerializers {
    public static final EntityDataSerializer<CompletelyPerverseState> COMPLETELY_PERVERSE_STATE;
    public static final EntityDataSerializer<HemimetabolousState> HEMIMETABOLOUS_STATE;

    static {
        COMPLETELY_PERVERSE_STATE = EntityDataSerializer.simpleEnum(CompletelyPerverseState.class);
        HEMIMETABOLOUS_STATE = EntityDataSerializer.simpleEnum(HemimetabolousState.class);
        EntityDataSerializers.registerSerializer(COMPLETELY_PERVERSE_STATE);
        EntityDataSerializers.registerSerializer(HEMIMETABOLOUS_STATE);
    }
}
