
package com.bilibili.player_ix.blue_oceans.util;

import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

@PAMAreNonnullByDefault
public class SummonUtil {
    public SummonUtil() {
    }

    public static void integerSummon(Entity source, Entity mob, int range, MobSpawnType spawnType) {
        Level level = source.level();
        if (!level.isClientSide) {
            ServerLevel server = (ServerLevel)level;
            BlockPos pos =source.blockPosition().offset(Maths.randomInteger(range), 0,
                    Maths.randomInteger(range));
            mob.moveTo(pos, 0, 0);
            if (mob instanceof Mob mob1) {
                nullableFinalizeSpawn(mob1, server, pos, MobSpawnType.MOB_SUMMONED);
            }
            MobUtil.moveToGround(mob);
            server.addFreshEntity(mob);
        }
    }

    public static void finalizeSpawn(Mob mob, ServerLevelAccessor accessor, DifficultyInstance instance,
                                               MobSpawnType spawnType, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        ForgeEventFactory.onFinalizeSpawn(mob, accessor, instance, spawnType, data, tag);
    }

    public static void nullableFinalizeSpawn(Mob mob, ServerLevelAccessor accessor, BlockPos pos,
                                                       MobSpawnType spawnType) {
        finalizeSpawn(mob, accessor, accessor.getCurrentDifficultyAt(pos), spawnType, null, null);
    }
}
