
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.ICitizen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

import java.util.List;

public class VillageChief
extends BaseVillager {
    public static final AttributeSupplier ATTRIBUTE_SUPPLIER;
    public VillageChief(EntityType<? extends VillageChief> pType, Level level) {
        super(pType, level);
    }

    public void aiStep() {
        super.aiStep();
        if (this.tickCount % 40 == 0) {
            this.addEffects();
        }
    }

    public void addEffects() {
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(16), e -> {
                    if (e instanceof ICitizen citizen) {
                        return citizen.getGovernment().equals(this.getGovernment());
                    }
                    return false;
                });
        entities.forEach(entity -> {
            if (!entity.hasEffect(MobEffects.DAMAGE_BOOST))
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0));
        });
    }

    public boolean canAttackEvenBaby() {
        return true;
    }

    static {
        ATTRIBUTE_SUPPLIER = createBaseVillagerAttributes().build();
    }
}
