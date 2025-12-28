
package com.bilibili.player_ix.blue_oceans.client.particles;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

@SuppressWarnings("deprecation")
public record ImpartParticleOption(float size, float speed) implements ParticleOptions {
    public static final Codec<ImpartParticleOption> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("size").forGetter(d -> d.size),
                    Codec.FLOAT.fieldOf("speed").forGetter(d -> d.speed)
            ).apply(instance, ImpartParticleOption::new));
    public static final Deserializer<ImpartParticleOption> DESERIALIZER = new Deserializer<>() {
        public ImpartParticleOption fromCommand(ParticleType<ImpartParticleOption> pParticleType, StringReader pReader)
                throws CommandSyntaxException {
            pReader.expect(' ');
            float size = pReader.readFloat();
            pReader.expect(' ');
            float speed = pReader.readFloat();
            return new ImpartParticleOption(size, speed);
        }

        public ImpartParticleOption fromNetwork(ParticleType<ImpartParticleOption> pParticleType, FriendlyByteBuf pBuffer) {
            return new ImpartParticleOption(pBuffer.readFloat(), pBuffer.readFloat());
        }
    };

    public ParticleType<?> getType() {
        return BlueOceansParticleTypes.IMPART.get();
    }

    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(size);
        pBuffer.writeFloat(speed);
    }

    public String writeToString() {
        return String.format(java.util.Locale.ROOT, "%s %.2f %s",
                BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.size, this.speed);
    }
}
