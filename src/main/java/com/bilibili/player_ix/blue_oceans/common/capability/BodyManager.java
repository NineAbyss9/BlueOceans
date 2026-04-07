package com.bilibili.player_ix.blue_oceans.common.capability;

import com.bilibili.player_ix.blue_oceans.common.entities.Virus;
import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.Organ;
import org.NineAbyss9.util.lister.Lister;
import org.NineAbyss9.util.lister.SubLister;

public class BodyManager
{
    private Lister<Organ> activeOrgans;
    private final LivingHealth health;

    public BodyManager(LivingHealth pHealth)
    {
        this.health = pHealth;
        this.activeOrgans = SubLister.of();
    }

    public void tick()
    {
        // Tick all active organs
        activeOrgans.ifNotEmpty(organ -> {
            organ.randomTick(this.health.owner.level(), this.health.owner);
            organ.applyBuff(this.health.owner);
        });

        if (this.health.hasEffect(LivingEffects.VIRAL_INVASION)) {
            LivingEffectInstance instance = this.health.getEffect(LivingEffects.VIRAL_INVASION);
            if (instance != null)
            {
                Virus.VirusType virusType = Virus.VirusType.byId(instance.getAmplifier());
                if (this.health.owner.tickCount % virusType.getOrganDamageInterval() == 0) {
                    // During viral invasion, target the specific organ or randomly damage/remove an organ
                    if (!activeOrgans.isEmpty()) {
                        Organ infectedOrgan = null;
                        Class<? extends Organ> targetClass = virusType.getTargetOrgan();
                        for (Organ organ : activeOrgans) {
                            if (targetClass.isInstance(organ)) {
                                infectedOrgan = organ;
                                break;
                            }
                        }
                        if (infectedOrgan == null) {
                            // No target organ, pick random
                            infectedOrgan = activeOrgans.sample();
                        }
                        this.removeOrgan(infectedOrgan);
                    }
                }
            }
        }
    }

    public Lister<Organ> getActiveOrgans()
    {
        return activeOrgans;
    }

    public void addOrgan(Organ organ)
    {
        if (organ != null)
        {
            activeOrgans.add(organ);
        }
    }

    public void removeOrgan(Organ organ)
    {
        activeOrgans.remove(organ);
        organ.onRemove(this.health.owner);
    }
}
