
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

//梭镖
public class Dart
extends AbstractHurtingProjectile {
    private int life;
    private boolean inGround;
    private boolean toxic;
    public Dart(EntityType<? extends Dart> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Dart(EntityType<? extends Dart> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY,
                double pOffsetZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public Dart(EntityType<? extends Dart> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY,
                double pOffsetZ, Level pLevel) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public void tick() {
        super.tick();
        if (!onGround() && !inGround) {
            life = 20;
        }
        if (this.life <= 0) {
            discard();
        }
    }

    public boolean isToxic() {
        return toxic;
    }

    public void setToxic(boolean pToxic) {
        this.toxic = pToxic;
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        inGround = true;
        life = Maths.toTick(20);
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (entity instanceof LivingEntity living && this.isToxic()) {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
        }
    }
}
