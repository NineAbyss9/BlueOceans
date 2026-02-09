
package com.bilibili.player_ix.blue_oceans.util;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansGameRules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.nine_abyss.array.IntArray;
import org.nine_abyss.array.ObjectArray;

import java.util.List;

public class RedPlumUtil {
    /**NeoPlum(NeoFighter) -> {
     * <p> {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumSpreader} ->
     *     {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumFactory}
     * <p> BasePlum(like RedPlumZombie) -> RedPlumSlayer
     * <p> {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumBuilder}
     * <p>
     }*/
    public static final ImmutableMap<Integer, List<EntityType<? extends AbstractRedPlumMob>>> MAP;
    public static final ObjectArray<EntityType<?>> PLUM_ENTITY_TYPES;
    public static final IntArray PLUM_PLUS_KILLS;
    public RedPlumUtil() {
    }

    public static boolean canSpreadPlum(Level pLevel) {
        return pLevel.getGameRules().getBoolean(BlueOceansGameRules.PLUM_SPREAD);
    }

    static {
        MAP = ImmutableMap.of(0, List.of(BlueOceansEntities.NEO_PLUM.get(), BlueOceansEntities.NEO_FIGHTER.get()),
                1, List.of(BlueOceansEntities.RED_PLUM_HUMAN.get(), BlueOceansEntities.RED_PLUM_CREEPER.get(),
                BlueOceansEntities.RED_PLUM_SPIDER.get(), BlueOceansEntities.RED_PLUM_SKELETON.get(),
                BlueOceansEntities.RED_PLUM_WORM.get(), BlueOceansEntities.RED_PLUMS_COW.get()),
                2, List.of(BlueOceansEntities.RED_PLUM_SLAYER.get()),
                3, List.of(BlueOceansEntities.PLUM_FACTORY.get(), BlueOceansEntities.PLUM_SPREADER.get(),
                        BlueOceansEntities.PLUM_BUILDER.get()));
        PLUM_ENTITY_TYPES = ObjectArray.of(BlueOceansEntities.RED_PLUM_SLAYER.get());
        PLUM_PLUS_KILLS = IntArray.of(3, 10, Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MAX_VALUE);
    }
}
