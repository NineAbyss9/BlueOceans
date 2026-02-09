
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.mob_effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlueOceansMobEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(
            ForgeRegistries.MOB_EFFECTS, BlueOceans.MOD_ID);
    //MobEffects
    public static final RegistryObject<MobEffect> COMFORTABLE;
    public static final RegistryObject<MobEffect> CONFIGURABLE_DAMAGE_BOOST;
    public static final RegistryObject<MobEffect> PLUM_INFECTION;
    public static final RegistryObject<MobEffect> PLUM_INVADE;
    public static final RegistryObject<MobEffect> STUN;

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
        Potions.POTIONS.register(bus);
    }

    static {
        COMFORTABLE = REGISTER.register("comfortable", Comfortable::new);
        CONFIGURABLE_DAMAGE_BOOST = REGISTER.register("mod_strength", () ->
                new ConfigurableDamageBoost().addAttributeModifier(Attributes.ATTACK_DAMAGE,
                        "F00097C1-83A1-EAC6-6E15-A75AA831B987", 0,
                        AttributeModifier.Operation.ADDITION));
        PLUM_INFECTION = REGISTER.register("plum_infection", PlumInfection::new);
        PLUM_INVADE = REGISTER.register("plum_invade", PlumInvade::new);
        STUN = REGISTER.register("stun", () -> new StunEffect().addAttributeModifier(
                Attributes.MOVEMENT_SPEED, "D55AE9CD-D8B2-3C22-D6A6-B06CAD4276FF",
                0, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    public static class Potions {
        public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS,
                BlueOceans.MOD_ID);
        public static final RegistryObject<Potion> PLUM_INVADE_POTION;

        static  {
            PLUM_INVADE_POTION = POTIONS.register("plum_invade", () -> new Potion(
                    new MobEffectInstance(PLUM_INVADE.get(), 400, 1)));
        }
    }
}
