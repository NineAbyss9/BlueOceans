
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.particles.ImpartParticleOption;
import com.mojang.serialization.Codec;
import org.NineAbyss9.annotation.PFMAreNonnullByDefault;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@PFMAreNonnullByDefault
public class BlueOceansParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister
            .create(ForgeRegistries.PARTICLE_TYPES, BlueOceans.MOD_ID);
    public static final RegistryObject<SimpleParticleType> BIG_RED_PLUM_INSTANT_SPELL;
    public static final RegistryObject<SimpleParticleType> BO_SPELL;
    public static final RegistryObject<ParticleType<ImpartParticleOption>> IMPART;
    public static final RegistryObject<SimpleParticleType> RED_PLUM_SPELL;
    public static final RegistryObject<SimpleParticleType> RED_PLUM_INSTANT_SPELL;
    public static final RegistryObject<SimpleParticleType> RED_SPELL;
    public static final RegistryObject<SimpleParticleType> SPARK;
    private BlueOceansParticleTypes() {
    }

    private static Supplier<SimpleParticleType> supplier() {
        return ()-> new SimpleParticleType(false);
    }

    private static RegistryObject<SimpleParticleType> register(String name) {
        return REGISTRY.register(name, supplier());
    }

    public static void addRedSpell(Level level, double px, double py, double pz, double ySpeed) {
        level.addParticle(RED_SPELL.get(), px, py, pz, 0, ySpeed, 0);
    }

    static {
        BIG_RED_PLUM_INSTANT_SPELL = register("big_plum_instant_spell");
        BO_SPELL = register("bo_spell");
        IMPART = REGISTRY.register("impart", ()-> new ParticleType<>(false,
                ImpartParticleOption.DESERIALIZER) {
            public Codec<ImpartParticleOption> codec() {
                return ImpartParticleOption.CODEC;
            }
        });
        RED_PLUM_SPELL = register("red_plum_spell");
        RED_PLUM_INSTANT_SPELL = register("red_plum_instant_spell");
        RED_SPELL = register("red_spell");
        SPARK = register("spark");
    }
}
