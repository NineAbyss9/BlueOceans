
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.NineAbyss9.ix_api.api.mobs.IConversion;
import com.github.NineAbyss9.ix_api.api.mobs.OwnableMob;
import com.github.NineAbyss9.ix_api.util.Colors;
import com.github.NineAbyss9.ix_api.util.Vec9;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.math.AbyssMath;
import org.NineAbyss9.math.MathSupport;

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
        if (this.isInFluid()) {
            this.decreaseConvertTick();
        }
        if (this.getConversionTick() >= 600) {
            this.decreaseConvertTick();
        }
        this.convertTick();
    }

    protected void clientAiStep() {
    }

    public void convertTick() {
        if (!isNoAi())
            IConversion.super.convertTick();
    }

    public void decreaseConvertTick() {
        if (!isNoAi())
            IConversion.super.decreaseConvertTick();
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addConversionSavedData(tag);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readConversionSavedData(tag);
    }

    public void standOnPlumTick() {
        this.decreaseConvertTick();
    }

    private boolean isInFluid() {
        return this.level().getBlockState(blockPosition()).getBlock() instanceof LiquidBlock;
    }

    public boolean shouldAttackOtherMobs() {
        return false;
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

    @Nullable
    public static NeoPlum createRandom(BlockPos pos, Level pLevel) {
        return MathSupport.random.nextFloat() < 0.1F ? NeoFighter.create(pos, pLevel) : create(pos, pLevel);
    }

    @Nullable
    public static NeoPlum createRandom(Vec3 vec3, Level pLevel) {
        return MathSupport.random.nextFloat() < 0.1F ? NeoFighter.create(vec3, pLevel) : create(vec3, pLevel);
    }

    public static void addParticleAroundPlum(Entity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel)entity.level();
            serverLevel.sendParticles(BlueOceansParticleTypes.RED_SPELL.get(), entity.getX(), entity.getY(),
                    entity.getZ(), 10, 0.7, 0.7, 0.7, 0);
        }
    }

    @SuppressWarnings("deprecation")
    public void performConvert() {
        if (!this.level().isClientSide) {
            ServerLevel serverLevel = this.serverLevel();
            AbstractRedPlumMob monster;
            if (this.isEyeInFluid(FluidTags.WATER)) {
                monster = BlueOceansEntities.RED_PLUM_FISH.get().create(serverLevel);
            } else {
                int chance = this.getRandomUtil().nextInt(RedPlumUtil.BASE_PLUM_RANDOM_POOL);
                float f = this.randomUtil.nextFloat();
                if (f > 0.875F && this.level().getEntitiesOfClass(PlumBuilder.class, this.getBoundingBox()
                        .inflate(20)).isEmpty() && this.level().getEntitiesOfClass(RedPlumMonster.class,
                        this.getBoundingBox().inflate(20)).size() < 10)
                    monster = BlueOceansEntities.PLUM_BUILDER.get().create(serverLevel);
                else if (f > 0.75F)
                    monster = BlueOceansEntities.PLUM_SPREADER.get().create(serverLevel);
                else
                    monster = RedPlumUtil.MAP.get(1).get(chance).create(serverLevel);
            }
            if (monster != null) {
                Vec3 pos = this.position();
                monster.moveTo(pos);
                serverLevel.addFreshEntity(monster);
                if (monster instanceof PlumBuilder builder) {
                    if (this.level().getEntitiesOfClass(AbstractRedPlumMob.class, this.getBoundingBox().inflate(10))
                            .size() > 12 ||
                            !this.level().getEntitiesOfClass(PlumBuilder.class, this.getBoundingBox().inflate(12),
                                    builder1 -> !builder1.equals(monster)).isEmpty()) {
                        builder.setTargetPos(pos.add(AbyssMath.random(40), 0,
                                AbyssMath.random(40)));
                    }
                }
            }
            this.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED);
            this.discard();
        }
    }

    protected boolean shouldLevelUp() {
        return false;
    }

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
