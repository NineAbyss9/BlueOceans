
package com.bilibili.player_ix.blue_oceans.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

public class Impart
extends TextureSheetParticle {
    private final float size;
    private final float speed;
    private final SpriteSet set;
    public Impart(ClientLevel pLevel, double pX, double pY, double pZ, ImpartParticleOption option, SpriteSet pSet) {
        super(pLevel, pX, pY, pZ, 0, 0, 0);
        size = option.finalSize();
        quadSize = option.size();
        speed = option.speed();
        set = pSet;
        pickSprite(set);
    }

    public void tick() {
        if (this.age++ >= this.lifetime) {
            remove();
            return;
        }
        if (this.quadSize < size)
            this.quadSize += speed;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public record Provider(SpriteSet set) implements ParticleProvider<ImpartParticleOption> {
        public Particle createParticle(ImpartParticleOption pType, ClientLevel pLevel, double pX, double pY, double pZ,
                                       double pXSpeed, double pYSpeed, double pZSpeed) {
            return new Impart(pLevel, pX, pY, pZ, pType, set());
        }
    }
}
