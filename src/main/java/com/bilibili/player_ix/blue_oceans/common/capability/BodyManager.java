
package com.bilibili.player_ix.blue_oceans.common.capability;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.Virus;
import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.*;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.world.entity.MobType;
import org.NineAbyss9.util.lister.Lister;
import org.NineAbyss9.util.lister.SubLister;

import java.util.List;

public class BodyManager
{
    private final Lister<Organ> activeOrgans;
    private final OrganHealth organHealth;
    private final LivingHealth health;
    public BodyManager(LivingHealth pHealth)
    {
        this.health = pHealth;
        this.organHealth = new OrganHealth();
        this.activeOrgans = SubLister.<Organ>of();
        this.initialize();
    }

    public void tick()
    {
        // Tick all active organs
        if (this.health.owner.getMobType() == MobType.UNDEAD) return;
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
                        //Organ infectedOrgan = null;
                        Class<? extends Organ> targetClass = virusType.getTargetOrgan();
                        for (Organ organ : activeOrgans) {
                            if (targetClass.isInstance(organ)) {
                                organ.takeDamage(virusType.getDamageMultiplier());
                                this.takeDamage(organ);
                                if (this.organHealth.shouldBeRemoved(organ)) {
                                    this.removeOrgan(organ);
                                }
                            }
                        }
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

    public void takeDamage(Organ organ) {
        organ.damageOwner(this.health.owner);
    }

    @SuppressWarnings("deprecation")
    public void removeOrgan(Organ organ)
    {
        organ.onRemove(this.health.owner);
        activeOrgans.remove(organ);
        this.organHealth.remove(organ);
    }

    public void initialize() {
        if (this.health.owner instanceof RedPlumMob) {
            this.activeOrgans.addAll(List.of(new PlumBrain(), new PlumLung(), new PlumHeart()));
        } else {
            if (this.health.owner.getMobType() == MobType.UNDEAD) return;
            this.activeOrgans.addAll(List.of(new Brain(), new Lung(), new Heart(), new Kidney()));
        }
    }

    private static class OrganHealth extends Object2FloatOpenHashMap<Organ> {
        @java.io.Serial
        private static final long serialVersionUID = 2532496322588640425L;

        public boolean shouldBeRemoved(Organ organ) {
            return this.getFloat(organ) <= 0.0F;
        }
    }
}
