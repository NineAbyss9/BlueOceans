
package com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean;

import com.bilibili.player_ix.blue_oceans.api.mob.Cnidarians;
import com.bilibili.player_ix.blue_oceans.api.mob.TypedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.WaterAnimal;
import com.github.NineAbyss9.ix_api.util.ItemUtil;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.function.Predicate;

public class Jellyfish
extends WaterAnimal
implements TypedMob<Jellyfish.Type>, Cnidarians {
    public static final Predicate<LivingEntity> NOT_JELLYFISH
            = e-> !(e instanceof Jellyfish);
    protected static final EntityDataAccessor<Integer> DATA_TYPE;
    public AnimationState idle = new AnimationState();
    public float xBodyRot;
    public float xBodyRotO;
    public float zBodyRot;
    public float zBodyRotO;
    public float tentacleMovement;
    public float oldTentacleMovement;
    public float tentacleAngle;
    public float oldTentacleAngle;
    private float speed;
    private float tentacleSpeed;
    private float rotateSpeed;
    private float tx;
    private float ty;
    private float tz;
    public Jellyfish(EntityType<? extends Jellyfish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.random.setSeed(this.getId());
        this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE, 0);
    }

    public void tick() {
        super.tick();
        this.tickSting(NOT_JELLYFISH);
    }

    public void aiStep() {
        super.aiStep();
        this.xBodyRotO = this.xBodyRot;
        this.zBodyRotO = this.zBodyRot;
        this.oldTentacleMovement = this.tentacleMovement;
        this.oldTentacleAngle = this.tentacleAngle;
        this.tentacleMovement += this.tentacleSpeed;
        if ((double)this.tentacleMovement > (Math.PI * 2D)) {
            if (this.level().isClientSide) {
                this.tentacleMovement = ((float)Math.PI * 2F);
            } else {
                this.tentacleMovement -= ((float)Math.PI * 2F);
                if (this.random.nextInt(10) == 0) {
                    this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }
                this.level().broadcastEntityEvent(this, (byte)19);
            }
        }
        if (this.isInWaterOrBubble()) {
            if (this.tentacleMovement < (float)Math.PI) {
                float f = this.tentacleMovement / (float)Math.PI;
                this.tentacleAngle = Mth.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
                if ((double)f > 0.75D) {
                    this.speed = 1.0F;
                    this.rotateSpeed = 1.0F;
                } else {
                    this.rotateSpeed *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.speed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }
            if (!this.level().isClientSide) {
                this.setDeltaMovement(this.tx * this.speed, this.ty * this.speed, this.tz * this.speed);
            }
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            this.yBodyRot += (-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI) - this.yBodyRot) * 0.1F;
            this.setYRot(this.yBodyRot);
            this.zBodyRot += (float)Math.PI * this.rotateSpeed * 1.5F;
            this.xBodyRot += (-((float)Mth.atan2(d0, vec3.y)) * (180F / (float)Math.PI) - this.xBodyRot) * 0.1F;
        } else {
            this.tentacleAngle = Mth.abs(Mth.sin(this.tentacleMovement)) * (float)Math.PI * 0.25F;
            if (!this.level().isClientSide) {
                double d1 = this.getDeltaMovement().y;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    d1 = 0.05D * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1);
                } else if (!this.isNoGravity()) {
                    d1 -= 0.08D;
                }
                this.setDeltaMovement(0.0D, d1 * (double)0.98F, 0.0D);
            }
            this.xBodyRot += (-90.0F - this.xBodyRot) * 0.02F;
        }
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(tickCount);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomMoveGoal(this));
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (super.hurt(pSource, pAmount)) {
            Entity entity = pSource.getDirectEntity();
            if (entity instanceof LivingEntity living && ItemUtil.isMainHandEmpty(living)) {
                this.sting(living);
            }
        }
        return false;
    }

    public void sting(LivingEntity pEntity) {
        pEntity.hurt(this.damageSources().cactus(), 1.0F);
        pEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200,
                this.getKind() == Type.Cubozoa ? 1 : 0));
    }

    public void setVariant(Type pVariant) {
        this.entityData.set(DATA_TYPE, pVariant.id);
    }

    public Type getKind() {
        return Type.of(this.entityData.get(DATA_TYPE));
    }

    public Vec3 getSize() {
        return this.getKind().size;
    }

    protected void dropExperience() {
        if (this.lastHurtByPlayer != null)
            super.dropExperience();
    }

    @SuppressWarnings("deprecation")
    public float getLightLevelDependentMagicValue() {
        return 2.4F;
    }

    public void setMovementVector(float pTx, float pTy, float pTz) {
        this.tx = pTx;
        this.ty = pTy;
        this.tz = pTz;
    }

    public boolean hasMovementVector() {
        return this.tx != 0.0F || this.ty != 0.0F || this.tz != 0.0F;
    }

    public static AttributeSupplier createAttributes() {
        return createMobAttributes().add(Attributes.ARMOR, 0.5).add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.2).add(ForgeMod.SWIM_SPEED.get(), 1).build();
    }

    static {
        DATA_TYPE = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.INT);
    }

    public enum Type implements Sized {
        //真水母，伞形
        Scyphozoa(0),
        //箱形水母
        Cubozoa(1),
        //水螅水母
        Hydrozoa(2, new Vec3(1.75, 1, 1.75)),
        //十字水母
        Stauromedusae(3, new Vec3(2, 1, 2));
        public final int id;
        public final Vec3 size;
        Type(int pId, Vec3 pSize) {
            id = pId;
            size = pSize;
        }

        Type(int pId) {
            this(pId, new Vec3(0.7, 1, 0.7));
        }

        public Vec3 size() {
            return size;
        }

        public static Type of(int pId) {
            return switch (pId) {
                case 3 -> Stauromedusae;
                case 1 -> Cubozoa;
                case 2 -> Hydrozoa;
                default -> Scyphozoa;
            };
        }
    }

    public static class RandomMoveGoal extends Goal {
        private final Jellyfish jellyfish;
        public RandomMoveGoal(Jellyfish pMob) {
            this.jellyfish = pMob;
        }

        public boolean canUse() {
            return true;
        }

        public void tick() {
            int i = this.jellyfish.getNoActionTime();
            if (i > 100) {
                this.jellyfish.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.jellyfish.getRandom().nextInt(reducedTickDelay(50)) == 0 || !this.jellyfish.wasTouchingWater
                    || !this.jellyfish.hasMovementVector()) {
                float f = this.jellyfish.getRandom().nextFloat() * Maths.TWO_PI;
                float f1 = Mth.cos(f) * 0.2F;
                float f2 = -0.1F + this.jellyfish.getRandom().nextFloat() * 0.2F;
                float f3 = Mth.sin(f) * 0.2F;
                this.jellyfish.setMovementVector(f1, f2, f3);
            }
        }
    }
}
