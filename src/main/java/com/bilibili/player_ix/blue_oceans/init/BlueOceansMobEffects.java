
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.mob_effect.ConfigurableDamageBoost;
import com.bilibili.player_ix.blue_oceans.common.mob_effect.PlumInvade;
import com.bilibili.player_ix.blue_oceans.common.mob_effect.StunEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlueOceansMobEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(
            ForgeRegistries.MOB_EFFECTS, BlueOceans.MOD_ID);
    public static final RegistryObject<MobEffect> CONFIGURABLE_DAMAGE_BOOST;
    public static final RegistryObject<MobEffect> PLUM_INVADE;
    public static final RegistryObject<MobEffect> STUN;

    static {
        CONFIGURABLE_DAMAGE_BOOST = REGISTER.register("mod_strength", () ->
                new ConfigurableDamageBoost().addAttributeModifier(Attributes.ATTACK_DAMAGE,
                        "F00097C1-83A1-EAC6-6E15-A75AA831B987", 0,
                        AttributeModifier.Operation.ADDITION));
        PLUM_INVADE = REGISTER.register("plum_invade", PlumInvade::new);
        STUN = REGISTER.register("stun", () -> new StunEffect().addAttributeModifier(
                Attributes.MOVEMENT_SPEED, "D55AE9CD-D8B2-3C22-D6A6-B06CAD4276FF",
                0, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
