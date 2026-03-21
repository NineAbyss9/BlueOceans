
package com.bilibili.player_ix.blue_oceans.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class BloodParticle
extends TextureSheetParticle
{
    private final SpriteSet set;

    protected BloodParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed
            , SpriteSet spriteSets)
    {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.999f;
        this.gravity = 0.75f;
        this.speedUpWhenYMotionIsBlocked = false;
        this.set = spriteSets;
        this.hasPhysics = true;
        this.xd *= 0.800000011920929;
        this.yd *= 0.800000011920929;
        this.zd *= 0.800000011920929;
        this.yd = this.random.nextFloat() * 0.4F + 0.05F;
        this.quadSize *= this.random.nextFloat() * 2.0F + 0.2F;
        this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.setSpriteFromAge(spriteSets);
    }

    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public float getQuadSize(float pScaleFactor)
    {
        float $$1 = ((float)this.age + pScaleFactor) / (float)this.lifetime;
        return this.quadSize * (1.0F - $$1 * $$1);
    }

    public void tick()
    {
        super.tick();
        this.setSpriteFromAge(this.set);
        if (this.onGround) {
            this.remove();
        }
    }

    public record Provider(SpriteSet set) implements ParticleProvider<SimpleParticleType>
    {
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ,
                                                 double pXSpeed, double pYSpeed, double pZSpeed)
        {
            return new BloodParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, set);
        }
    }
}
