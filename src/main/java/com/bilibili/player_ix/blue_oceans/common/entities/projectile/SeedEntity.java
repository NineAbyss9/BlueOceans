
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SeedEntity
extends Projectile
implements ItemSupplier {
    private final ItemStack seed;
    public SeedEntity(EntityType<? extends SeedEntity> pEntityType, Level pLevel, ItemStack pSeed) {
        super(pEntityType, pLevel);
        this.seed = pSeed;
    }

    public SeedEntity(EntityType<? extends SeedEntity> entityType, Level pLevel) {
        this(entityType, pLevel, new ItemStack(Items.WHEAT_SEEDS));
    }

    public void tick() {
        super.tick();
    }

    protected void defineSynchedData() {
    }

    @SuppressWarnings("deprecation")
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos pos = pResult.getBlockPos().below();
        BlockState blockState = this.level().getBlockState(pResult.getBlockPos().below());
        if (this.seed.getItem() instanceof BlockItem blockItem && blockItem.getBlock()
                .canSurvive(blockState, this.level(), pos)) {
            this.level().setBlock(pResult.getBlockPos(), blockItem.getBlock().defaultBlockState(), 0);
        }
        this.discard();
    }

    public ItemStack getItem() {
        return seed;
    }
}
