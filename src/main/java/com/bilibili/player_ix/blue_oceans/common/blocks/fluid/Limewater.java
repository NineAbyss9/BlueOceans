
package com.bilibili.player_ix.blue_oceans.common.blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public abstract class Limewater
extends BaseFlowingFluid {
    private Limewater() {
        super();
    }

    public Fluid getFlowing() {
        return null;
    }

    public Fluid getSource() {
        return null;
    }

    protected boolean canConvertToSource(Level pLevel) {
        return true;
    }

    public Item getBucket() {
        return Items.BUCKET;
    }

    protected boolean canBeReplacedWith(FluidState pState, BlockGetter pLevel, BlockPos pPos, Fluid pFluid,
                                        Direction pDirection) {
        return pDirection == Direction.DOWN && !(pState.getType() instanceof Limewater);
    }

    public void tick(Level pLevel, BlockPos pPos, FluidState pState) {
        super.tick(pLevel, pPos, pState);
        List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos).inflate(1.0));
        if (!list.isEmpty()) {
            list.forEach(livingEntity -> livingEntity.hurt(pLevel.damageSources().onFire(),
                    4.0F));
        }
    }

    protected float getExplosionResistance() {
        return 40F;
    }

    protected BlockState createLegacyBlock(FluidState pState) {
        return null;
    }

    public boolean isSource(FluidState pState) {
        return false;
    }

    public static class Flowing extends Limewater {
    }

    public static class Source extends Limewater {
        public boolean isSource(FluidState pState) {
            return true;
        }

        public int getAmount(FluidState pState) {
            return 8;
        }

        protected Property<?>[] properties() {
            return new Property[] {FALLING};
        }
    }
}
