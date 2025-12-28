
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.Profession;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.Behavior;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.ModFishingHook;
import com.github.player_ix.ix_api.api.item.ItemStacks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

public class Fishman
extends BaseVillager {
    private BlockPos fishPos;
    @Nullable
    public ModFishingHook fishing;
    public Fishman(EntityType<? extends Fishman> pType, Level level) {
        super(pType, level);
        this.fishPos = this.blockPosition();
        this.setItemInHand(InteractionHand.MAIN_HAND, ItemStacks.of(Items.FISHING_ROD));
    }

    public void registerBehaviors() {
        super.registerBehaviors();
        this.behaviorSelector.addBehavior(4, new FishBehavior(this));
    }

    public Profession getProfession() {
        return Profession.FISHMAN;
    }

    public VillagerTrades.ItemListing[] getTradeLists() {
        return BoVillagerTrades.FISHMAN_TRADES;
    }

    @Nullable
    private BlockPos findWaterPos() {
        BlockPos pos;
        RandomSource source = this.getRandom();
        BlockPos blockPos = this.blockPosition();
        for (int i = 0; i < 10; i++) {
            pos = blockPos.offset(source.nextInt(20) - 10, 2 - source.nextInt(8),
                    source.nextInt(20) - 10);
            if (this.level().getBlockState(pos).is(Blocks.WATER)) {
                return pos;
            }
        }
        return null;
    }

    @Nullable
    private BlockPos findFishPos() {
        if (this.findWaterPos() != null) {
            BlockPos pos = this.findWaterPos();
            if (pos != null) {
                BlockPos finalPos = pos.above();
                if (this.level().isEmptyBlock(finalPos) && this.level().isEmptyBlock(finalPos.above())
                        && this.level().getBlockState(finalPos.above()).entityCanStandOn(
                        this.level(), finalPos.above(), this)) {
                    this.fishPos = finalPos;
                    return finalPos;
                }
            }
        }
        return null;
    }

    private boolean canFish() {
        return this.isHolding(Items.FISHING_ROD) && this.findFishPos() != null;
    }

    protected ItemStack getDailyItem() {
        return ItemStacks.of(Items.FISHING_ROD);
    }

    private static class FishBehavior extends Behavior {
        private final Fishman fishman;
        FishBehavior(Fishman pMan) {
            this.fishman = pMan;
        }

        public void start() {
            BlockPos pos = this.fishman.fishPos;
            this.fishman.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.8);
        }

        public void tick() {
            super.tick();
        }

        public boolean canUse() {
            return this.fishman.canFish();
        }
    }
}
