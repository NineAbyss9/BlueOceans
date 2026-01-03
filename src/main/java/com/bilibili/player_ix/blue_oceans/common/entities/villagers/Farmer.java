
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.Profession;
import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.bilibili.player_ix.blue_oceans.common.item.util.ScytheItem;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.github.player_ix.ix_api.api.ApiPose;
import com.github.player_ix.ix_api.api.item.ItemStacks;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.ForgeEventFactory;
import org.nine_abyss.util.IXUtil;
import org.nine_abyss.util.IXUtilUser;
import org.nine_abyss.util.Option;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class Farmer
extends BaseVillager {
    public Farmer(EntityType<? extends Farmer> type, Level level) {
        super(type, level);
    }

    public void registerBehaviors() {
        this.behaviorSelector.addBehavior(3, new VillagerAttackBehavior(
                this, 1.0, false, 7.5));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new UseBonemealGoal(this));
        this.goalSelector.addGoal(2, new FarmGoal(this));
        super.registerGoals();
    }

    public Profession getProfession() {
        return Profession.FARMER;
    }

    public VillagerTrades.ItemListing[] getTradeLists() {
        return BoVillagerTrades.FARMER_TRADES;
    }

    protected ItemStack getDailyItem() {
        return ItemStacks.of(Items.IRON_HOE);
    }

    protected ItemStack getWorkItem() {
        return new ItemStack(BlueOceansItems.IRON_SCYTHE.get());
    }

    public ApiPose getArmPose() {
        if (isAggressive() || isWorking())
            return ApiPose.ATTACKING;
        return ApiPose.NATURAL;
    }

    protected static class UseBonemealGoal extends Goal implements IXUtilUser {
        private long nextWorkCycleTime;
        private long lastBonemealingSession;
        private int timeWorkedSoFar;
        private Option<BlockPos> cropPos = Option.empty();
        private final Farmer farmer;
        public UseBonemealGoal(Farmer pFarmer) {
            this.farmer = pFarmer;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            if (farmer.tickCount % 10 == 0 && (this.lastBonemealingSession == 0L || this.lastBonemealingSession + 160L
                    <= farmer.tickCount)) {
                if (farmer.getInventory().countItem(Items.BONE_MEAL) <= 0)
                    return false;
                else {
                    if (!checkTarget())
                        return false;
                    this.cropPos = this.pickNextTarget(farmer.level(), farmer);
                    return this.cropPos.isPresent();
                }
            } else
                return false;
        }

        public boolean canContinueToUse() {
            return this.timeWorkedSoFar < 80 && this.cropPos.isPresent()
                    && checkTarget();
        }

        private Option<BlockPos> pickNextTarget(Level pLevel, Farmer pVillager) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            Option<BlockPos> option = Option.empty();
            int i = 0;
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    for (int l = -1; l <= 1; ++l) {
                        blockpos$mutableblockpos.setWithOffset(pVillager.blockPosition(), j, k, l);
                        if (this.validPos(blockpos$mutableblockpos, pLevel)) {
                            ++i;
                            if (pLevel.random.nextInt(i) == 0) {
                                option = Option.of(blockpos$mutableblockpos.immutable());
                            }
                        }
                    }
                }
            }
            return option;
        }

        private boolean validPos(BlockPos pPos, Level pLevel) {
            BlockState blockstate = pLevel.getBlockState(pPos);
            net.minecraft.world.entity.ai.behavior.UseBonemeal useBonemeal;
            Block block = blockstate.getBlock();
            return block instanceof CropBlock && !((CropBlock)block).isMaxAge(blockstate);
        }

        private void setCurrentCropAsTarget(Farmer entity) {
            this.cropPos.ifPresent(pos -> {
                entity.navigation.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.5D);
                entity.lookControl.setLookAt(pos.getX(), pos.getY(), pos.getZ());
            });
        }

        public void start() {
            farmer.setTask(Task.WORK);
            this.setCurrentCropAsTarget(farmer);
            farmer.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BONE_MEAL));
            this.nextWorkCycleTime = farmer.level().getGameTime();
            this.timeWorkedSoFar = 0;
        }

        @SuppressWarnings("deprecation")
        public void tick() {
            Level pLevel = farmer.level();
            long pGameTime = pLevel.getGameTime();
            BlockPos blockpos = this.cropPos.orElseThrow();
            if (pGameTime >= this.nextWorkCycleTime && blockpos.closerToCenterThan(farmer.position(), 1)) {
                ItemStack itemstack = ItemStack.EMPTY;
                SimpleContainer simplecontainer = farmer.getInventory();
                int i = simplecontainer.getContainerSize();
                for (int j = 0; j < i; ++j) {
                    ItemStack itemstack1 = simplecontainer.getItem(j);
                    if (itemstack1.is(Items.BONE_MEAL)) {
                        itemstack = itemstack1;
                        break;
                    }
                }
                if (!itemstack.isEmpty() && BoneMealItem.growCrop(itemstack, pLevel, blockpos)) {
                    pLevel.levelEvent(1505, blockpos, 0);
                    this.cropPos = this.pickNextTarget(pLevel, farmer);
                    this.setCurrentCropAsTarget(farmer);
                    this.nextWorkCycleTime = pGameTime + 40L;
                }
                ++this.timeWorkedSoFar;
            }
        }

        public void stop() {
            farmer.resetTask();
            farmer.setHandItemToDaily();
            this.lastBonemealingSession = farmer.tickCount;
        }

        public <T> T convert() {
            return IXUtil.c.convert(this.farmer);
        }

        public boolean checkTarget() {
            return farmer.getTarget() == null;
        }
    }

    protected static class FarmGoal extends Goal implements IXUtilUser {
        private final Farmer farmer;
        @Nullable
        private BlockPos aboveFarmlandPos;
        private long nextOkStartTime;
        private int timeWorkedSoFar;
        private final List<BlockPos> validFarmlandAroundVillager = Lists.newArrayList();
        public FarmGoal(Farmer pFarmer) {
            this.farmer = pFarmer;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Nullable
        private BlockPos getValidFarmland(Level pServerLevel) {
            return this.validFarmlandAroundVillager.isEmpty() ? null : this.validFarmlandAroundVillager.get(pServerLevel
                    .getRandom().nextInt(this.validFarmlandAroundVillager.size()));
        }

        private boolean validPos(BlockPos pPos, Level pServerLevel) {
            BlockState blockstate = pServerLevel.getBlockState(pPos);
            Block block = blockstate.getBlock();
            Block block1 = pServerLevel.getBlockState(pPos.below()).getBlock();
            return block instanceof CropBlock && ((CropBlock)block).isMaxAge(blockstate) || blockstate.isAir() && block1
                    instanceof FarmBlock;
        }

        public boolean canUse() {
            if (!ForgeEventFactory.getMobGriefingEvent(this.farmer.level(), this.farmer))
                return false;
            else {
                if (!checkTarget())
                    return false;
                BlockPos.MutableBlockPos pos = this.farmer.blockPosition().mutable();
                validFarmlandAroundVillager.clear();
                for (int i = -1; i <= 1; ++i) {
                    for (int j = -1; j <= 1; ++j) {
                        for (int k = -1; k <= 1; ++k) {
                            pos.set(farmer.getX() + i, farmer.getY() + j, farmer.getZ() + k);
                            if (this.validPos(pos, farmer.level())) {
                                this.validFarmlandAroundVillager.add(new BlockPos(pos));
                            }
                        }
                    }
                }
                aboveFarmlandPos = this.getValidFarmland(this.farmer.level());
                return aboveFarmlandPos != null;
            }
        }

        public void start() {
            if (farmer.level().getGameTime() > nextOkStartTime && aboveFarmlandPos != null) {
                farmer.setTask(Task.WORK);
                farmer.lookControl.setLookAt(aboveFarmlandPos.getX(), aboveFarmlandPos.getY(), aboveFarmlandPos.getZ());
                farmer.navigation.moveTo(aboveFarmlandPos.getX(), aboveFarmlandPos.getY(), aboveFarmlandPos.getZ(),
                        0.5);
                farmer.setHandItemToWork();
            }
        }

        public void tick() {
            Farmer pOwner = farmer;
            Level pLevel = farmer.level();
            long pGameTime = farmer.level().getGameTime();
            if (this.aboveFarmlandPos == null || this.aboveFarmlandPos.closerToCenterThan(pOwner.position(), 1)) {
                if (this.aboveFarmlandPos != null && pGameTime > this.nextOkStartTime) {
                    BlockState blockstate = pLevel.getBlockState(this.aboveFarmlandPos);
                    Block block = blockstate.getBlock();
                    Block block1 = pLevel.getBlockState(this.aboveFarmlandPos.below()).getBlock();
                    if (block instanceof CropBlock && ((CropBlock)block).isMaxAge(blockstate)) {
                        if (farmer.getMainHandItem().getItem() instanceof ScytheItem scythe)
                            scythe.mineBlock(farmer.getMainHandItem(), pLevel, blockstate, aboveFarmlandPos, farmer);
                        else
                            pLevel.destroyBlock(this.aboveFarmlandPos, true, pOwner);
                        farmer.swing(InteractionHand.MAIN_HAND);
                    }
                    if (blockstate.isAir() && block1 instanceof FarmBlock && pOwner.hasFarmSeeds()) {
                        SimpleContainer simplecontainer = pOwner.getInventory();
                        for (int i = 0; i < simplecontainer.getContainerSize(); ++i) {
                            ItemStack itemstack = simplecontainer.getItem(i);
                            boolean flag = false;
                            if (!itemstack.isEmpty() && itemstack.is(ItemTags.VILLAGER_PLANTABLE_SEEDS)) {
                                Item $$11 = itemstack.getItem();
                                if ($$11 instanceof BlockItem blockitem) {
                                    BlockState blockstate1 = blockitem.getBlock().defaultBlockState();
                                    pLevel.setBlockAndUpdate(this.aboveFarmlandPos, blockstate1);
                                    pLevel.gameEvent(GameEvent.BLOCK_PLACE, this.aboveFarmlandPos, GameEvent.Context
                                            .of(pOwner, blockstate1));
                                    flag = true;
                                } else if (itemstack.getItem() instanceof IPlantable plantable) {
                                    if (plantable.getPlantType(pLevel, aboveFarmlandPos) == PlantType.CROP) {
                                        pLevel.setBlock(aboveFarmlandPos, plantable.getPlant(pLevel, aboveFarmlandPos), 3);
                                        flag = true;
                                    }
                                }
                            }
                            if (flag) {
                                BlockPos pos = aboveFarmlandPos;
                                pLevel.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                                        SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
                                itemstack.shrink(1);
                                if (itemstack.isEmpty()) {
                                    simplecontainer.setItem(i, ItemStack.EMPTY);
                                }
                                break;
                            }
                        }
                    }
                    if (block instanceof CropBlock && !((CropBlock)block).isMaxAge(blockstate)) {
                        this.validFarmlandAroundVillager.remove(this.aboveFarmlandPos);
                        this.aboveFarmlandPos = this.getValidFarmland(pLevel);
                    }
                }
                ++this.timeWorkedSoFar;
            }
        }

        public boolean canContinueToUse() {
            return this.timeWorkedSoFar < 200 && checkTarget();
        }

        public boolean checkTarget() {
            return farmer.getTarget() == null;
        }

        public void stop() {
            farmer.resetTask();
            farmer.navigation.stop();
            farmer.setHandItemToDaily();
            this.timeWorkedSoFar = 0;
            this.nextOkStartTime = farmer.level().getGameTime() + 40L;
        }

        protected <T> T convert() {
            return IXUtil.c.convert(farmer);
        }
    }
}
