
package com.bilibili.player_ix.blue_oceans.util;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansGameRules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import org.NineAbyss9.array.IntArray;
import org.NineAbyss9.array.ObjectArray;

import java.util.List;

public class RedPlumUtil {
    /**NeoPlum(NeoFighter) -> {
     * <p> {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumSpreader} ->
     *     {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumFactory}
     * <p> BasePlum(like RedPlumSpider) -> RedPlumSlayer
     * <p> {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumBuilder}
     * <p>
     }*/
    public static final ImmutableMap<Integer, List<EntityType<? extends AbstractRedPlumMob>>> MAP;
    public static final ObjectArray<EntityType<?>> PLUM_ENTITY_TYPES;
    public static final IntArray PLUM_PLUS_KILLS;
    public static final int BASE_PLUM_RANDOM_POOL = 5;
    private RedPlumUtil() {
    }

    public static boolean canSpreadPlum(Level pLevel) {
        return pLevel.getGameRules().getBoolean(BlueOceansGameRules.PLUM_SPREAD);
    }

    public static void spawnRedPlumHuman(Level pLevel, LivingEntity pEntity) {
        var human = BlueOceansEntities.RED_PLUM_HUMAN.get().create(pLevel);
        if (human != null) {
            human.moveTo(pEntity.position());
            if (!pLevel.addFreshEntity(human))
                human.discard();
        }
    }

    public static void spawnRedPlumVillager(Level pLevel, AbstractVillager pVillager) {
        var villager = BlueOceansEntities.RED_PLUM_VILLAGER.get().create(pLevel);
        if (villager != null) {
            villager.moveTo(pVillager.position());
            if (!pLevel.addFreshEntity(pVillager))
                villager.discard();
        }
    }

    static {
        MAP = ImmutableMap.of(0, List.of(BlueOceansEntities.NEO_PLUM.get(), BlueOceansEntities.NEO_FIGHTER.get()),
                1, List.of(BlueOceansEntities.RED_PLUM_CREEPER.get(),
                        BlueOceansEntities.RED_PLUM_SPIDER.get(), BlueOceansEntities.RED_PLUM_SKELETON.get(),
                        BlueOceansEntities.RED_PLUM_WORM.get(), BlueOceansEntities.RED_PLUMS_COW.get(),
                        BlueOceansEntities.RED_PLUM_VILLAGER.get(), BlueOceansEntities.RED_PLUM_HUMAN.get()),
                2, List.of(BlueOceansEntities.RED_PLUM_SLAYER.get()),
                3, List.of(BlueOceansEntities.PLUM_FACTORY.get(), BlueOceansEntities.PLUM_SPREADER.get(),
                        BlueOceansEntities.PLUM_BUILDER.get()));
        PLUM_ENTITY_TYPES = ObjectArray.of(BlueOceansEntities.RED_PLUM_SLAYER.get());
        PLUM_PLUS_KILLS = IntArray.of(3, 10, Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MAX_VALUE);
    }
}
