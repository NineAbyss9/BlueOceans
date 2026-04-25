
package com.bilibili.player_ix.blue_oceans.common.entities.projectile.plum;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.blocks.plum.PlumBlock;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.util.BoDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class EchoPotion
extends ThrowableItemProjectile {
    public EchoPotion(EntityType<? extends EchoPotion> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @SuppressWarnings("unused")
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
            this.transformPlumBlocks();
            this.level().levelEvent(2002, this.blockPosition(), PotionUtils.getColor(Potions.LUCK));
            this.discard();
        }
    }

    protected void hurtPlums() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()
                .inflate(4, 1, 4));
        if (!list.isEmpty()) {
            list.forEach(mob -> {
                if (mob instanceof RedPlumMob)
                    mob.hurt(BoDamageSource.clear(level(), livingOwner(), livingOwner()), 10.0F);
                else if (mob.hasEffect(BlueOceansMobEffects.PLUM_INFECTION.get()) ||
                        mob.hasEffect(BlueOceansMobEffects.PLUM_INVADE.get())) {
                    mob.removeEffect(BlueOceansMobEffects.PLUM_INFECTION.get());
                    mob.removeEffect(BlueOceansMobEffects.PLUM_INVADE.get());
                }
            });
        }
    }

    protected void transformPlumBlocks() {
        AABB aabb = this.getBoundingBox().inflate(4, 2, 4);
        for (BlockPos pos1 : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ),
                Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
            if (checkIsPlum(pos1)) {
                var block = ((PlumBlock)this.level().getBlockState(pos1).getBlock())
                        .getRestoreBlock(this.level(), pos1).defaultBlockState();
                this.level().destroyBlock(pos1, false, this.getOwner());
                this.level().setBlock(pos1, block, 2);
            }
        }
    }

    private LivingEntity livingOwner() {
        return this.getOwner() instanceof LivingEntity living ? living : null;
    }

    private boolean checkIsPlum(BlockPos pos) {
        return this.level().getBlockState(pos).is(BoTags.RED_PLUM_BLOCKS);
    }

    protected Item getDefaultItem() {
        return BlueOceansItems.ECHO_POTION.get();
    }
}
