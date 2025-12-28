
package com.bilibili.player_ix.blue_oceans.api.potion;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.effect.MobEffectInstance;
import java.util.List;

public class PlumDishPotion {
    private final String name;
    private final ImmutableList<MobEffectInstance> effects;

    public PlumDishPotion(MobEffectInstance... instances) {
        this(null, instances);
    }

    public PlumDishPotion(String p_43484_, MobEffectInstance... instances) {
        this.name = p_43484_;
        this.effects = ImmutableList.copyOf(instances);
    }

    public List<MobEffectInstance> getEffects() {
        return this.effects;
    }

    public boolean hasInstantEffects() {
        if (!this.effects.isEmpty()) {
            for (MobEffectInstance mobeffectinstance : this.effects) {
                if (mobeffectinstance.getEffect().isInstantenous()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getName(String s) {
        return s + this.name;
    }

    public String toString() {
        return "PlumDishPotion{" +
                "name:'" + name + '\'' +
                ", effects:" + effects +
                '}';
    }
}
