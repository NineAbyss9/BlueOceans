
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class FeelingManager
{
    private LivingEntity owner;
    private Status status;
    /**The bigger, the better.*/
    private float feelingLevel;
    public FeelingManager(LivingHealth pHealth)
    {
        this.owner = pHealth.owner;
        this.status = Status.GOOD;
        this.feelingLevel = 60.0F;
    }

    public void tick(LivingHealth pHealth)
    {
    }

    /** Chronic infection drags mood; cleared gradually when healthy. */
    public void applyDiseaseMood(LivingHealth pHealth)
    {
        if (this.owner.tickCount % 20 != 0)
        {
            return;
        }
        if (pHealth.hasEffect(LivingEffects.VIRAL_INVASION))
        {
            LivingEffectInstance v = pHealth.getEffect(LivingEffects.VIRAL_INVASION);
            int amp = v == null ? 0 : v.getAmplifier();
            this.decrease(0.015F + 0.01F * amp);
        }
        else if (pHealth.hasEffect(LivingEffects.ILL))
        {
            this.decrease(0.008F);
        }
        if (pHealth.hasEffect(LivingEffects.IMMUNE_RESPONSE) && !pHealth.hasEffect(LivingEffects.VIRAL_INVASION))
        {
            this.increase(0.01F);
        }
    }

    public float getFeelingLevel()
    {
        return feelingLevel;
    }

    public void setFeelingLevel(float feelingLevelIn)
    {
        this.feelingLevel = feelingLevelIn;
        if (this.status == this.getStatusByFeelingLevel()) return;
        this.status = this.getStatusByFeelingLevel();
    }

    public void increase(float increasement)
    {
        this.setFeelingLevel(this.getFeelingLevel() + increasement);
    }

    public void decrease(float decreasement)
    {
        this.setFeelingLevel(this.getFeelingLevel() - decreasement);
    }

    public Status getStatusByFeelingLevel()
    {
        if (this.feelingLevel < 10F) return Status.WANT_TO_GO_DIE;
        else if (this.feelingLevel < 30F) return Status.BAD;
        else if (this.feelingLevel < 40F) return Status.NOT_BAD;
        else if (this.feelingLevel > 70F) return Status.VERY_GOOD;
        return Status.GOOD;
    }

    public static class Feeling
    {
        private float feelingLevel;
        private Operation operation;
        public static final Feeling HAPPY;
        public static final Feeling SCARED;
        public static final Feeling NERVOUS;
        public static final Feeling ANGRY;
        public Feeling(float feelingLevelIn)
        {
            this.feelingLevel = feelingLevelIn;
        }

        public Feeling operation(final Operation pOp)
        {
            this.operation = pOp;
            return this;
        }

        public Operation getOperation()
        {
            return operation;
        }

        public Feeling toAvoid()
        {
            if (this == HAPPY)
            {
                return SCARED;
            } else if (this == SCARED)
            {
                return HAPPY;
            } else if (this == NERVOUS)
            {
                return null;
            }
            return null;
        }

        public boolean equals(Object o)
        {
            if (!(o instanceof Feeling feeling)) return false;
            return Float.compare(feelingLevel, feeling.feelingLevel) == 0;
        }

        public int hashCode()
        {
            return Objects.hashCode(feelingLevel);
        }

        public enum Operation
        {
            ADD,
            MULTIPLY;
        }

        static
        {
            HAPPY = new Feeling(70.0F);
            SCARED = new Feeling(30.0F);
            NERVOUS = new Feeling(-10.0F);
            ANGRY = new Feeling(-30.0F);
        }
    }

    public enum Status
    {
        WANT_TO_GO_DIE(4),
        BAD(1),
        NOT_BAD(2),
        GOOD(0),
        VERY_GOOD(3);
        private final int id;
        private Status(int pId)
        {
            id = pId;
        }

        public static Status byId(int pId)
        {
            return switch (pId)
            {
                case 1 -> BAD;
                case 2 -> NOT_BAD;
                case 3 -> VERY_GOOD;
                case 4 -> WANT_TO_GO_DIE;
                default -> GOOD;
            };
        }

        public int getStatusId()
        {
            return id;
        }
    }
}
