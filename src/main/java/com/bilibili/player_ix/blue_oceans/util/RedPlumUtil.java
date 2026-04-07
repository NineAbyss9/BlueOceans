
package com.bilibili.player_ix.blue_oceans.util;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumHuman;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.AbstractHuntingVillager;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansGameRules;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.array.IntArray;
import org.NineAbyss9.array.ObjectArray;
import org.NineAbyss9.math.MathSupport;

import java.util.List;
import java.util.Map;

public class RedPlumUtil {
    /**NeoPlum(NeoFighter) -> {
     * <p> {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumSpreader} ->
     *     {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumFactory}
     * <p> BasePlum(like RedPlumSpider) -> RedPlumSlayer & Red Demon
     * <p> {@linkplain com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumBuilder}
     * <p>
     }*/
    public static final Map<Integer, List<EntityType<? extends AbstractRedPlumMob>>> MAP;
    public static final Map<Integer, List<String>> STRING_MAP;
    public static final ObjectArray<EntityType<?>> PLUM_ENTITY_TYPES;
    public static final IntArray PLUM_PLUS_KILLS;
    public static final int BASE_PLUM_RANDOM_POOL = 5;
    private RedPlumUtil() {
    }

    public static boolean canSpreadPlum(Level pLevel) {
        return pLevel.getGameRules().getBoolean(BlueOceansGameRules.PLUM_SPREAD);
    }

    public static boolean likeVillager(Entity pEntity) {
        return pEntity instanceof AbstractVillager || pEntity instanceof AbstractHuntingVillager
                || pEntity instanceof AbstractIllager;
    }

    public static boolean likeHuman(Entity pEntity) {
        return pEntity instanceof Zombie || pEntity instanceof Player;
    }

    public static void spawnRedPlumHuman(Level pLevel, LivingEntity pEntity) {
        RedPlumHuman human = BlueOceansEntities.RED_PLUM_HUMAN.get().create(pLevel);
        if (human != null) {
            human.moveTo(pEntity.position());
            human.finalizeSpawn((ServerLevelAccessor)pLevel,
                    pLevel.getCurrentDifficultyAt(pEntity.blockPosition()), MobSpawnType.BREEDING);
            pLevel.addFreshEntity(human);
        }
    }

    public static void spawnRedPlumVillager(Level pLevel, LivingEntity pVillager) {
        var villager = BlueOceansEntities.RED_PLUM_VILLAGER.get().create(pLevel);
        if (villager != null) {
            villager.moveTo(pVillager.position());
            villager.finalizeSpawn((ServerLevelAccessor)pLevel,
                    pLevel.getCurrentDifficultyAt(pVillager.blockPosition()), MobSpawnType.BREEDING);
            pLevel.addFreshEntity(villager);
        }
    }

    public static void spawnBase(Level pLevel, Vec3 pos) {
        var list = MAP.get(1);
        if (list != null) {
            var mob = list.get(MathSupport.random.nextInt(BASE_PLUM_RANDOM_POOL)).create(pLevel);
            if (mob != null) {
                mob.moveTo(pos);
                pLevel.addFreshEntity(mob);
            }
        }
    }

    public static MobEffectInstance plumInfection(int pTime, int pLevel) {
        return new MobEffectInstance(BlueOceansMobEffects.PLUM_INFECTION.get(), pTime, pLevel);
    }

    /**Level = 0*/
    public static MobEffectInstance plumInfection() {
        return new MobEffectInstance(BlueOceansMobEffects.PLUM_INFECTION.get());
    }

    private static String bo(String name) {
        return "blue_oceans:" + name;
    }

    static {
        MAP = Map.of(0, List.of(BlueOceansEntities.NEO_PLUM.get(), BlueOceansEntities.NEO_FIGHTER.get()),
                1, List.of(BlueOceansEntities.RED_PLUM_CREEPER.get(),
                        BlueOceansEntities.RED_PLUM_SPIDER.get(), BlueOceansEntities.RED_PLUM_SKELETON.get(),
                        BlueOceansEntities.RED_PLUM_WORM.get(), BlueOceansEntities.RED_PLUMS_COW.get(),
                        BlueOceansEntities.RED_PLUM_VILLAGER.get(), BlueOceansEntities.RED_PLUM_HUMAN.get()),
                2, List.of(BlueOceansEntities.RED_PLUM_SLAYER.get(), BlueOceansEntities.RED_DEMON.get()),
                3, List.of(BlueOceansEntities.PLUM_FACTORY.get(), BlueOceansEntities.PLUM_SPREADER.get(),
                        BlueOceansEntities.PLUM_BUILDER.get()));
        STRING_MAP = Map.of(0, List.of(bo("neo_plum"), bo("neo_fighter")),
                1, List.of(bo("red_plum_creeper"),  bo("red_plum_spider"), bo("red_plum_villager"),
                        bo("red_plum_worm"), bo("red_plum_human"), bo("red_plum_skeleton")),
                2, List.of(bo("red_plum_slayer"), bo("red_demon")));
        PLUM_ENTITY_TYPES = ObjectArray.of(BlueOceansEntities.RED_PLUM_SLAYER.get());
        PLUM_PLUS_KILLS = IntArray.of(3, 10, Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MAX_VALUE);
    }
}
