
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlueOceansSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BlueOceans.MOD_ID);
    public static final RegistryObject<SoundEvent> BIKE_RUN = register("bike_run", "bike_run");
    public static final RegistryObject<SoundEvent> BULLET_HIT = register("bullet_hit", "bullet_hit");
    public static final RegistryObject<SoundEvent> DUCK_IDLE = register("duck_idle", "duck_idle");
    public static final RegistryObject<SoundEvent> HONK = register("honk", "honk");
    public static final RegistryObject<SoundEvent> RING = register("ring", "ring");
    public static final RegistryObject<SoundEvent> RED_PLUM_GIRL_CAST_SPELL = SOUNDS.register("red_plum_girl_cast_spell", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BlueOceans.MOD_ID, "red_plum_girl_cast_spell")));
    public static final RegistryObject<SoundEvent> RED_PLUM_CULTIST_IDLE = SOUNDS.register("red_plum_cultist_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BlueOceans.MOD_ID, "red_plum_cultist_ambient")));
    public static final RegistryObject<SoundEvent> RED_PLUM_CULTIST_HURT = register("red_plum_cultist_hurt", "red_plum_cultist_hurt");
    public static final RegistryObject<SoundEvent> RED_PLUM_CULTIST_DIES = register("red_plum_cultist_dies", "red_plum_cultist_dies");
    public static final RegistryObject<SoundEvent> RED_PLUM_CULTIST_CAST_SPELL = register("red_plum_cultist_cast_spell", "red_plum_cultist_cast_spell");
    public static final RegistryObject<SoundEvent> RedPlumWormIdle = register("red_plum_worm_ambient", "red_plum_worm_ambient");
    public static final RegistryObject<SoundEvent> RedPlumWormHurt = register("red_plum_worm_hurts", "red_plum_worm_hurt");
    public static final RegistryObject<SoundEvent> RedPlumWormDie = register("red_plum_worm_dies", "red_plum_worm_die");
    public static final RegistryObject<SoundEvent> RedPlumWormStep = register("red_plum_worm_step", "red_plum_worm_step");
    public static final RegistryObject<SoundEvent> PUFF = register("puff", "puff");
    public static final RegistryObject<SoundEvent> SNIPER_RIFLE_FIRE = register("sniper_rifle_fire", "sniper_rifle_fire");

    private static RegistryObject<SoundEvent> register(String name, String location) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(BlueOceans.location(location)));
    }
}
