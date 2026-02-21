
package com.bilibili.player_ix.blue_oceans.common.blocks.be.farming;

import com.bilibili.player_ix.blue_oceans.common.blocks.farming.Sprinkler;
import com.github.NineAbyss9.ix_api.util.DirectionUtil;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.NineAbyss9.math.AbyssMath;

public class SprinklerEntity
extends BlockEntity {
    private boolean activated;
    public int workTime;
    public SprinklerEntity(BlockPos pPos, BlockState pBlockState) {
        super(null, pPos, pBlockState);
    }

    public static void severTick(Level pLevel, BlockPos pPos, BlockState pState,
                                 SprinklerEntity pEntity) {
        if (pEntity.isActivated()) {
            pEntity.increaseWorkTime();
            if (pEntity.workTime % 40 == 0) {
                Direction facing = pState.getValue(Sprinkler.FACING);
                tryGrowCrops(pLevel, pPos, facing);
            }
        } else
            pEntity.workTime = 0;
    }

    public static void clientTick(Level pLevel, BlockPos pPos, SprinklerEntity pEntity) {
        if (pEntity.isActivated()) {
            ParticleUtil.addParticle(pLevel, ParticleTypes.FALLING_WATER, pPos,
                    AbyssMath.random(0.8), AbyssMath.random(0.4), AbyssMath.random(0.8));
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.activated = pTag.getBoolean("Activated");
        this.workTime = pTag.getInt("WorkTime");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("Activated", this.activated);
        pTag.putInt("WorkTime", workTime);
    }

    public boolean isActivated() {
        return activated;
    }

    public void increaseWorkTime() {
        ++workTime;
    }

    public static void tryGrowCrops(Level pLevel, BlockPos pPos, Direction pFacing) {
        AABB aabb = DirectionUtil.isHorizontal(pFacing) ? new AABB(pPos).inflate(20, 2, 20)
                : new AABB(pPos).inflate(20);
        for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ),
                Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
            if (pLevel.random.nextFloat() < 0.05F) {
                growCrop(pLevel, pos, pLevel.getBlockState(pos));
            }
        }
    }

    public static void growCrop(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pState.getBlock() instanceof CropBlock block) {
            block.growCrops(pLevel, pPos, pState);
        }
    }
}
