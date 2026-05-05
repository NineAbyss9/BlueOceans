
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import com.bilibili.player_ix.blue_oceans.events.UseContentEvent;
import com.bilibili.player_ix.blue_oceans.events.dish.RegisterPlumDishEvent;
import com.bilibili.player_ix.blue_oceans.events.entity.FinalizeSpawnEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import org.NineAbyss9.value_holder.BooleanValueHolder;
import org.jetbrains.annotations.Nullable;

public class BlueOceansHooks
{
    public static BooleanValueHolder<Content> onUseContent(LivingEntity pEntity, Content pContent)
    {
        UseContentEvent event = new UseContentEvent(pEntity, pContent);
        return new BooleanValueHolder<>(!MinecraftForge.EVENT_BUS.post(event), event.getContent());
    }

    public static boolean onFinalizeSpawn(Mob mob, ServerLevelAccessor level, double x, double y, double z,
                                          DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData,
                                          @Nullable CompoundTag spawnTag)
    {
        var event = new FinalizeSpawnEvent(mob, level, x, y, z, difficulty, spawnType, spawnData,
                spawnTag);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static boolean onFinalizeSpawn(Mob mob, ServerLevelAccessor level, double x, double y, double z,
                                          DifficultyInstance difficulty, MobSpawnType spawnType)
    {
        var event = new FinalizeSpawnEvent(mob, level, x, y, z, difficulty, spawnType, null,
                null);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static boolean onFinalizeSpawn(Mob mob, ServerLevelAccessor level,
                                          DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData,
                                          @Nullable CompoundTag spawnTag)
    {
        var event = new FinalizeSpawnEvent(mob, level, mob.getX(), mob.getY(), mob.getZ(), difficulty, spawnType, spawnData,
                spawnTag);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static boolean onFinalizeSpawn(Mob mob, ServerLevelAccessor level,
                                          DifficultyInstance difficulty, MobSpawnType spawnType)
    {
        var event = new FinalizeSpawnEvent(mob, level, mob.getX(), mob.getY(), mob.getZ(), difficulty, spawnType, null,
                null);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static void onRegisterPlumDishes()
    {
        MinecraftForge.EVENT_BUS.post(new RegisterPlumDishEvent());
    }
}
