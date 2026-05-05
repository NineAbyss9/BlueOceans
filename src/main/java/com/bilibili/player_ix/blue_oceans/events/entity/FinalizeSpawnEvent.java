
package com.bilibili.player_ix.blue_oceans.events.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.Nullable;

@Cancelable
public class FinalizeSpawnEvent
extends EntityEvent
{
    private final ServerLevelAccessor level;
    private final double x;
    private final double y;
    private final double z;
    private final MobSpawnType spawnType;
    private DifficultyInstance difficulty;
    @Nullable
    private SpawnGroupData spawnData;
    @Nullable
    private CompoundTag spawnTag;
    public FinalizeSpawnEvent(Mob mob, ServerLevelAccessor level, double x, double y, double z,
                              DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData,
                              @Nullable CompoundTag spawnTag)
    {
        super(mob);
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
        this.difficulty = difficulty;
        this.spawnType = spawnType;
        this.spawnData = spawnData;
        this.spawnTag = spawnTag;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }

    public ServerLevelAccessor getLevel()
    {
        return level;
    }

    public Mob getEntity()
    {
        return (Mob)super.getEntity();
    }

    public DifficultyInstance getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(DifficultyInstance inst) {
        this.difficulty = inst;
    }

    public MobSpawnType getSpawnType() {
        return this.spawnType;
    }

    public @Nullable SpawnGroupData getSpawnData() {
        return this.spawnData;
    }

    public void setSpawnData(@Nullable SpawnGroupData data) {
        this.spawnData = data;
    }

    public @Nullable CompoundTag getSpawnTag() {
        return this.spawnTag;
    }

    public void setSpawnTag(@Nullable CompoundTag tag) {
        this.spawnTag = tag;
    }
}
