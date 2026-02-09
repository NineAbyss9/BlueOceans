
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EchoPotion
extends ThrowableItemProjectile {
    public EchoPotion(EntityType<? extends EchoPotion> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EchoPotion(double pX, double pY, double pZ, Level pLevel) {
        super(BlueOceansEntities.ECHO_POTION.get(), pX, pY, pZ, pLevel);
    }

    public EchoPotion(LivingEntity pShooter, Level pLevel) {
        super(BlueOceansEntities.ECHO_POTION.get(), pShooter, pLevel);
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.hurtPlums();
            this.removePlumBlocks(pResult.getLocation());
            this.level().levelEvent(2002, this.blockPosition(), PotionUtils.getColor(Potions.LUCK));
            this.discard();
        }
    }

    protected void hurtPlums() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()
                .inflate(4, 1, 4));
        if (!list.isEmpty()) {
            list.forEach(mob-> {
                if (mob instanceof RedPlumMob)
                    mob.hurt(this.damageSources().magic(), 10.0F);
                else if (mob.hasEffect(BlueOceansMobEffects.PLUM_INFECTION.get()) ||
                        mob.hasEffect(BlueOceansMobEffects.PLUM_INVADE.get())) {
                    mob.removeEffect(BlueOceansMobEffects.PLUM_INFECTION.get());
                    mob.removeEffect(BlueOceansMobEffects.PLUM_INVADE.get());
                }
            });
        }
    }

    protected void removePlumBlocks(Vec3 pos) {
        BlockPos blockPos = BlockPos.containing(pos);
        for (int j = 0; j < 2;j++) {
            for (int i = 0;i < 3;i++) {
                BlockPos blockPos1 = blockPos.below().offset(i, j, 0);
                BlockPos blockPos2 = blockPos.below().offset(j, 0, i);
                BlockPos blockPos3 = blockPos.below().offset(i, j, i);
                BlockPos blockPos4 = blockPos.below().offset(-i, j, 0);
                BlockPos blockPos5 = blockPos.below().offset(-i, j, -i);
                BlockPos blockPos6 = blockPos.below().offset(0, j, -i);
                BlockPos blockPos7 = blockPos.below().offset(0, j, i);
                if (checkIsPlum(blockPos1))
                    this.level().destroyBlock(blockPos1, false, this.getOwner());
                if (checkIsPlum(blockPos2))
                    this.level().destroyBlock(blockPos2, false, this.getOwner());
                if (checkIsPlum(blockPos3))
                    this.level().destroyBlock(blockPos3, false, this.getOwner());
                if (checkIsPlum(blockPos4))
                    this.level().destroyBlock(blockPos4, false, this.getOwner());
                if (checkIsPlum(blockPos5))
                    this.level().destroyBlock(blockPos5, false, this.getOwner());
                if (checkIsPlum(blockPos6))
                    this.level().destroyBlock(blockPos6, false, this.getOwner());
                if (checkIsPlum(blockPos7))
                    this.level().destroyBlock(blockPos7, false, this.getOwner());
            }
        }
    }

    private boolean checkIsPlum(BlockPos pos) {
        return this.level().getBlockState(pos).is(BoTags.RED_PLUM_BLOCKS);
    }

    protected Item getDefaultItem() {
        return BlueOceansItems.ECHO_POTION.get();
    }
}
