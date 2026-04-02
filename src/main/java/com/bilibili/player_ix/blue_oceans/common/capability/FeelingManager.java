
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
        this.feelingLevel = 60F;
    }

    public void tick(LivingHealth pHealth)
    {

    }

    public float getFeelingLevel()
    {
        return feelingLevel;
    }

    public void setFeelingLevel(float feelLevelIn)
    {
        this.feelingLevel = feelLevelIn;
    }

    public void increase(float increasement)
    {
        this.feelingLevel += increasement;
    }

    public void decrease(float decreasement)
    {
        this.feelingLevel -= decreasement;
    }

    public Status getStatus()
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
        public static final Feeling HAPPY;
        public static final Feeling SCARED;
        public static final Feeling NERVOUS;
        public Feeling(float feelingLevelIn)
        {
            this.feelingLevel = feelingLevelIn;
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
                return ;
            }
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
