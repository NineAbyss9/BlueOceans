
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.bilibili.player_ix.blue_oceans.init.BoTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EchoPotion
extends ThrowableItemProjectile {
    public EchoPotion(EntityType<? extends EchoPotion> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EchoPotion(EntityType<? extends EchoPotion> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public EchoPotion(EntityType<? extends EchoPotion> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        this.clearPlumBlocks(pResult.getLocation());
    }

    protected void clearPlumBlocks(Vec3 pos) {
        BlockPos blockPos = BlockPos.containing(pos);
        //for (int j = 0; j < 3;j++) {
            for (int i = 0;i < 3;i++) {
                BlockPos blockPos1 = blockPos.below().offset(i, 0, 0);
                BlockPos blockPos2 = blockPos.below().offset(0, 0, i);
                BlockPos blockPos3 = blockPos.below().offset(i, 0, i);
                BlockPos blockPos4 = blockPos.below().offset(-i, 0, 0);
                BlockPos blockPos5 = blockPos.below().offset(-i, 0, -i);
                BlockPos blockPos6 = blockPos.below().offset(0, 0, -i);
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
            }
        //}
    }

    private boolean checkIsPlum(BlockPos pos) {
        return this.level().getBlockState(pos).is(BoTags.RED_PLUM_BLOCKS);
    }

    protected Item getDefaultItem() {
        return Items.ITEM_FRAME;
    }
}
