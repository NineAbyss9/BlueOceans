
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior;

import org.nine_abyss.util.IXUtil;
import org.nine_abyss.util.IXUtilUser;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;

import java.util.EnumSet;

public class MoveToBlockBehavior
extends Behavior
implements IXUtilUser {
    protected final PathfinderMob mob;
    public final double speedModifier;
    /** Controls task execution delay */
    protected int nextStartTick;
    protected int tryTicks;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos blockPos = BlockPos.ZERO;
    private boolean reachedTarget;
    private final int searchRange;
    private final int verticalSearchRange;
    protected int verticalSearchStart;
    protected final TagKey<Block> tag;

    public MoveToBlockBehavior(PathfinderMob pMob, double pSpeedModifier, int pSearchRange,
                               TagKey<Block> pTagKey) {
        this(pMob, pSpeedModifier, pSearchRange, 2, pTagKey);
    }

    public MoveToBlockBehavior(PathfinderMob pMob, double pSpeedModifier, int pSearchRange,
                               int pVerticalSearchRange, TagKey<Block> pTag) {
        this.mob = pMob;
        this.speedModifier = pSpeedModifier;
        this.searchRange = pSearchRange;
        this.verticalSearchStart = 0;
        this.verticalSearchRange = pVerticalSearchRange;
        this.tag = pTag;
        this.setFlags(EnumSet.of(BehaviorFlag.MOVE, BehaviorFlag.JUMP));
    }

    public boolean canUse() {
        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            return this.findNearestBlock();
        }
    }

    protected int nextStartTick(PathfinderMob pCreature) {
        return reducedTickDelay(200 + pCreature.getRandom().nextInt(200));
    }

    public boolean canContinueToUse() {
        return this.tryTicks >= -this.maxStayTicks && this.tryTicks <= 1200 && this.isValidTarget(this.mob.level(),
                this.blockPos);
    }

    public void start() {
        this.moveMobToBlock();
        this.tryTicks = 0;
        this.maxStayTicks = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200)
                + 1200) + 1200;
    }

    protected void moveMobToBlock() {
        this.mob.getNavigation().moveTo(this.blockPos.getX() + 0.5,
                this.blockPos.getY() + 1, this.blockPos.getZ() + 0.5, this.speedModifier);
    }

    public double acceptedDistance() {
        return 1.0D;
    }

    protected BlockPos getMoveToTarget() {
        return this.blockPos.above();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        BlockPos blockpos = this.getMoveToTarget();
        if (!blockpos.closerToCenterThan(this.mob.position(), this.acceptedDistance())) {
            this.reachedTarget = false;
            ++this.tryTicks;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo(blockpos.getX(), blockpos.getY(),
                        blockpos.getZ(), this.speedModifier);
            }
        } else {
            this.reachedTarget = true;
            --this.tryTicks;
        }
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 40 == 0;
    }

    protected boolean isReachedTarget() {
        return this.reachedTarget;
    }

    /**
     * Searches and sets new destination block and returns true if a suitable block (specified in {@link
     * #isValidTarget(net.minecraft.world.level.LevelReader, net.minecraft.core.BlockPos)}) can be found.
     */
    protected boolean findNearestBlock() {
        BlockPos blockpos = this.mob.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for(int k = this.verticalSearchStart;k <= this.verticalSearchRange;k = k > 0 ? -k : 1 - k) {
            for(int l = 0;l < this.searchRange;++l) {
                for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, i1, k - 1, j1);
                        if (this.mob.isWithinRestriction(blockpos$mutableblockpos) && this.isValidTarget(
                                this.mob.level(), blockpos$mutableblockpos)) {
                            this.blockPos = blockpos$mutableblockpos;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Return {@code true} to set given position as destination
     */
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).is(this.tag);
    }

    public <T> T convert() {
        return IXUtil.c.convert(this.mob);
    }
}
