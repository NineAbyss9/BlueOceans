
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ConfigurableDamageBoost
extends MobEffect {
    public static double value = 3.0D;
    public ConfigurableDamageBoost() {
        super(MobEffectCategory.BENEFICIAL, 16762624);
    }

    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        return BoCommonConfig.DAMAGE_BOOST_PLUS_VALUE.get() * (pAmplifier + 1);
    }
}
