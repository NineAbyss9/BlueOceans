
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.api.mob.TypedMob;
import com.github.player_ix.ix_api.api.mobs.IFlagMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;

public class TypedSpider
extends BoAnimal
implements VariantHolder<TypedSpider.SpiderType>, IAnimatedMob, IFlagMob, TypedMob<TypedSpider.SpiderType> {
    protected static final EntityDataAccessor<Integer> DATA_ATTACK_TICK;
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    protected static final EntityDataAccessor<Integer> DATA_TYPE;
    public TypedSpider(EntityType<? extends TypedSpider> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACK_TICK, 0);
        this.entityData.define(DATA_FLAGS, 0);
        this.entityData.define(DATA_TYPE, 0);
    }

    public void aiStep() {
        super.aiStep();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DoTasksGoal(this));
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public void setVariant(SpiderType pVariant) {
        this.entityData.set(DATA_TYPE, pVariant.id);
    }

    public SpiderType getVariant() {
        return SpiderType.of(this.entityData.get(DATA_TYPE));
    }

    public SpiderType getKind() {
        return getVariant();
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public int getAttackTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAttackTick(int attackTick) {
        this.entityData.set(DATA_ATTACK_TICK, attackTick);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of();
    }

    static {
        DATA_ATTACK_TICK = SynchedEntityData.defineId(TypedSpider.class, EntityDataSerializers.INT);
        DATA_FLAGS = SynchedEntityData.defineId(TypedSpider.class, EntityDataSerializers.INT);
        DATA_TYPE = SynchedEntityData.defineId(TypedSpider.class, EntityDataSerializers.INT);
    }

    public enum SpiderType {
        NORMAL(0, 2),
        JUMPING(1, 1.6);
        public final int id;
        public final double damage;
        SpiderType(int pId, double pDamage) {
            id = pId;
            damage = pDamage;
        }

        public static SpiderType of(int pId) {
            for (SpiderType spiderType : values()) {
                if (pId != spiderType.id)
                    continue;
                return spiderType;
            }
            return NORMAL;
        }
    }

    protected static class DoTasksGoal extends Goal {
        protected final TypedSpider spider;
        public DoTasksGoal(TypedSpider pSpider) {
            this.spider = pSpider;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return spider.isIdle();
        }

        public void tick() {
            super.tick();
        }
    }
}
