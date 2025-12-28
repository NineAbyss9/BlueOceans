
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

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
                this.level().destroyBlock(blockPos.below().offset(i, 0, 0), false, this.getOwner());
                this.level().destroyBlock(blockPos.below().offset(0, 0, i), false, this.getOwner());
                this.level().destroyBlock(blockPos.below().offset(i, 0, i), false, this.getOwner());
                this.level().destroyBlock(blockPos.below().offset(-i, 0, 0), false, this.getOwner());
                this.level().destroyBlock(blockPos.below().offset(-i, 0, -i), false, this.getOwner());
                this.level().destroyBlock(blockPos.below().offset(0, 0, -i), false, this.getOwner());
            }
        //}
    }

    protected Item getDefaultItem() {
        return Items.ITEM_FRAME;
    }
}
