
package com.github.NineAbyss9.ix_api.api.mobs.ai.goal;

import com.github.NineAbyss9.ix_api.api.mobs.MobUtils;
import com.github.NineAbyss9.ix_api.api.mobs.Ownable;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class ApiOwnerTargetGoal extends TargetGoal {
    protected final Class<LivingEntity> targetType;
    private final TargetingConditions conditions;
    @Nullable
    protected LivingEntity target;
    public ApiOwnerTargetGoal(Mob ownable) {
        super(ownable, false, false);
        this.targetType = LivingEntity.class;
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.conditions = TargetingConditions.forCombat().range(this.getFollowDistance())
                .selector(ApiOwnerTargetGoal.target(ownable));
    }

    public void start() {
        super.start();
        if (this.mob instanceof Ownable ownable) {
            LivingEntity o = ownable.getOwner();
            if (o instanceof Mob m) {
                LivingEntity lie = m.getTarget();
                if (lie != null) {
                    this.mob.setTarget(lie);
                    ((Ownable) this.mob).setTargetByOwner();
                }
            } else {
                throw new IllegalArgumentException(this.toString());
            }
        } else {
            throw new IllegalArgumentException(this.toString());
        }
    }

    public boolean canUse() {
        if (this.mob instanceof Ownable ownable) {
            LivingEntity owner = ownable.getOwner();
            if (!ownable.wouldHaveOwner()) {
                return false;
            }
            if (owner == null) {
                return false;
            }
            if (!(owner instanceof Mob)) {
                return false;
            }
            Mob mobOwner = (Mob)ownable;
            if (mobOwner.getTarget() == null) {
                return false;
            }
            return this.mob.canAttack(mobOwner.getTarget());
        }
        return false;
    }

    protected double getFollowDistance() {
        return Maths.square(300);
    }

    public boolean target() {
        this.findTarget();
        return this.target != null;
    }

    protected AABB getTargetSearchArea(double p_26069_) {
        return this.mob.getBoundingBox().inflate(p_26069_, 4.0, p_26069_);
    }

    protected void findTarget() {
        this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class,
                this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> true),
                this.conditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    public static Predicate<LivingEntity> target(LivingEntity lie) {
        return target -> MobUtils.canHurt(target, lie);
    }
}
