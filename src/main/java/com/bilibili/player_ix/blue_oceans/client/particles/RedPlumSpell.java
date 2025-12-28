
package com.bilibili.player_ix.blue_oceans.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class RedPlumSpell extends TextureSheetParticle {
    private static final RandomSource source = RandomSource.create();
    private final SpriteSet set;

    public RedPlumSpell(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSets) {
        super(world, x, y, z, 0.5 - source.nextDouble(), vy, 0.5 - source.nextDouble());
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.set = spriteSets;
        this.yd *= 0.2;
        if (vx == 0.0 && vz == 0.0) {
            this.xd *= 0.1;
            this.zd *= 0.1;
        }
        this.quadSize *= 1f;
        this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteSets);
        if (this.isCloseToScopingPlayer()) {
            this.setAlpha(0.0F);
        }
    }

    private boolean isCloseToScopingPlayer() {
        Minecraft $$0 = Minecraft.getInstance();
        LocalPlayer $$1 = $$0.player;
        return $$1 != null && $$1.getEyePosition().distanceToSqr(this.x, this.y, this.z) <= 16.0
                && $$0.options.getCameraType().isFirstPerson() && $$1.isScoping();
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.set);
        if (this.isCloseToScopingPlayer()) {
            this.setAlpha(0.0F);
        } else {
            this.setAlpha(Mth.lerp(0.05F, this.alpha, 1.0F));
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class BOSpellProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public BOSpellProvider(SpriteSet pSet) {
            spriteSet = pSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            RedPlumSpell spell = new RedPlumSpell(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            spell.setColor((float)xSpeed, (float)ySpeed, (float)zSpeed);
            return spell;
        }
    }

    public static class RedSpell implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public RedSpell(SpriteSet pSet) {
            spriteSet = pSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            RedPlumSpell spell = new RedPlumSpell(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            spell.setColor(0.4F, 0.0F, 0.0F);
            return spell;
        }
    }

    public static class SpellProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public SpellProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            RedPlumSpell spell = new RedPlumSpell(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            spell.setColor((float)xSpeed, (float)ySpeed, (float)zSpeed);
            spell.quadSize *= Mth.randomBetween(source, 0.5f, 0.75f);
            return spell;
        }
    }

    public static class BigInstantSpellProvider implements  ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public BigInstantSpellProvider(SpriteSet s) {
            this.spriteSet = s;
        }

        public  Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            RedPlumSpell spell = new RedPlumSpell(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            spell.quadSize *= 1.25f;
            spell.setColor((float)xSpeed,(float)ySpeed,(float)zSpeed);
            return spell;
        }
    }

    public static class InstantProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public InstantProvider(SpriteSet p_107805_) {
            this.sprite = p_107805_;
        }

        public Particle createParticle(SimpleParticleType p_107816_, ClientLevel p_107817_, double p_107818_, double p_107819_, double p_107820_, double p_107821_, double p_107822_, double p_107823_) {
            RedPlumSpell spell = new RedPlumSpell(p_107817_, p_107818_, p_107819_, p_107820_, p_107821_, p_107822_, p_107823_, this.sprite);
            spell.quadSize *= Mth.randomBetween(RandomSource.create(), 0.3f, 0.95f);
            spell.setColor((float)p_107821_, (float)p_107822_, (float)p_107823_);
            return spell;
        }
    }
}
