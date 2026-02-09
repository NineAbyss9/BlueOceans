
package com.bilibili.player_ix.blue_oceans.common.entities.animal.aquatic;

import com.bilibili.player_ix.blue_oceans.api.mob.TypedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.WaterAnimal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class Jellyfish
extends WaterAnimal
implements TypedMob<Jellyfish.Type> {
    public static final Predicate<LivingEntity> NOT_JELLYFISH
            = e->!(e instanceof Jellyfish);
    protected static final EntityDataAccessor<Integer> DATA_TYPE;
    public Jellyfish(EntityType<? extends Jellyfish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE, 0);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(
                    this.getSize().x, this.getSize().y, this.getSize().z), NOT_JELLYFISH);
            if (!entities.isEmpty())
                entities.forEach(this::sting);
        }
    }

    public void sting(LivingEntity pEntity) {
        pEntity.addEffect(new MobEffectInstance(MobEffects.POISON));
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
            this(pId, new Vec3(1, 2, 1));
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
}
