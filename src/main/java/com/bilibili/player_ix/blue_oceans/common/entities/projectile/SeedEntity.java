
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
    protected static final EntityDataAccessor<ItemStack> DATA_SEED;
    public SeedEntity(EntityType<? extends SeedEntity> pEntityType, Level pLevel, ItemStack pSeed) {
        super(pEntityType, pLevel);
        this.setSeed(pSeed);
    }

    public SeedEntity(EntityType<? extends SeedEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_SEED, new ItemStack(Items.WHEAT_SEEDS));
    }

    public void tick() {
        super.tick();
    }

    @SuppressWarnings("deprecation")
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos pos = pResult.getBlockPos().below();
        BlockState blockState = this.level().getBlockState(pResult.getBlockPos().below());
        if (this.getItem().getItem() instanceof BlockItem blockItem && blockItem.getBlock()
                .canSurvive(blockState, this.level(), pos)) {
            this.level().setBlock(pResult.getBlockPos(), blockItem.getBlock().defaultBlockState(), 0);
        }
        this.discard();
    }

    public ItemStack getItem() {
        return entityData.get(DATA_SEED);
    }

    public void setSeed(ItemStack pSeed) {
        this.entityData.set(DATA_SEED, pSeed);
    }

    static {
        DATA_SEED = SynchedEntityData.defineId(SeedEntity.class, EntityDataSerializers.ITEM_STACK);
    }
}
