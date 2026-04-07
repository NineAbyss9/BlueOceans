
package com.bilibili.player_ix.blue_oceans.common.capability;

/** Hydration / fluid balance; supports recovery from disease when expanded. */
public class DrinkManager
{
    private final LivingHealth health;
    private float hydration = 1.0F;

    public DrinkManager(LivingHealth pHealth)
    {
        this.health = pHealth;
    }

    public float getHydration()
    {
        return this.hydration;
    }

    public void setHydration(float pValue)
    {
        this.hydration = Math.max(0.0F, Math.min(1.0F, pValue));
    }

    public void addHydration(float pDelta)
    {
        this.setHydration(this.hydration + pDelta);
    }

    public void tick()
    {
        if (this.health.owner.level().isClientSide)
        {
            return;
        }
        if (this.health.owner.tickCount % 200 == 0)
        {
            float drain = this.health.hasEffect(LivingEffects.VIRAL_INVASION) ? 0.03F : 0.01F;
            this.addHydration(-drain);
        }
        if (this.hydration < 0.25F && this.health.owner.tickCount % 120 == 0)
        {
            this.health.feelingManager.decrease(0.02F);
        }
    }
}
