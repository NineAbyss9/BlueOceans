
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.player_ix.ix_api.api.mobs.IConversion;
import com.github.player_ix.ix_api.api.mobs.OwnableMob;
import com.github.player_ix.ix_api.util.Colors;
import com.github.player_ix.ix_api.util.Vec9;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class NeoPlum
extends RedPlumMonster
implements IConversion {
    private static final EntityDataAccessor<Integer> DATA_CONVERT_TICK;
    public NeoPlum(EntityType<? extends NeoPlum> type, Level level) {
        super(type, level);
        this.xpReward = 1;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CONVERT_TICK, 1200);
    }

    public void aiStep() {
        super.aiStep();
        if (this.getConversionTick() >= 600) {
            this.reduceConvertTick();
        }
        this.convertTick();
    }

    protected void clientAiStep() {
        if (this.isConverting()) {
            this.getClientLevel().addParticle(BlueOceansParticleTypes.RED_SPELL.get(),
                    this.getRandomX(0.8), this.getRandomY(), this.getRandomZ(0.8),
                    0, 0, 0);
        }
    }

    public void standOnPlumTick() {
        this.reduceConvertTick();
    }

    public void registerBehaviors() {
        this.addMoveToPlumBehavior(3, 0.8);
    }

    public float[] getConversionColor() {
        return Colors.DARK_RED;
    }

    protected void registerGoals() {
        OwnableMob.addBehaviorGoals(this, 5, 0.0, 10F,
                false, false);
    }

    public int getConversionTick() {
        return this.entityData.get(DATA_CONVERT_TICK);
    }

    public void setConversionTick(int tick) {
        this.entityData.set(DATA_CONVERT_TICK, tick);
    }

    public boolean isConverting() {
        return this.getConversionTick() < 600;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return 0.25F;
    }

    @Nullable
    public static NeoPlum create(BlockPos pos, Level pLevel) {
        return create(Vec9.of(pos), pLevel);
    }

    @Nullable
    public static NeoPlum create(Vec3 vec3, Level pLevel) {
        var plum = BlueOceansEntities.NEO_PLUM.get().create(pLevel);
        if (plum != null) {
            plum.moveTo(vec3);
            pLevel.addFreshEntity(plum);
        }
        return plum;
    }

    public static void addParticleAroundPlum(Entity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel)entity.level();
            serverLevel.sendParticles(BlueOceansParticleTypes.RED_SPELL.get(), entity.getX(), entity.getY(),
                    entity.getZ(), 12, 0.7, 0.7, 0.7, 0);
        }
    }

    @SuppressWarnings("all")
    public void performConvert() {
        if (!this.level().isClientSide) {
            ServerLevel serverLevel = this.getServerLevel();
            int chance = this.getRandomUtil().nextInt(5);
            AbstractRedPlumMob monster = RedPlumUtil.MAP.get(1).get(chance).create(serverLevel);
            if (monster != null) {
                monster.moveTo(this.position());
                serverLevel.addFreshEntity(monster);
            }
            this.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED);
            this.discard();
        }
    }

    /**NeoPlums never level up.*/
    protected int nextConvertUpNeeds() {
        return 0x7fffffff;
    }

    public int getLevel() {
        return 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    static {
        DATA_CONVERT_TICK = SynchedEntityData.defineId(NeoPlum.class, EntityDataSerializers.INT);
    }
}
